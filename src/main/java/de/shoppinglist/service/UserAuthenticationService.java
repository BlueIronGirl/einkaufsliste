package de.shoppinglist.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.shoppinglist.dto.LoginDto;
import de.shoppinglist.dto.RegisterDto;
import de.shoppinglist.entity.Role;
import de.shoppinglist.entity.RoleName;
import de.shoppinglist.entity.User;
import de.shoppinglist.exception.EntityAlreadyExistsException;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.exception.UnautorizedException;
import de.shoppinglist.repository.RoleRepository;
import de.shoppinglist.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.*;

/**
 * Component-Class providing the UserAuthenticationProvider to create and validate JWT-Tokens
 */
@Service
public class UserAuthenticationService {
    private String secretKey; // secret key for JWT
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthenticationService(@Value("${security.jwt.token.secret-key:secret-key}") String secretKey, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.secretKey = secretKey;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // encode secret key
    }

    /**
     * Login a User to the Application and check the user and the password
     *
     * @param loginDto LoginDto containing the username and password
     * @return User-Object of the logged in user
     */
    public User login(LoginDto loginDto) {
        User user = findByLogin(loginDto.getUsername());

        if (passwordEncoder.matches(CharBuffer.wrap(loginDto.getPassword()), user.getPassword())) {
            fixRoles(user);
            return user;
        }
        throw new UnautorizedException("Passwort falsch!");
    }

    /**
     * TODO: Kann bald weg. Nur zum Rollen zuweisen
     *
     * @param user user
     */
    private void fixRoles(User user) {
        if (user.getRoles().isEmpty()) {

            // wenn noch keine Rollen angelegt sind, erstmal die Rollen anlegen
            if (roleRepository.findAll().isEmpty()) {
                for (RoleName roleName : RoleName.values()) {
                    Role role = Role.builder().name(roleName).build();
                    roleRepository.save(role);
                }
            }

            HashSet<Role> roles = new HashSet<>();
            if (user.getUsername().equals("alice")) { // Alice -> Admin
                roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN));
            } else { // Rest -> Gast
                roles.add(roleRepository.findByName(RoleName.ROLE_GUEST));
            }
            user.setRoles(roles);

            if (StringUtils.isEmpty(user.getEmail())) {
                user.setEmail("changeme@test.de");
            }

            userRepository.save(user);
        }
    }

    /**
     * Register a new User to the Application
     *
     * @param registerDto RegisterDto containing the username, password and name of the new user
     * @return User-Object of the registered user
     */
    public User register(RegisterDto registerDto) {
        Optional<User> optionalUser = userRepository.findByUsername(registerDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new EntityAlreadyExistsException("Benutzer existiert bereits");
        }

        Role guest = roleRepository.findByName(RoleName.ROLE_GUEST);
        HashSet<Role> roles = new HashSet<>();
        roles.add(guest);

        User user = User.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPassword())))
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByUsername(login)
                .orElseThrow(() -> new EntityNotFoundException("Unbekannter User!"));
    }

    public User findCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user) {
            String username = user.getUsername();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("Unbekannter User!"));
        }
        return null;
    }

    /**
     * Method to create a JWT-Token for a given User that is valid for 12 hours
     *
     * @param user User to create the token for
     * @return JWT-Token
     */
    public String createToken(User user) {
        Date now = new Date();
        Date validUntil = new Date(now.getTime() + 43_200_000); // 12 Hours

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validUntil)
                .withClaim("name", user.getName())
                .sign(algorithm);
    }

    /**
     * Method to validate a given JWT-Token and return the Authentication for the User
     *
     * @param token JWT-Token to validate
     * @return Authentication for the User
     */
    public Authentication validateToken(HttpServletRequest request, String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = verifier.verify(token);

        User user = findByLogin(decodedJWT.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, new WebAuthenticationDetailsSource().buildDetails(request), getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return authorities;
    }
}
