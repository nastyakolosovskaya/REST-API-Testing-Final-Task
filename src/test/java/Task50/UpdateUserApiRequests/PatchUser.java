package Task50.UpdateUserApiRequests;

import Helpers.UpdateUsersJsonUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpHeaders;
import org.json.JSONObject;
import storageApp.Data.User;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

import static io.restassured.RestAssured.given;

public class PatchUser {

    private final PropertyReader propertyReader = new PropertyReader();
    @SneakyThrows
    public int patchUser(User user, User updatedUser) {

        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonUsers = UpdateUsersJsonUtility.getJsonObject(user, updatedUser);
        JsonNode jsonUser = mapper.readTree(String.valueOf(jsonUsers));

        int response = (given()
                .baseUri(propertyReader.getProperty("basic.url"))
                .body(jsonUser)
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonWrite())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .patch(propertyReader.getProperty("users"))
                .then().log().all()
                .extract().statusCode());
        return response;
    }
}
