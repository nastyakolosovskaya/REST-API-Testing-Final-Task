package Task50.UpdateUserApiRequests;

import Helpers.UpdateUsersJsonUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storageApp.Data.User;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

public class PatchUser {

    private final PropertyReader propertyReader = new PropertyReader();
    static Logger logger = LoggerFactory.getLogger(PatchUser.class);

    public HttpPatch patchUserRequest(String json) {

        HttpPatch httpPutUser = new HttpPatch(propertyReader.getProperty("basic.url") + propertyReader.getProperty("users"));
        httpPutUser.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonWrite());

        final StringEntity entity = new StringEntity(json);
        httpPutUser.setEntity(entity);
        httpPutUser.setHeader("Accept", "application/json");
        httpPutUser.setHeader("Content-type", "application/json");
        return httpPutUser;
    }

    @SneakyThrows
    public int patchUserResponse(User user, User updatedUser) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonUsers = UpdateUsersJsonUtility.getJsonObject(user, updatedUser);
            JsonNode jsonUser = mapper.readTree(String.valueOf(jsonUsers));

            CloseableHttpResponse response = client.execute(patchUserRequest(String.valueOf(jsonUser)));
            {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                logger.info(responseBody);
                int statusCode = response.getCode();
                return statusCode;
            }
        }
    }
}
