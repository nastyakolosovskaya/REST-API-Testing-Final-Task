package Task20.ZipCodesApiRequests;

import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpHeaders;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

import static io.restassured.RestAssured.given;


public class GetZipcodes {
    private static final PropertyReader propertyReader = new PropertyReader();
    @SneakyThrows
    public String getZipCodes() {

        String response = (given()
                .baseUri(propertyReader.getProperty("basic.url"))
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonRead())
                .get(propertyReader.getProperty("zipcodes"))
                .then().log().all()
                .extract().body().asString());
        return response;
    }
}
