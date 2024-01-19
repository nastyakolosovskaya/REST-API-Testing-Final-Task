package Task50;

import Task30.UserApiRequests.GetUser;
import Task30.UserApiRequests.PostUser;
import Task50.UpdateUserApiRequests.PatchUser;
import Task50.UpdateUserApiRequests.PutUser;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageApp.Data.User;
import storageApp.Data.UserBuilder;
import storageApp.Helpers.TokenSingleton;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateUserTest {

    private final GetUser getUser = new GetUser();
    private final PutUser putUser = new PutUser();
    private final PatchUser patchUser = new PatchUser();
    private final PostUser postUser = new PostUser();

    @BeforeEach
    void setup() {
        TokenSingleton.initialize();
    }

    @SneakyThrows
    @Test
    void updateUserWithPutMethodTest() {

        User user = new UserBuilder().setAge("20").setName("test14").setSex("FEMALE").createUser();
        postUser.postUserResponse(user);
        User updatedUser = new UserBuilder().setAge("21").setName("test15").setSex("MALE").createUser();

        int statusCode = putUser.putUserResponse(user, updatedUser);

        String userResponseBody = getUser.getUserResponse(HttpStatus.SC_OK);
        assertAll(
                () -> assertEquals(HttpStatus.SC_OK, statusCode),
                () -> assertTrue(userResponseBody.contains(updatedUser.getName())),
                () -> assertTrue(userResponseBody.contains(updatedUser.getAge())),
                () -> assertTrue(userResponseBody.contains(updatedUser.getSex()))
        );
    }

    @SneakyThrows
    @Test
    void updateUserWithPatchMethodTest() {

        User user = new UserBuilder().setAge("30").setName("test16").setSex("MALE").setZipCode("33333").createUser();
        postUser.postUserResponse(user);
        User updatedUser = new UserBuilder().setAge("31").setName("test17").setSex("FEMALE").setZipCode("23456").createUser();

        int statusCode = patchUser.patchUserResponse(user, updatedUser);

        String userResponseBody = getUser.getUserResponse(HttpStatus.SC_OK);
        assertAll(
                () -> assertEquals(HttpStatus.SC_OK, statusCode),
                () -> assertTrue(userResponseBody.contains(updatedUser.getName())),
                () -> assertTrue(userResponseBody.contains(updatedUser.getAge())),
                () -> assertTrue(userResponseBody.contains(updatedUser.getSex()))

        );
    }

    @SneakyThrows
    @Test
    void updateUserWithUnavailableZipCodeTest() {

        User user = new UserBuilder().setAge("40").setName("test18").setSex("MALE").setZipCode("23456").createUser();
        postUser.postUserResponse(user);
        User updatedUser = new UserBuilder().setAge("41").setName("test19").setSex("FEMALE").setZipCode("00000").createUser();

        int statusCode = patchUser.patchUserResponse(user, updatedUser);

        assertEquals(HttpStatus.SC_FAILED_DEPENDENCY, statusCode);
    }

    @SneakyThrows
    @Test
    void updateUserWithoutNameTest() {

        User user = new UserBuilder().setAge("50").setName("test20").setSex("MALE").createUser();
        postUser.postUserResponse(user);
        User updatedUser = new UserBuilder().setAge("51").setSex("FEMALE").createUser();

        int statusCode = patchUser.patchUserResponse(user, updatedUser);

        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }

    @SneakyThrows
    @Test
    void updateUserWithoutSexTest() {

        User user = new UserBuilder().setAge("50").setName("test22").setSex("MALE").createUser();
        postUser.postUserResponse(user);
        User updatedUser = new UserBuilder().setAge("51").setName("test23").createUser();

        int statusCode = patchUser.patchUserResponse(user, updatedUser);

        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }

    @SneakyThrows
    @Test
    void updateUserWithoutAgeTest() {

        User user = new UserBuilder().setAge("50").setName("test24").setSex("MALE").createUser();
        postUser.postUserResponse(user);
        User updatedUser = new UserBuilder().setSex("FEMALE").setName("test25").createUser();

        int statusCode = patchUser.patchUserResponse(user, updatedUser);

        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }
}
