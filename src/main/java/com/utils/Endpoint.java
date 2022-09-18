package com.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public enum Endpoint {
    CREATE_USER("createUser"),
    CREATE_MULTIPLE_USERS("createMultipleUsers"),
    GET_UPDATE_DELETE_USER("getUpdateDeleteUser"),
    CREATE_UPDATE_PET("createPet"),
    GET_DELETE_PET("getUpdateDeletePet"),
    FIND_PET_BY_STATUS("findByStatusPet"),
    TEST("test");

    public String endpoint;
    private static final Map<String, String> ENDPOINTS = new HashMap<>();

    static {
        // Retrieve the properties from ApiResource file
        ResourceBundle bundle = PropertiesUtil.getBundle(BundleFile.API_RESOURCES);

        // Store all the endpoints into the map
        for (Endpoint enp : values()) {
            ENDPOINTS.put(enp.endpoint, bundle.getString(enp.endpoint));
        }
    }

    Endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return ENDPOINTS.get(this.endpoint);
    }

    public static int getMapCount() {
        return ENDPOINTS.size();
    }
}
