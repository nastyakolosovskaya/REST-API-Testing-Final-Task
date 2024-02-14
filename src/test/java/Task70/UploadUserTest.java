package Task70;

import Task30.UserApiRequests.GetUser;
import Task70.UploadUsersApiRequests.UploadUser;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageApp.Helpers.TokenSingleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UploadUserTest {

    private final GetUser getUser = new GetUser();
    private final UploadUser uploadUser = new UploadUser();

    @BeforeEach
    void setup() {
        TokenSingleton.initialize();
    }

    @Step
    String getUser() {
        String userResponseBody = getUser.getUsers();
        return userResponseBody;
    }

    @Step
    int uploadUser(String filePath) {
        int statusCode = uploadUser.postUserUpload("jsonFileValid");
        return statusCode;
    }

    @Test
    @Description("Scenario 1: Update user with all fields")
    @Story("Upload User")
    void uploadUserTest() {

        int statusCode = uploadUser("jsonFileValid");
        String userResponseBody = getUser();

        assertEquals(HttpStatus.SC_CREATED, statusCode);
        assertTrue(userResponseBody.contains("test70"));
    }

    @Test
    @Description("Scenario 2: Update user with invalid zip code")
    @Story("Upload User")
    @Issue("Bug #1 : Wrong status code for incorrect zip code in response body")
    void uploadUserWithInvalidZipCodeTest() {

        int statusCode = uploadUser("usersWithInvalidZipCode");
        assertEquals(HttpStatus.SC_FAILED_DEPENDENCY, statusCode);
    }

    @Test
    @Description("Scenario 3: Update user without user name")
    @Story("Upload User")
    @Issue("Bug #2 : Wrong status code for empty required field")
    void uploadUserWithoutNameTest() {

        int statusCode = uploadUser("usersWithoutName");
        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }

    @Test
    @Description("Scenario 3: Update user without sex")
    @Story("Upload User")
    @Issue("Bug #2 : Wrong status code for empty required field")
    void uploadUserWithoutSexTest() {

        int statusCode = uploadUser("usersWithoutSex");
        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }
}
