package Task30.UserApiRequests;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

public class PostUser {

    private final PropertyReader propertyReader = new PropertyReader();
    static Logger logger = LoggerFactory.getLogger(PostUser.class);

    @SneakyThrows
    public HttpPost postUserRequest(String json) {

        HttpPost httpPostUser = new HttpPost(propertyReader.getProperty("basic.url") + propertyReader.getProperty("users"));
        httpPostUser.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonWrite());

        final StringEntity entity = new StringEntity(json);
        httpPostUser.setEntity(entity);
        httpPostUser.setHeader("Accept", "application/json");
        httpPostUser.setHeader("Content-type", "application/json");
        return httpPostUser;
    }

    @SneakyThrows
    public int postUserResponse(String json) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            try (
                    CloseableHttpResponse response = client.execute(postUserRequest(json))) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                logger.info(responseBody);
                int statusCode = response.getCode();
                return statusCode;
            }
        }
    }
}
