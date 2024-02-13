package Task30;

import Task20.ZipCodesApiRequests.GetZipcodes;
import Task20.ZipCodesApiRequests.PostZipCodes;
import Task30.UserApiRequests.GetUser;
import Task30.UserApiRequests.PostUser;
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

import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTest {

    private final PostZipCodes postZipCodes = new PostZipCodes();
    private final GetZipcodes getZipcodes = new GetZipcodes();
    private final GetUser getUser = new GetUser();
    private final PostUser postUser = new PostUser();

    @BeforeEach
    void setup() {
        TokenSingleton.initialize();
    }

    @Step
    String getZipCodes() {
        String responseBody = getZipcodes.getZipCodes();
        return responseBody;
    }

    @Step
    String getUser() {
        String userResponseBody = getUser.getUsers();
        return userResponseBody;
    }

    @Step
    int postUser(User user) {
        int responseBody = postUser.postUser(user);
        return responseBody;
    }

    @Test
    @Description("Scenario #1: Create user with all fields")
    @Story("Create User")
    void createUserWithAllFieldsTest() {

        postZipCodes.postZipCode("[" + "13131" + "]");
        User user = new UserBuilder().setAge("10").setName("test30").setSex("FEMALE").setZipCode("13131").createUser();
        postUser(user);
        String userResponseBody = getUser();
        String zipCodesResponseBody = getZipCodes();
        assertTrue(userResponseBody.contains(user.getName()));
        assertFalse(zipCodesResponseBody.contains(user.getZipCode()));
    }

    @Test
    @Description("Scenario #2: Create user with only required fields")
    @Story("Create User")
    void createUserWithOnlyRequiredFieldsTest() {

        User user = new UserBuilder().setAge("10").setName("test31").setSex("FEMALE").createUser();
        postUser(user);
        String userResponseBody = getUser();
        assertTrue(userResponseBody.contains(user.getName()));
    }

    @Test
    @Description("Scenario #3: Create user with unavailable zip code")
    @Story("Create User")
    void createUserWithUnavailableZipCodeTest() {

        User user = new UserBuilder().setAge("10").setName("test32").setSex("FEMALE").setZipCode("21212").createUser();
        int statusCode = postUser(user);
        assertEquals(HttpStatus.SC_FAILED_DEPENDENCY, statusCode);
    }

    @Test
    @Description("Scenario #4: Create user with the same sex and name")
    @Story("Create User")
    @Issue("Bug #1 : It is possible to create user with the same name and sex")
    void createUserWithTheSameNameAndSexTest() {

        postZipCodes.postZipCode("[" + "12121" + "," + "12122" + "]");
        User user = new UserBuilder().setAge("10").setName("test33").setSex("FEMALE").setZipCode("12121").createUser();
        User duplicatedUser = new UserBuilder().setAge("10").setName("test33").setSex("FEMALE").setZipCode("12122").createUser();
        postUser(user);
        int statusCode = postUser(duplicatedUser);
        assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }
}
