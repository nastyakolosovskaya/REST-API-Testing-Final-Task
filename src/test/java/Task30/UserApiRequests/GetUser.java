package Task30.UserApiRequests;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

public class GetUser {

    private final PropertyReader propertyReader = new PropertyReader();
    static Logger logger = LoggerFactory.getLogger(GetUser.class);

    @SneakyThrows
    public HttpGet getUsersRequest() {
        TokenSingleton.initialize();

        HttpGet request = new HttpGet(propertyReader.getProperty("basic.url") + propertyReader.getProperty("users"));
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonRead());

        return request;
    }

    @SneakyThrows
    public String getUserResponse(int status) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            try (CloseableHttpResponse response = client.execute(getUsersRequest())) {
                int statusCode = response.getCode();

                if (status != statusCode) {
                    logger.info("Wrong status code: " + status);
                }
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                logger.info(responseBody);

                return responseBody;
            }
        }
    }
}

