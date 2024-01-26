package Task60;

import Task20.ZipCodesApiRequests.GetZipcodes;
import Task30.UserApiRequests.GetUser;
import Task30.UserApiRequests.PostUser;
import Task60.DeleteUserApiRequests.DeleteUser;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageApp.Data.User;
import storageApp.Data.UserBuilder;
import storageApp.Helpers.TokenSingleton;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteUserTest {

    private final DeleteUser deleteUser = new DeleteUser();
    private final PostUser postUser = new PostUser();
    private final GetZipcodes getZipcodes = new GetZipcodes();
    private final GetUser getUser = new GetUser();

    @BeforeEach
    void setup() {
        TokenSingleton.initialize();
    }

    @SneakyThrows
    @Test
    void deleteUserWithAllFieldsTest() {

        User user = new UserBuilder().setAge("10").setName("test").setSex("FEMALE").setZipCode("12345").createUser();
        postUser.postUserResponse(user);

        int statusCode = deleteUser.deleteUserResponse(user);
        String zipCodesResponseBody = getZipcodes.getZipCodesResponse(HttpStatus.SC_OK);

        getUser.getUserResponse(HttpStatus.SC_OK);
        assertEquals(HttpStatus.SC_NO_CONTENT, statusCode);
        assertTrue(zipCodesResponseBody.contains(user.getZipCode()));
    }

    @SneakyThrows
    @Test
    void deleteUserWithOnlyRequiredFieldsTest() {

        User user = new UserBuilder().setAge("10").setName("test1").setSex("FEMALE").setZipCode("23456").createUser();
        postUser.postUserResponse(user);
        User userWithRequiredFields = new UserBuilder().setAge("10").setName("test1").setSex("FEMALE").createUser();
        int statusCode = deleteUser.deleteUserResponse(userWithRequiredFields);
        String zipCodesResponseBody = getZipcodes.getZipCodesResponse(HttpStatus.SC_OK);

        assertEquals(HttpStatus.SC_NO_CONTENT, statusCode);
        assertTrue(zipCodesResponseBody.contains(user.getZipCode()));
    }

    @SneakyThrows
    @Test
    void deleteUserWithoutNameTest() {

        User user = new UserBuilder().setAge("10").setName("test2").setSex("FEMALE").setZipCode("ABCDE").createUser();
        postUser.postUserResponse(user);
        User userWithRequiredFields = new UserBuilder().setAge("10").setSex("FEMALE").setZipCode("ABCDE").createUser();
        int statusCode = deleteUser.deleteUserResponse(userWithRequiredFields);

        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }

    @SneakyThrows
    @Test
    void deleteUserWithoutSexTest() {

        User user = new UserBuilder().setAge("10").setName("test3").setSex("FEMALE").setZipCode("12345").createUser();
        postUser.postUserResponse(user);
        User userWithRequiredFields = new UserBuilder().setAge("10").setName("test3").setZipCode("12345").createUser();
        int statusCode = deleteUser.deleteUserResponse(userWithRequiredFields);

        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }
}
