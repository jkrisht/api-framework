package com.utils;

public enum BundleFile {
    API_RESOURCES("Endpoints"),
    RESPONSE_MESSAGES("ResponseMessages"),
    TEST("test"),
    NOT_EXISTS("not_exist");

    private final String name;

    BundleFile(String fileName) {
        this.name = fileName;
    }

    public String getName() {
        return this.name;
    }
}
