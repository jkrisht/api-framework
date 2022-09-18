package com.controller;

import com.commons.Logger;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import static com.commons.TestConstants.API_BASE_URI;

public abstract class ApiRequestController {

    protected Logger logger = Logger.getLogger(this.getClass());

    /***
     * Prepare base request specification for making API calls
     * @return specification
     */
    public RequestSpecification getRequestSpec() {
        RequestSpecification specification = new RequestSpecBuilder().build();
        specification.baseUri(API_BASE_URI);
        specification.accept(ContentType.JSON);
        specification.contentType(ContentType.JSON);
        return specification;
    }

    /***
     * Returns Queryable Specification to know / log any request spec details
     * @param specification
     * @return QueryableRequestSpecification
     */
    public QueryableRequestSpecification getQueryRequest(RequestSpecification specification) {
        return SpecificationQuerier.query(specification);
    }

    /***
     * Set Given endpoint as base path to the API request
     * @param specification
     * @param endpoint
     * @return
     */
    public RequestSpecification setBasePath(RequestSpecification specification, String endpoint) {
        specification.basePath(endpoint);
        QueryableRequestSpecification query = getQueryRequest(specification);
        logger.info("Following URI has been set to Request Specification: " + query.getURI());
        return specification;
    }

    /***
     * Make POST API calls with request body
     * @param specification
     * @param reqBody
     * @return Response
     */
    public Response postRequest(RequestSpecification specification, Object reqBody) {
        return RestAssured.given(specification)
                .when().body(reqBody).post()
                .then().extract().response();
    }

    /***
     * Make PUT API call with given request body
     * @param specification
     * @param reqBody
     * @return Response
     */
    public Response putRequest(RequestSpecification specification, Object reqBody) {
        return RestAssured.given(specification)
                .when().body(reqBody).put()
                .then().extract().response();
    }

    /***
     * Make GET API call
     * @param specification
     * @return Response
     */
    public Response getRequest(RequestSpecification specification) {
        return RestAssured.given(specification)
                .when().get()
                .then().extract().response();
    }

    /***
     * Make DELETE API call
     * @param specification
     * @return Response
     */
    public Response deleteRequest(RequestSpecification specification) {
        return RestAssured.given(specification)
                .when().delete()
                .then().extract().response();
    }
}
