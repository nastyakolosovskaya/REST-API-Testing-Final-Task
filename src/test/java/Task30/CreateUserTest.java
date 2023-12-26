package Task30;

import Task20.ZipCodesApiRequests.GetZipcodes;
import Task30.UserApiRequests.GetUser;
import Task30.UserApiRequests.PostUser;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

        final String json = """
                {
                  "age": 10,
                  "name": "test",
                  "sex": "FEMALE",
                  "zipCode": "23456"
                }""";
        postUser.postUserResponse(json);
        String userResponseBody = getUser.getUserResponse(HttpStatus.SC_OK);
        String zipCodesResponseBody = getZipcodes.getZipCodesResponse(HttpStatus.SC_OK);

        assertTrue(userResponseBody.contains("test"));
        assertFalse(zipCodesResponseBody.contains("23456"));
    }

    @SneakyThrows
    @Test
    void createUserWithOnlyRequiredFieldsTest() {

        final String json = """
                {
                  "age": 10,
                  "name": "test1",
                  "sex": "MALE"
                }""";

        postUser.postUserResponse(json);
        String userResponseBody = getUser.getUserResponse(HttpStatus.SC_OK);

        assertTrue(userResponseBody.contains("test1"));
    }

    @SneakyThrows
    @Test
    void createUserWithUnavailableZipCodeTest() {

        final String json = """
                {
                  "age": 10,
                  "name": "test2",
                  "sex": "FEMALE",
                  "zipCode": "55555"
                }""";
        int statusCode = postUser.postUserResponse(json);

        assertEquals(HttpStatus.SC_FAILED_DEPENDENCY, statusCode);
    }

    @SneakyThrows
    @Test
    void createUserWithTheSameNameAndSexTest() {

        final String json = """
                {
                  "age": 10,
                  "name": "test",
                  "sex": "FEMALE",
                  "zipCode": "33333"
                }""";
        int statusCode = postUser.postUserResponse(json);

        assertEquals(HttpStatus.SC_BAD_REQUEST, statusCode);
    }
}
