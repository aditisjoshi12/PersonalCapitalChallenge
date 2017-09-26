package com.example.aditijoshi.personalcapital.NetworkUtils;

/**
 * Created by aditijoshi on 9/24/17.
 */

public class ArticleSyncFailedEvent {
    public ArticleSyncFailedEvent(String message) {

        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    String errorMessage;

}
