package com.am.store.starwars.exception;

/**
 * Created by Augusto on 14/01/2017.
 */

public class StarWarIntegrationException extends StarWarsException {

    public StarWarIntegrationException() {
        super();
    }

    public StarWarIntegrationException(Exception cause) {
        super(cause);
    }

    public StarWarIntegrationException(String message) {
        super(message);
    }

    public StarWarIntegrationException(String message, Exception cause) {
        super(message, cause);
    }
}
