package Task50;

import Task20.ZipCodesApiRequests.PostZipCodes;
import Task30.UserApiRequests.GetUser;
import Task30.UserApiRequests.PostUser;
import Task50.UpdateUserApiRequests.PatchUser;
import Task50.UpdateUserApiRequests.PutUser;
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

public class UpdateUserTest {

    private final PostZipCodes postZipCodes = new PostZipCodes();
    private final GetUser getUser = new GetUser();
    private final PutUser putUser = new PutUser();
    private final PatchUser patchUser = new PatchUser();
    private final PostUser postUser = new PostUser();

    @BeforeEach
    void setup() {
        TokenSingleton.initialize();
    }

    @Step
    int postUser(User user) {
        int statusCode = postUser.postUserResponse(user);
        return statusCode;
    }

    @Step
    int putUser(User user, User updatedUser) {
        int statusCode = putUser.putUserResponse(user, updatedUser);
        return statusCode;
    }

    @Step
    int patchUser(User user, User updatedUser) {
        int statusCode = patchUser.patchUserResponse(user, updatedUser);
        return statusCode;
    }

    @Step
    String getUser() {
        String userResponseBody = getUser.getUserResponse(HttpStatus.SC_OK);
        return userResponseBody;
    }

    @Test
    @Description("Scenario 1: Update user using put method")
    @Story("Update User")
    void updateUserWithPutMethodTest() {

        User user = new UserBuilder().setAge("20").setName("test14").setSex("FEMALE").createUser();
        User updatedUser = new UserBuilder().setAge("21").setName("test15").setSex("MALE").createUser();

        postUser(user);
        int statusCode = putUser(user, updatedUser);
        String userResponseBody = getUser();

        assertAll(
                () -> assertEquals(HttpStatus.SC_OK, statusCode),
                () -> assertTrue(userResponseBody.contains(updatedUser.getName())),
                () -> assertTrue(userResponseBody.contains(updatedUser.getAge())),
                () -> assertTrue(userResponseBody.contains(updatedUser.getSex()))
        );
    }

    @Test
    @Description("Scenario 1: Update user using patch method")
    @Story("Update User")
    void updateUserWithPatchMethodTest() {

        final String json = "[" + "55555" + "]";
        postZipCodes.postZipCodeResponse(json);

        User user = new UserBuilder().setAge("30").setName("test16").setSex("MALE").setZipCode("55555").createUser();
        User updatedUser = new UserBuilder().setAge("31").setName("test17").setSex("FEMALE").setZipCode("55555").createUser();

        postUser(user);
        int statusCode = patchUser(user, updatedUser);
        String userResponseBody = getUser();

        assertAll(
                () -> assertEquals(HttpStatus.SC_OK, statusCode),
                () -> assertTrue(userResponseBody.contains(updatedUser.getName())),
                () -> assertTrue(userResponseBody.contains(updatedUser.getAge())),
                () -> assertTrue(userResponseBody.contains(updatedUser.getSex()))
        );
    }

    @Test
    @Description("Scenario 2: Update user with unavailable zip code")
    @Story("Update User")
    @Issue("Bug #1 : Wrong status code for incorrect zip code in response body")
    void updateUserWithUnavailableZipCodeTest() {

        User user = new UserBuilder().setAge("40").setName("test50").setSex("MALE").setZipCode("23456").createUser();
        User updatedUser = new UserBuilder().setAge("41").setName("test50").setSex("FEMALE").setZipCode("00000").createUser();

        postUser(user);
        int statusCode = patchUser(user, updatedUser);

        assertEquals(HttpStatus.SC_FAILED_DEPENDENCY, statusCode);
    }

    @Test
    @Description("Scenario 3: Update user without name")
    @Story("Update User")
    void updateUserWithoutNameTest() {

        User user = new UserBuilder().setAge("50").setName("test52").setSex("MALE").createUser();
        User updatedUser = new UserBuilder().setAge("51").setSex("FEMALE").createUser();

        postUser(user);
        int statusCode = patchUser(user, updatedUser);

        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }

    @Test
    @Description("Scenario 3: Update user without sex")
    @Story("Update User")
    void updateUserWithoutSexTest() {

        User user = new UserBuilder().setAge("50").setName("test54").setSex("MALE").createUser();
        User updatedUser = new UserBuilder().setAge("51").setName("test54").createUser();

        postUser(user);
        int statusCode = patchUser(user, updatedUser);

        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }

    @Test
    @Description("Scenario 3: Update user without age")
    @Story("Update User")
    @Issue("Bug #2 : User is updated with empty age")
    void updateUserWithoutAgeTest() {

        User user = new UserBuilder().setAge("50").setName("test56").setSex("MALE").createUser();
        User updatedUser = new UserBuilder().setSex("FEMALE").setName("test56").createUser();

        postUser(user);
        int statusCode = patchUser(user, updatedUser);

        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }
}
