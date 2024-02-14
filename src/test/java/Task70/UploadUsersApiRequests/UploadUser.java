package Task70.UploadUsersApiRequests;

import lombok.SneakyThrows;
import org.apache.hc.core5.http.HttpHeaders;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UploadUser {

    private final PropertyReader propertyReader = new PropertyReader();

    @SneakyThrows
    public int postUserUpload(String filePath) {

        int response = (given()
                .baseUri(propertyReader.getProperty("basic.url"))
                .multiPart("file",new File(propertyReader.getProperty(filePath)))
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonWrite())
                .post(propertyReader.getProperty("usersUpload"))
                .then().log().all()
                .extract().statusCode());
        return response;
    }
}
