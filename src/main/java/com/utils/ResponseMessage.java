package com.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public enum ResponseMessage {

    OK("ok"),
    USER_NOT_FOUND("userNotFound"),
    SOMETHING_BAD_HAPPENED("somethingBad"),
    PET_NOT_FOUND("petNotFound"),
    TEST("test");

    private static final Map<String, String> MESSAGES = new HashMap<>();

    static {
        // Retrieve the properties from ResponseMessages file
        ResourceBundle bundle = PropertiesUtil.getBundle(BundleFile.RESPONSE_MESSAGES);

        // Store all the endpoints into the map
        for (ResponseMessage message : values()) {
            MESSAGES.put(message.responseMessage, bundle.getString(message.responseMessage));
        }
    }

    private final String responseMessage;

    ResponseMessage(String errorMessage) {
        this.responseMessage = errorMessage;
    }

    public String getResponseMessage() {
        return MESSAGES.get(this.responseMessage);
    }

    public static int getMapCount() {
        return MESSAGES.size();
    }
}
