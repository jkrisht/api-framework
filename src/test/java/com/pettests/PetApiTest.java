package com.pettests;

import com.base.BaseTest;
import com.builders.PetBuilder;
import com.controller.PetRequestController;
import com.enums.PetStatus;
import com.pojo.Pet;
import com.utils.TestData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.utils.ResponseMessage.PET_NOT_FOUND;

public class PetApiTest extends BaseTest {

    @DataProvider(name = "petData")
    public Object[][] postPetData() {
        Pet pet = PetBuilder.build();
        TestData testData = new TestData().addPet(pet);
        Object[][] data = new Object[1][1];
        data[0][0] = testData;
        return data;
    }

    @Test(description = "Verify Pet POST request with valid request body", dataProvider = "petData")
    public void testCreatePet(TestData testData) {
        Pet pet = testData.getPets().get(0);

        PetRequestController controller = new PetRequestController();
        Response response = controller.createPet(pet);
        validateResponseStatus(response, 200);
        Pet resPet = response.as(Pet.class);
        Assert.assertEquals(pet, resPet, "Request and Response details are incorrect for POST Pet request.");

        // Retrieve details using GET request and validate
        Response getRes = controller.getPet(pet);
        validateResponseStatus(response, 200);
        Pet getResPet = getRes.as(Pet.class);
        Assert.assertEquals(pet, getResPet, "Request and Response details are incorrect for GET Pet request.");
    }

    @Test(description = "Verify POST pet request with empty request body")
    public void testPostWithEmptyData() {
        String reqBody = "{}";

        PetRequestController controller = new PetRequestController();
        RequestSpecification specification = controller.createOrUpdatePetRequest();
        Response response = controller.postRequest(specification, reqBody);
        Assert.assertTrue(response.jsonPath().getLong("id") >= 0,
                "Id is empty when pet is created with empty request body.");
    }

    @Test(description = "Verify Pet POST request with invalid content type")
    public void testWithInvalidContentType() {
        PetRequestController controller = new PetRequestController();
        RequestSpecification specification = controller.createOrUpdatePetRequest();
        specification.contentType(ContentType.XML);
        Response response = controller.postRequest(specification, "{}");
        validateResponseStatus(response, 400);
    }

    @Test(description = "Verify Pet PUT request with valid request body", dataProvider = "petData")
    public void testUpdatePet(TestData testData) {
        Pet pet = testData.getPets().get(0);

        // POST request
        PetRequestController controller = new PetRequestController();
        Response response = controller.createPet(pet);
        validateResponseStatus(response, 200);

        // Update Pet details
        pet.setStatus("pending");
        // PUT request
        Response putResponse = controller.updatePet(pet);
        validateResponseStatus(putResponse, 200);
        Pet updateReqPet = putResponse.as(Pet.class);
        Assert.assertEquals(pet, updateReqPet,
                "Request and Response details are incorrect for GET Pet request.");
    }

    @Test(description = "Verify Pet PUT request with invalid content type", dataProvider = "petData")
    public void testUpdatePetWithInvalidContentType(TestData testData) {
        Pet pet = testData.getPets().get(0);

        // POST request
        PetRequestController controller = new PetRequestController();
        Response response = controller.createPet(pet);
        validateResponseStatus(response, 200);

        // PUT request
        RequestSpecification specification = controller.createOrUpdatePetRequest();
        specification.contentType(ContentType.XML);
        Response response1 = controller.putRequest(specification, "{}");
        validateResponseStatus(response1, 400);
    }

    @Test(description = "Verify the GET request with non-exit pet id")
    public void testGetWithNonExistPetId() {
        Pet pet = new Pet();
        pet.setId(-1);

        PetRequestController controller = new PetRequestController();
        Response response = controller.getPet(pet);
        validateResponseStatusAndMessage(response, 404, PET_NOT_FOUND);
    }

    @Test(description = "Verify the GET findByStatus request with available status")
    public void testGetWithAvailableStatus() {
        PetRequestController controller = new PetRequestController();
        Response response = controller.getPetsByStatus(PetStatus.AVAILABLE);
        validateResponseStatus(response, 200);

        Pet[] availablePets = response.as(Pet[].class);
        List<Pet> nonAvailablePets = new ArrayList<>();
        Arrays.stream(availablePets).forEach(pet -> {
            if (!pet.getStatus().equals(PetStatus.AVAILABLE.getStatus())) {
                nonAvailablePets.add(pet);
            }
        });

        Assert.assertEquals(nonAvailablePets.size(), 0,
                "Following non-available pets are returned while filtering with 'available' status: "
                        + nonAvailablePets);
    }

    @Test(description = "Verify the GET findByStatus request with pending status")
    public void testGetWithPendingStatus() {
        PetRequestController controller = new PetRequestController();
        Response response = controller.getPetsByStatus(PetStatus.PENDING);
        validateResponseStatus(response, 200);

        Pet[] availablePets = response.as(Pet[].class);
        List<Pet> nonPendingPets = new ArrayList<>();
        Arrays.stream(availablePets).forEach(pet -> {
            if (!pet.getStatus().equals(PetStatus.PENDING.getStatus())) {
                nonPendingPets.add(pet);
            }
        });

        Assert.assertEquals(nonPendingPets.size(), 0,
                "Following non-pending pets are returned while filtering with 'pending' status: "
                        + nonPendingPets);
    }

    @Test(description = "Verify the GET findByStatus request with sold status")
    public void testGetWithSoldStatus() {
        PetRequestController controller = new PetRequestController();
        Response response = controller.getPetsByStatus(PetStatus.SOLD);
        validateResponseStatus(response, 200);

        Pet[] availablePets = response.as(Pet[].class);
        List<Pet> unSoldPets = new ArrayList<>();
        Arrays.stream(availablePets).forEach(pet -> {
            if (!pet.getStatus().equals(PetStatus.SOLD.getStatus())) {
                unSoldPets.add(pet);
            }
        });

        Assert.assertEquals(unSoldPets.size(), 0,
                "Following unsold pets are returned while filtering with 'sold' status: "
                        + unSoldPets);
    }

    @Test(description = "Verify the GET findByStatus request with non-existing status filter")
    public void testGetWithNonExistingStatusFilter() {
        PetRequestController controller = new PetRequestController();
        Response response = controller.getPetsByStatus(PetStatus.INVALID);
        validateResponseStatus(response, 200);
        Pet[] pets = response.as(Pet[].class);
        Assert.assertEquals(pets.length, 0,
                "Pets are being are being returned in the response when making GET status call with "
                        + PetStatus.INVALID.getStatus());
    }

}
