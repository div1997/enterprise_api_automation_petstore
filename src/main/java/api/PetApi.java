package api;

import config.Routes;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.Pet;

public class PetApi {

    @Step("Sending POST request to create a new pet with ID: {petPayload.id}")
    public static Response createPet(Pet petPayload) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(petPayload)
                .when()
                .post(Routes.POST_PET);
    }

    @Step("Sending GET request to fetch pet details for ID: {petId}")
    public static Response getPet(long petId) {
        return RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", petId)
                .when()
                .get(Routes.GET_PET);
    }

    @Step("Sending PUT request to update pet details")
    public static Response updatePet(Pet petPayload) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(petPayload)
                .when()
                .put(Routes.PUT_PET);
    }

    @Step("Sending DELETE request to remove pet with ID: {petId}")
    public static Response deletePet(long petId) {
        return RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", petId)
                .when()
                .delete(Routes.DELETE_PET);
    }
}
