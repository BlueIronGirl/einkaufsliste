package de.shoppinglist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")  // 401
public class UnautorizedException extends RuntimeException {

    public UnautorizedException(String message) {
        super(message);
    }
}
