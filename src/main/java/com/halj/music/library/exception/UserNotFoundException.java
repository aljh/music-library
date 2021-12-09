package com.halj.music.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class UserNotFoundException.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8751418905865804047L;

    /**
     * Instantiates a new user not found exception.
     */
    public UserNotFoundException() {
        super();
    }

    /**
     * Instantiates a new user not found exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public UserNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new user not found exception.
     *
     * @param message the message
     */
    public UserNotFoundException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new user not found exception.
     *
     * @param cause the cause
     */
    public UserNotFoundException(final Throwable cause) {
        super(cause);
    }
}
