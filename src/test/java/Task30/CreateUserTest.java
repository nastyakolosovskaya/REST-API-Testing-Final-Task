package Task30;

import Task20.ZipCodesApiRequests.GetZipcodes;
import Task30.UserApiRequests.GetUser;
import Task30.UserApiRequests.PostUser;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageApp.Data.User;
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

        User user = new User("10", "test", "FEMALE", "23456");
        postUser.postUserResponse(user);
        String userResponseBody = getUser.getUserResponse(HttpStatus.SC_OK);
        String zipCodesResponseBody = getZipcodes.getZipCodesResponse(HttpStatus.SC_OK);

        assertTrue(userResponseBody.contains(user.getName()));
        assertFalse(zipCodesResponseBody.contains(user.getZipCode()));
    }

    @SneakyThrows
    @Test
    void createUserWithOnlyRequiredFieldsTest() {

        User user = new User("10", "test1", "FEMALE");
        postUser.postUserResponse(user);
        String userResponseBody = getUser.getUserResponse(HttpStatus.SC_OK);

        assertTrue(userResponseBody.contains(user.getName()));
    }

    @SneakyThrows
    @Test
    void createUserWithUnavailableZipCodeTest() {

        User user = new User("10", "test2", "FEMALE", "55555");
        int statusCode = postUser.postUserResponse(user);

        assertEquals(HttpStatus.SC_FAILED_DEPENDENCY, statusCode);
    }

    @SneakyThrows
    @Test
    void createUserWithTheSameNameAndSexTest() {

        User user = new User("10", "test", "FEMALE", "33333");

        int statusCode = postUser.postUserResponse(user);

        assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }
}
