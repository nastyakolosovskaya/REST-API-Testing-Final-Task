package Task40.UserFilterApiRequests;

import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpHeaders;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

import static io.restassured.RestAssured.given;

public class GetFilteredUsers {

    private final PropertyReader propertyReader = new PropertyReader();

    @SneakyThrows
    public String getUsersFiltered(String parameter, String value) {

        String response = (given()
                .baseUri(propertyReader.getProperty("basic.url"))
                .param(parameter, value)
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonRead())
                .get(propertyReader.getProperty("users"))
                .then().log().all()
                .extract().body().asString());
        return response;
    }
}
