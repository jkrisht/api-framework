package com.controller;

import com.pojo.User;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static com.commons.TestConstants.API_VERSION;
import static com.utils.Endpoint.CREATE_MULTIPLE_USERS;
import static com.utils.Endpoint.GET_UPDATE_DELETE_USER;

public class UsersApiController extends ApiRequestController {

    /***
     * Generate API request specification for creating multiple users using Array endpoint
     * @return specification
     */
    public RequestSpecification requestSpecForMultipleCreateUsers() {
        String endpoint = String.format(CREATE_MULTIPLE_USERS.getEndpoint(), API_VERSION);
        RequestSpecification specification = getRequestSpec();
        setBasePath(specification, endpoint);
        return specification;
    }

    /***
     * Generate API request specification for GET, PUT, DELETE using username
     * @param userName
     * @return specification
     */
    public RequestSpecification requestWithUsername(String userName) {
        String endpoint = GET_UPDATE_DELETE_USER.getEndpoint();
        endpoint = String.format(endpoint, API_VERSION, userName);

        RequestSpecification specification = getRequestSpec();
        setBasePath(specification, endpoint);
        return specification;
    }

    /**
     * Create multiple users using 'createWithArray' endpoint
     *
     * @param users
     * @return response
     */
    public Response createUsersWithArray(List<User> users) {
        logger.info("POST: Create Users with array endpoint.");
        RequestSpecification specification = requestSpecForMultipleCreateUsers();
        return postRequest(specification, users);
    }

    /***
     * Update given user details̵
     *
     * @param user
     * @return response
     */
    public Response updateUser(User user) {
        logger.info("PUT: Update user details using PUT request: " + user);
        RequestSpecification specification = requestWithUsername(user.getUsername());
        return putRequest(specification, user);
    }

    /***
     * Retrieve given user details
     * @param user
     * @return
     */
    public Response getUser(User user) {
        logger.info("GET: Retrieve following user details: " + user.getUsername());
        RequestSpecification getReqSpec = requestWithUsername(user.getUsername());
        return getRequest(getReqSpec);
    }
}
