package Task40.UserFilterApiRequests;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

import java.net.URI;

public class GetFilteredUsers {

    private final PropertyReader propertyReader = new PropertyReader();

    static Logger logger = LoggerFactory.getLogger(GetFilteredUsers.class);

    @SneakyThrows
    public String getUsersFilteredRequest(String parameter, String value) {
        TokenSingleton.initialize();

        HttpGet request = new HttpGet(propertyReader.getProperty("basic.url") + propertyReader.getProperty("users"));
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonRead());

        URI uriBuilder = new URIBuilder(request.getUri()).
                addParameter(parameter, value).build();
        request.setUri(uriBuilder);

        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            try (CloseableHttpResponse response = client.execute(request)) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                logger.info(responseBody);

                return responseBody;
            }
        }
    }
}
