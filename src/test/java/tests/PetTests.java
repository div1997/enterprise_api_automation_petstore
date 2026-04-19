package tests;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.PetApi;
import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import pojo.Category;
import pojo.Pet;
import pojo.Tag;

@Epic("PetStore Automation")
@Feature("Pet Management")
@Listeners(listeners.TestListener.class)
public class PetTests extends BaseTest {

    Pet petPayload;
    Faker faker = new Faker();
    long id = faker.number().randomNumber();

    @Test(priority = 1)
    @Story("Create a new pet")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify that a new pet can be created via POST request")
    public void testCreatePet() {
        logger.info("Building Pet payload...");
        String name= faker.animal().name();
        
        Category category = Category.builder().id(id).name(name).build();
        Tag tag = Tag.builder().id(0).name("string").build();
        
        petPayload = Pet.builder()
                .id(id)
                .category(category)
                .name(name)
                .photoUrls(Arrays.asList("string"))
                .tags(Arrays.asList(tag))
                .status("available")
                .build();

        logger.info("Sending POST request to create pet.");
        Response response = PetApi.createPet(petPayload);
        logger.info(response.getBody().asString());

        logger.info("Asserting creation response.");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), name);
    }

    @Test(priority = 2, dependsOnMethods = "testCreatePet")
    @Story("Retrieve an existing pet")
    @Severity(SeverityLevel.NORMAL)
    public void testGetPet() {
        
        logger.info("Fetching pet with ID: " + petPayload.getId());
        Response response = PetApi.getPet(petPayload.getId());
        logger.info(response.getBody().asString());
        
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test(dependsOnMethods = "testCreatePet")
    @Story("Update an existing pet")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdatePet() {
        String updatedname = faker.animal().name();
        Category category = Category.builder().id(id).name(updatedname).build();
        Tag tag = Tag.builder().id(0).name("string").build();
        Pet updatePetPayload;
        updatePetPayload = Pet.builder()
                .id(id)
                .category(category)
                .name(updatedname)
                .photoUrls(Arrays.asList("string"))
                .tags(Arrays.asList(tag))
                .status("available")
                .build();
        logger.info("Updating pet with ID:"+updatePetPayload.getId());
        //petPayload.setName("abhishek");
        Response response = PetApi.updatePet(updatePetPayload);
        logger.info(response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Response response1 =PetApi.getPet(updatePetPayload.getId());
        logger.info(response1.getBody().asString());
        Assert.assertEquals(response1.getStatusCode(), 200);
        Assert.assertEquals(response1.jsonPath().getString("name"), updatedname);
    
    }
    @Test(dependsOnMethods = "testCreatePet")
    @Story("Delete an existing pet")
    @Severity(SeverityLevel.NORMAL  )
    public void testDeletePet() {
        logger.info("Deleting pet with ID:"+id);
        Response response = PetApi.deletePet(id);
    
        logger.info(response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Response response1 =PetApi.getPet(id);
        logger.info(response1.getBody().asPrettyString());
        Assert.assertEquals(response1.getStatusCode(), 404);
    }

}