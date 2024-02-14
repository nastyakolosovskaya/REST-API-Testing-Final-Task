package Task30.UserApiRequests;

import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpHeaders;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

import static io.restassured.RestAssured.given;

public class GetUser {

    private final PropertyReader propertyReader = new PropertyReader();
    @SneakyThrows
    public String getUsers() {

        String response = (given()
                .baseUri(propertyReader.getProperty("basic.url"))
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonRead())
                .get(propertyReader.getProperty("users"))
                .then().log().all()
                .extract().body().asString());
        return response;
    }
}
