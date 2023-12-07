package Task20;

import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageApp.ApiRequests.GetZipcodes;
import storageApp.ApiRequests.PostZipCodes;
import storageApp.Helpers.TokenSingleton;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserStorageAppTest {

    private final GetZipcodes getZipcodes = new GetZipcodes();
    private final PostZipCodes postZipCodes = new PostZipCodes();

    @BeforeEach
    void setup() {
        TokenSingleton.initialize();
    }

    @SneakyThrows
    @Test
    void getAllAvailableZipCodeTest() {

        String responseBody = getZipcodes.getZipCodesResponse();
        assertTrue(responseBody.contains("ABCD"));

    }

    @Test
    @SneakyThrows
    void addZipCodeTest() {

        final String json = "[" + "11111" + "]";

        int statusCode = postZipCodes.postZipCodeResponse(json);
        String responseBody = getZipcodes.getZipCodesResponse();

        assertThat(statusCode, equalTo(HttpStatus.SC_CREATED));
        assertTrue(responseBody.contains(json));

    }


    @Test
    @SneakyThrows
    void addSeveralZipCodesTest() {

        final String json = "[" + "11111" + "," + "2222" + "]";
        int statusCode = postZipCodes.postZipCodeResponse(json);
        String responseBody = getZipcodes.getZipCodesResponse();
        assertThat(statusCode, equalTo(HttpStatus.SC_CREATED));
        assertAll(
                () -> assertTrue(responseBody.contains("11111")),
                () -> assertTrue(responseBody.contains("2222"))
        );
    }

    @Test
    @SneakyThrows
    void duplicateValidationInZipCodeListTest() {

        final String json = "[" + "33333" + "," + "33333" + "]";
        int statusCode = postZipCodes.postZipCodeResponse(json);
        String responseBody = getZipcodes.getZipCodesResponse();
        assertThat(statusCode, equalTo(HttpStatus.SC_CREATED));
        assertTrue(responseBody.contains("33333"));

    }

    @Test
    @SneakyThrows
    void duplicationsAlreadyExistInZipCodeListTest() {

        final String json = "[" + "22222" + "]";
        int statusCode = postZipCodes.postZipCodeResponse(json);
        String responseBody = getZipcodes.getZipCodesResponse();
        assertThat(statusCode, equalTo(HttpStatus.SC_CREATED));
        assertTrue(responseBody.contains("33333"));

    }
}
