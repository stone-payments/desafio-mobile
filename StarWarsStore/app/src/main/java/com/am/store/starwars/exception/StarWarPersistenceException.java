package com.am.store.starwars.exception;

/**
 * Created by Augusto on 14/01/2017.
 */

public class StarWarPersistenceException extends StarWarsException {

    public StarWarPersistenceException() {
        super();
    }

    public StarWarPersistenceException(Exception cause) {
        super(cause);
    }

    public StarWarPersistenceException(String message) {
        super(message);
    }

    public StarWarPersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
