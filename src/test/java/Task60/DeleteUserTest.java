package Task60;

import Task20.ZipCodesApiRequests.GetZipcodes;
import Task20.ZipCodesApiRequests.PostZipCodes;
import Task30.UserApiRequests.GetUser;
import Task30.UserApiRequests.PostUser;
import Task60.DeleteUserApiRequests.DeleteUser;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageApp.Data.User;
import storageApp.Data.UserBuilder;
import storageApp.Helpers.TokenSingleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteUserTest {

    private final PostZipCodes postZipCodes = new PostZipCodes();
    private final DeleteUser deleteUser = new DeleteUser();
    private final PostUser postUser = new PostUser();
    private final GetZipcodes getZipcodes = new GetZipcodes();
    private final GetUser getUser = new GetUser();

    @BeforeEach
    void setup() {
        TokenSingleton.initialize();
    }

    @Step
    String getUser() {
        String userResponseBody = getUser.getUserResponse(HttpStatus.SC_OK);
        return userResponseBody;
    }

    @Step
    int postUser(User user) {
        int statusCode = postUser.postUserResponse(user);
        return statusCode;
    }

    int deleteUser(User user) {
        int statusCode = deleteUser.deleteUserResponse(user);
        return statusCode;
    }

    @Step
    String getZipCodes() {
        String responseBody = getZipcodes.getZipCodesResponse(HttpStatus.SC_CREATED);
        return responseBody;
    }

    @Test
    @Description("Scenario 1: Delete user with all fields")
    @Story("Delete User")
    void deleteUserWithAllFieldsTest() {

        final String json = "[" + "66666" + "]";
        postZipCodes.postZipCodeResponse(json);
        User user = new UserBuilder().setAge("10").setName("test60").setSex("FEMALE").setZipCode("66666").createUser();

        postUser(user);
        int statusCode = deleteUser(user);
        String zipCodesResponseBody = getZipCodes();
        getUser();

        assertEquals(HttpStatus.SC_NO_CONTENT, statusCode);
        assertTrue(zipCodesResponseBody.contains(user.getZipCode()));
    }

    @Test
    @Description("Scenario 2: Delete user with only required fields")
    @Story("Delete User")
    @Issue("Bug #1 : User isn’t deleted if body request body contains required fields only")
    void deleteUserWithOnlyRequiredFieldsTest() {

        final String json = "[" + "88888" + "]";
        postZipCodes.postZipCodeResponse(json);
        User user = new UserBuilder().setAge("10").setName("test62").setSex("FEMALE").setZipCode("88888").createUser();
        User userWithRequiredFields = new UserBuilder().setAge("10").setName("test62").setSex("FEMALE").createUser();

        postUser(user);
        int statusCode = deleteUser(userWithRequiredFields);
        String zipCodesResponseBody = getZipCodes();

        assertEquals(HttpStatus.SC_NO_CONTENT, statusCode);
        assertTrue(zipCodesResponseBody.contains(user.getZipCode()));
    }

    @Test
    @Description("Scenario 3: Delete user without user name")
    @Story("Delete User")
    void deleteUserWithoutNameTest() {

        final String json = "[" + "77777" + "]";
        postZipCodes.postZipCodeResponse(json);
        User user = new UserBuilder().setAge("10").setName("test64").setSex("FEMALE").setZipCode("77777").createUser();
        User userWithRequiredFields = new UserBuilder().setAge("10").setSex("FEMALE").setZipCode("77777").createUser();

        postUser(user);
        int statusCode = deleteUser(userWithRequiredFields);

        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }

    @Test
    @Description("Scenario 3: Delete user without sex")
    @Story("Delete User")
    void deleteUserWithoutSexTest() {

        final String json = "[" + "99999" + "]";
        postZipCodes.postZipCodeResponse(json);
        User user = new UserBuilder().setAge("10").setName("test65").setSex("FEMALE").setZipCode("99999").createUser();
        User userWithRequiredFields = new UserBuilder().setAge("10").setName("test65").setZipCode("99999").createUser();

        postUser(user);
        int statusCode = deleteUser(userWithRequiredFields);

        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }
}
