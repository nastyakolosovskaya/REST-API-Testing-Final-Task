package Task20.ZipCodesApiRequests;

import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpHeaders;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

import static io.restassured.RestAssured.given;

public class PostZipCodes {

    private final PropertyReader propertyReader = new PropertyReader();
    @SneakyThrows
    public String postZipCode(String json) {

        String response = (given()
                .baseUri(propertyReader.getProperty("basic.url"))
                .body(json)
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonWrite())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post(propertyReader.getProperty("zipcodes.expand"))
                .then().log().all()
                .extract().response().asString());
        return response;
    }

    public int numberOfOccurrences(String source, String sentence) {
        int occurrences = 0;

        if (source.contains(sentence)) {
            int withSentenceLength = source.length();
            int withoutSentenceLength = source.replace(sentence, "").length();
            occurrences = (withSentenceLength - withoutSentenceLength) / sentence.length();
        }
        return occurrences;
    }
}
