package Task60.DeleteUserApiRequests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpHeaders;
import storageApp.Data.User;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

import static io.restassured.RestAssured.given;

public class DeleteUser {

    private final PropertyReader propertyReader = new PropertyReader();
    @SneakyThrows
    public int deleteUser(User user) {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);

        int response = (given()
                .baseUri(propertyReader.getProperty("basic.url"))
                .body(jsonString)
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonWrite())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .delete(propertyReader.getProperty("users"))
                .then().log().all()
                .extract().statusCode());
        return response;
    }
}
