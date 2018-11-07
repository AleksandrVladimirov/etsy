package com.vladimirov.etsy.Model;

import com.vladimirov.etsy.MyInterface.Retryable;

public class RequestFailure {

    private Retryable retryable;
    private String errorMessage;

    public RequestFailure(Retryable retryable, String errorMessage) {
        this.retryable = retryable;
        this.errorMessage = errorMessage;
    }

    public Retryable getRetryable() {
        return retryable;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
