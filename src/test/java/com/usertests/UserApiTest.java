package com.usertests;

import com.base.BaseTest;
import com.builders.UserBuilder;
import com.controller.UsersApiController;
import com.pojo.User;
import com.utils.TestData;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.utils.ResponseMessage.OK;
import static com.utils.ResponseMessage.SOMETHING_BAD_HAPPENED;
import static com.utils.ResponseMessage.USER_NOT_FOUND;

public class UserApiTest extends BaseTest {
    @DataProvider(name = "multipleUsersData")
    public Object[][] getUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            users.add(UserBuilder.build());
        }

        Object[][] data = new Object[1][1];
        TestData testData = new TestData().addUsers(users);
        data[0][0] = testData;

        return data;
    }

    @Test(description = "Create multiple users with array and verify the response", dataProvider = "multipleUsersData")
    public void createMultipleUsers(TestData testData) {
        List<User> users = testData.getUsers();

        // Make POST API request to create users
        UsersApiController usersApiController = new UsersApiController();
        Response response = usersApiController.createUsersWithArray(users);
        validateCodeMessageResponseStatus(response, 200, OK);

        // Make GET request and verify user details
        users.forEach(user -> {
            Response getRes = usersApiController.getUser(user);
            validateResponseStatus(getRes, 200);
            User user1 = getRes.as(User.class);
            Assert.assertEquals(user, user1, "User details are not created correctly.");
        });
    }

    @Test(description = "Update user details and verify the response", dataProvider = "multipleUsersData")
    public void updateUserWithUserName(TestData testData) {
        List<User> users = testData.getUsers();
        User user = users.get(0);

        // Make POST API request to create users
        UsersApiController usersApiController = new UsersApiController();
        Response response = usersApiController.createUsersWithArray(users);
        validateCodeMessageResponseStatus(response, 200, OK);

        // Update user details
        user.setFirstName("Test Update");
        // Make PUT to update the details
        Response updateResponse = usersApiController.updateUser(user);
        validateCodeAndResponseStatus(updateResponse, 200);

        // Retrieve updated details and verify
        Response getResponse = usersApiController.getUser(user);
        validateResponseStatus(getResponse, 200);
        User user1 = getResponse.as(User.class);
        Assert.assertEquals(user, user1,
                "PUT request is not updating the user details.");
    }

    @Test(description = "Test POST request with empty users request body")
    public void testPostRequestWithEmptyData() {
        List<User> users = new ArrayList<>();
        users.add(new User());

        UsersApiController usersApiController = new UsersApiController();
        Response response = usersApiController.createUsersWithArray(users);
        validateCodeMessageResponseStatus(response, 200, OK);
    }

    @Test(description = "Test POST request with empty request body")
    public void testPostRequestWithEmptyDataWithoutUsers() {
        List<User> users = new ArrayList<>();

        UsersApiController usersApiController = new UsersApiController();
        Response response = usersApiController.createUsersWithArray(users);
        validateCodeMessageResponseStatus(response, 200, OK);
    }

    @Test(description = "Test POST request with invalid request body")
    public void testPostRequestWithInvalidData() {
        User user = new User();

        UsersApiController usersApiController = new UsersApiController();
        RequestSpecification specification = usersApiController.requestSpecForMultipleCreateUsers();
        Response response = usersApiController.postRequest(specification, user);
        validateCodeMessageResponseStatus(response, 500, SOMETHING_BAD_HAPPENED);
    }

    @Test(description = "GET request with non-exist username")
    public void testGetRequestWithNonExistUsername() {
        User user = UserBuilder.build();

        UsersApiController usersApiController = new UsersApiController();
        Response response = usersApiController.getUser(user);
        validateResponseStatusAndMessage(response, 404, USER_NOT_FOUND);
    }

    @Test(description = "GET request with empty and null as username")
    public void testGetRequestWithEmptyAndNullValue() {
        User user = new User();

        // Make GET request with empty username
        UsersApiController usersApiController = new UsersApiController();
        Response response = usersApiController.getUser(user);
        validateResponseStatusAndMessage(response, 404, USER_NOT_FOUND);

        // Make GET request with null as username
        user.setUsername(null);
        response = usersApiController.getUser(user);
        validateResponseStatusAndMessage(response, 404, USER_NOT_FOUND);
    }
}
