package de.shoppinglist.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.shoppinglist.entity.Role;
import de.shoppinglist.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Component-Class providing the UserAuthenticationProvider to create and validate JWT-Tokens
 */
@Service
public class UserAuthenticationService {
    private String secretKey; // secret key for JWT
    private final UserService userService; // UserService to find User by username

    @Autowired
    public UserAuthenticationService(@Value("${security.jwt.token.secret-key:secret-key}") String secretKey, UserService userService) {
        this.secretKey = secretKey;
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // encode secret key
    }

    /**
     * Method to create a JWT-Token for a given User that is valid for 1 hour
     *
     * @param user User to create the token for
     * @return JWT-Token
     */
    public String createToken(User user) {
        Date now = new Date();
        Date validUntil = new Date(now.getTime() + 3_600_000); // 1 Hour

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

        User user = userService.findByLogin(decodedJWT.getIssuer());

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
