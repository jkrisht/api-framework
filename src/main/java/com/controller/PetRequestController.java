package com.controller;

import com.enums.PetStatus;
import com.pojo.Pet;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.commons.TestConstants.API_VERSION;
import static com.utils.Endpoint.CREATE_UPDATE_PET;
import static com.utils.Endpoint.FIND_PET_BY_STATUS;
import static com.utils.Endpoint.GET_DELETE_PET;

public class PetRequestController extends ApiRequestController {

    /***
     * Prepare Pet POST or PUT API request
     * @return specification
     */
    public RequestSpecification createOrUpdatePetRequest() {
        String endpoint = String.format(CREATE_UPDATE_PET.getEndpoint(), API_VERSION);
        RequestSpecification specification = getRequestSpec();
        setBasePath(specification, endpoint);
        return specification;
    }

    /***
     * Prepare API request with pet id for GET, DELETE requests
     * @param id
     * @return specification
     */
    public RequestSpecification requestWithId(long id) {
        String endpoint = String.format(GET_DELETE_PET.getEndpoint(), API_VERSION, id);
        RequestSpecification specification = getRequestSpec();
        setBasePath(specification, endpoint);
        return specification;
    }

    public RequestSpecification getRequestFindByStatus(PetStatus status) {
        String endpoint = String.format(FIND_PET_BY_STATUS.getEndpoint(), API_VERSION);
        RequestSpecification specification = getRequestSpec();
        setBasePath(specification, endpoint);
        specification.queryParam("status1", status.getStatus());
        return specification;
    }

    /***
     * Create Pet by making POST API call with given Pet object
     * @param pet Pet
     * @return Response
     */
    public Response createPet(Pet pet) {
        logger.info("POST: Create new pet");
        RequestSpecification specification = createOrUpdatePetRequest();
        return postRequest(specification, pet);
    }

    /***
     * Make PUT API request to update given Pet details
     * @param pet
     * @return
     */
    public Response updatePet(Pet pet) {
        logger.info("PUT: Update pet details");
        RequestSpecification specification = createOrUpdatePetRequest();
        return putRequest(specification, pet);
    }

    /***
     * Make GET API request with given Pet object id
     * @param pet
     * @return
     */
    public Response getPet(Pet pet) {
        logger.info("GET: Retrieve given pet details using id - " + pet.getId());
        RequestSpecification specification = requestWithId(pet.getId());
        return getRequest(specification);
    }

    /***
     * Make GET API request with given Pet object id
     * @param status
     * @return Response
     */
    public Response getPetsByStatus(PetStatus status) {
        logger.info("GET: Retrieve Pets based on the status - " + status.getStatus());
        RequestSpecification specification = getRequestFindByStatus(status);
        return getRequest(specification);
    }
}
