package Task20;

import Task20.ZipCodesApiRequests.GetZipcodes;
import Task20.ZipCodesApiRequests.PostZipCodes;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storageApp.Helpers.TokenSingleton;

import static org.junit.jupiter.api.Assertions.*;

public class ZipCodesTest {
    private final GetZipcodes getZipcodes = new GetZipcodes();
    private final PostZipCodes postZipCodes = new PostZipCodes();
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
    int numberOfZipCodeOccurrences(String responseBody, String sentence) {
        int occurrences = postZipCodes.numberOfOccurrences(responseBody, sentence);
        return occurrences;
    }
    @Step
    String postZipCodes(String json) {
        String responseBody = postZipCodes.postZipCode(json);
        return responseBody;
    }

    @Test
    @Description("Scenario #1: Get the list of all available Zip Codes")
    @Story("Zip Codes")
    void getAllAvailableZipCodeTest() {

        String responseBody = getZipCodes();
        assertTrue(responseBody.contains("ABCDE"));
    }

    @Test
    @Description("Scenario #2: Add valid Zip Code")
    @Story("Zip Codes")
    @Issue("Bug #1 : GET request to /zip-codes endpoint return 201 code instead of 200 code")
    void addZipCodeTest() {

        final String json = "[" + "44444" + "]";
        String responseBody = postZipCodes(json);
        assertTrue(responseBody.contains("44444"));
    }

    @Test
    @Description("Scenario #2: Add several valid Zip Codes")
    @Story("Zip Codes")
    void addSeveralZipCodesTest() {

        final String json = "[" + "11111" + "," + "2222" + "]";
        String responseBody = postZipCodes(json);
        assertAll(
                () -> assertTrue(responseBody.contains("11111")),
                () -> assertTrue(responseBody.contains("2222"))
        );
    }

    @Test
    @Description("Scenario #3: Add duplicated Zip Codes which don't exist in the Zip Codes list")
    @Story("Zip Codes")
    @Issue("Bug #2: Duplicates are saved for POST request to /zip-codes/expand endpoint")
    void duplicateValidationInZipCodeListTest() {

        final String json = "[" + "31313" + "," + "31313" + "]";
        String responseBody = postZipCodes(json);
        int occurrences = numberOfZipCodeOccurrences(responseBody, "31313");
        assertEquals(1, occurrences);
    }

    @Test
    @Description("Scenario #3: Add duplicated Zip Codes which already exist in the Zip Codes list")
    @Story("Zip Codes")
    @Issue("Bug #3: Duplicates are saved for POST request to /zip-codes/expand endpoint")
    void duplicationsAlreadyExistInZipCodeListTest() {

        final String json = "[" + "12345" + "]";
        String responseBody = postZipCodes(json);
        int occurrences = numberOfZipCodeOccurrences(responseBody, "12345");
        assertEquals(1, occurrences);
    }
}
