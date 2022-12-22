package com.fenonq.oriltask.exception;

import com.fenonq.oriltask.model.enums.ErrorType;

public class CryptocurrencyNotFoundException extends ServiceException {

    private static final String DEFAULT_MESSAGE = "Cryptocurrency is not found!";

    public CryptocurrencyNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public CryptocurrencyNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.VALIDATION_ERROR_TYPE;
    }

}
