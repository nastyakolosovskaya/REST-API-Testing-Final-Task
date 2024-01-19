package Task30;

import Task20.ZipCodesApiRequests.GetZipcodes;
import Task30.UserApiRequests.GetUser;
import Task30.UserApiRequests.PostUser;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageApp.Data.User;
import storageApp.Data.UserBuilder;
import storageApp.Helpers.TokenSingleton;

import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTest {

    private final GetZipcodes getZipcodes = new GetZipcodes();
    private final GetUser getUser = new GetUser();
    private final PostUser postUser = new PostUser();

    @BeforeEach
    void setup() {
        TokenSingleton.initialize();
    }

    @SneakyThrows
    @Test
    void createUserWithAllFieldsTest() {

        User user = new UserBuilder().setAge("10").setName("test").setSex("FEMALE").setZipCode("23456").createUser();
        postUser.postUserResponse(user);
        String userResponseBody = getUser.getUserResponse(HttpStatus.SC_OK);
        String zipCodesResponseBody = getZipcodes.getZipCodesResponse(HttpStatus.SC_OK);

        assertTrue(userResponseBody.contains(user.getName()));
        assertFalse(zipCodesResponseBody.contains(user.getZipCode()));
    }

    @SneakyThrows
    @Test
    void createUserWithOnlyRequiredFieldsTest() {

        User user = new UserBuilder().setAge("10").setName("test1").setSex("FEMALE").createUser();
        postUser.postUserResponse(user);
        String userResponseBody = getUser.getUserResponse(HttpStatus.SC_OK);

        assertTrue(userResponseBody.contains(user.getName()));
    }

    @SneakyThrows
    @Test
    void createUserWithUnavailableZipCodeTest() {

        User user = new UserBuilder().setAge("10").setName("test2").setSex("FEMALE").setZipCode("55555").createUser();
        int statusCode = postUser.postUserResponse(user);

        assertEquals(HttpStatus.SC_FAILED_DEPENDENCY, statusCode);
    }

    @SneakyThrows
    @Test
    void createUserWithTheSameNameAndSexTest() {

        User user = new UserBuilder().setAge("10").setName("test").setSex("FEMALE").setZipCode("33333").createUser();

        int statusCode = postUser.postUserResponse(user);

        assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }
}
