package Task60.DeleteUserApiRequests;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storageApp.Data.User;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

public class DeleteUser {

    private final PropertyReader propertyReader = new PropertyReader();
    static Logger logger = LoggerFactory.getLogger(DeleteUser.class);

    public HttpDelete deleteUserRequest(String json) {

        HttpDelete httpDeleteUser = new HttpDelete(propertyReader.getProperty("basic.url") + propertyReader.getProperty("users"));
        httpDeleteUser.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonWrite());

        final StringEntity entity = new StringEntity(json);
        httpDeleteUser.setEntity(entity);
        httpDeleteUser.setHeader("Accept", "application/json");
        httpDeleteUser.setHeader("Content-type", "application/json");
        return httpDeleteUser;
    }

    @SneakyThrows
    public int deleteUserResponse(User user) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(user);
            CloseableHttpResponse response = client.execute(deleteUserRequest(jsonString));
            {
                logger.info(String.valueOf(response));
                int statusCode = response.getCode();
                return statusCode;
            }
        }
    }
}
