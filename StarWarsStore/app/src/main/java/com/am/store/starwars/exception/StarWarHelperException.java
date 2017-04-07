package com.am.store.starwars.exception;

/**
 * Created by Augusto on 14/01/2017.
 */

public class StarWarHelperException extends StarWarsException {

    public StarWarHelperException() {
        super();
    }

    public StarWarHelperException(Exception cause) {
        super(cause);
    }

    public StarWarHelperException(String message) {
        super(message);
    }

    public StarWarHelperException(String message, Exception cause) {
        super(message, cause);
    }
}
