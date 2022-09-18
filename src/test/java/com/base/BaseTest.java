package com.base;

import com.commons.ReportingListener;
import com.utils.ResponseMessage;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public abstract class BaseTest {
    final Logger logger = Logger.getLogger(this.getClass());

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        // Initiate extent report class
        ReportingListener.onClassStart(this.getClass().getSimpleName());
        logger.info(this.getClass().getSimpleName() + " class execution started!");
    }

    @AfterClass
    public void afterClass() {
        logger.info(this.getClass().getSimpleName() + " class execution completed!");
        // end extent report class
        ReportingListener.onClassEnd();
    }

    // Verify the response status code
    public void validateResponseStatus(Response response, int expected) {
        Assert.assertEquals(response.getStatusCode(), expected, "API Request is failed.");
    }

    // Verify the code value in the response
    public void validateCodeInResponse(Response response, int expected) {
        Assert.assertEquals(response.jsonPath().getInt("code"), expected, "API Request is failed.");
    }

    // Verify the message in the response
    public void validateMessage(Response response, ResponseMessage message) {
        Assert.assertEquals(message.getResponseMessage(), response.jsonPath().get("message"),
                "Incorrect response message received.");
    }

    // Verify the Response status and code in the response
    public void validateCodeAndResponseStatus(Response response, int expectedCode) {
        validateResponseStatus(response, expectedCode);
        validateCodeInResponse(response, expectedCode);
    }

    // Verify the Response status and message in the response
    public void validateResponseStatusAndMessage(Response response, int expectedCode, ResponseMessage message) {
        validateResponseStatus(response, expectedCode);
        validateMessage(response, message);
    }

    // Verify the Response status, code and message in the response
    public void validateCodeMessageResponseStatus(Response response, int expectedCode, ResponseMessage message) {
        validateResponseStatus(response, expectedCode);
        validateCodeInResponse(response, expectedCode);
        validateMessage(response, message);
    }
}
