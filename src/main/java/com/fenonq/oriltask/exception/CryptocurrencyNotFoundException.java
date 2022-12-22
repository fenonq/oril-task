package com.fenonq.oriltask.exception;

public class CryptocurrencyNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Cryptocurrency is not found!";

    public CryptocurrencyNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public CryptocurrencyNotFoundException(String message) {
        super(message);
    }

}
