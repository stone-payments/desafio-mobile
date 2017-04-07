package com.am.store.starwars.exception;

/**
 * Created by Augusto on 14/01/2017.
 */
public class StarWarsException extends Exception {

    public StarWarsException() {
        super();
    }

    public StarWarsException(Exception cause) {
        super(cause);
    }

    public StarWarsException(String message) {
        super(message);
    }

    public StarWarsException(String message, Exception cause) {
        super(message, cause);
    }
}