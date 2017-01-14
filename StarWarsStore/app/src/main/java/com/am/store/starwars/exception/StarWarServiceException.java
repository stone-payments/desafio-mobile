package com.am.store.starwars.exception;

/**
 * Created by Augusto on 14/01/2017.
 */

public class StarWarServiceException extends StarWarsException {

    public StarWarServiceException() {
        super();
    }

    public StarWarServiceException(Exception cause) {
        super(cause);
    }

    public StarWarServiceException(String message) {
        super(message);
    }

    public StarWarServiceException(String message, Exception cause) {
        super(message, cause);
    }
}
