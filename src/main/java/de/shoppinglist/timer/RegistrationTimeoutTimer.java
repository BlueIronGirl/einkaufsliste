package de.shoppinglist.timer;

import de.shoppinglist.entity.ConfirmationToken;
import de.shoppinglist.entity.User;
import de.shoppinglist.repository.ConfirmationTokenRepository;
import de.shoppinglist.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class RegistrationTimeoutTimer {
    private static final Logger log = LoggerFactory.getLogger(RegistrationTimeoutTimer.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final ConfirmationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public RegistrationTimeoutTimer(ConfirmationTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRate = 3_600_000) // every hour
    public void deleteOldToken() {
        log.info("Timer is now running ({})", dateFormat.format(new Date()));

        List<ConfirmationToken> tokensExpiredOrConfirmed = tokenRepository.findByExpiresAtBeforeOrConfirmedAtNotNull(LocalDateTime.now());
        tokenRepository.deleteAll(tokensExpiredOrConfirmed);

        List<User> timedoutUsers = userRepository.findByLastLoggedInNullAndCreatedAtBefore(LocalDateTime.now().minusDays(1)); // vor mind. einem Tag erstellt, aber noch nie eingeloggt
        userRepository.deleteAll(timedoutUsers);

        log.info("Timer is now finished ({})", dateFormat.format(new Date()));
    }
}
