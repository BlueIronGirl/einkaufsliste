package de.shoppinglist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception-Class for the case that an internal server error occurred
 * resulting in a 500 (Bad request)
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Validierungsfehler!")  // 500
public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String message) {
        super(message);
    }
}
