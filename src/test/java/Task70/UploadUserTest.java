package Task70;

import Task30.UserApiRequests.GetUser;
import Task70.UploadUsersApiRequests.UploadUser;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @Test
    void uploadUserTest() {

        int statusCode = uploadUser.postUserUploadResponse("jsonFileValid");
        String userResponseBody = getUser.getUserResponse(HttpStatus.SC_OK);

        assertEquals(HttpStatus.SC_CREATED, statusCode);
        assertTrue(userResponseBody.contains("test10"));
    }

    @SneakyThrows
    @Test
    void uploadUserWithInvalidZipCodeTest() {

        int statusCode = uploadUser.postUserUploadResponse("usersWithInvalidZipCode");
        assertEquals(HttpStatus.SC_FAILED_DEPENDENCY, statusCode);
    }

    @SneakyThrows
    @Test
    void uploadUserWithoutNameTest() {

        int statusCode = uploadUser.postUserUploadResponse("usersWithoutName");
        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }

    @SneakyThrows
    @Test
    void uploadUserWithoutSexTest() {

        int statusCode = uploadUser.postUserUploadResponse("usersWithoutSex");
        assertEquals(HttpStatus.SC_CONFLICT, statusCode);
    }
}
