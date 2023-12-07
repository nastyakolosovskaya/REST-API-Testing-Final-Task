package storageApp.ApiRequests;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

public class PostZipCodes {

    private final PropertyReader propertyReader = new PropertyReader();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SneakyThrows
    public HttpPost postZipCodeRequest(String json) {

        HttpPost httpPostAddZipCode = new HttpPost(propertyReader.getProperty("basic.url") + propertyReader.getProperty("zipcodes.expand"));
        httpPostAddZipCode.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonWrite());

        final StringEntity entity = new StringEntity(json);
        httpPostAddZipCode.setEntity(entity);
        httpPostAddZipCode.setHeader("Accept", "application/json");
        httpPostAddZipCode.setHeader("Content-type", "application/json");
        return httpPostAddZipCode;
    }

    @SneakyThrows
        public int postZipCodeResponse(String json) {
            CloseableHttpClient client = HttpClients.createDefault();
            try (
                    CloseableHttpResponse response = client.execute(postZipCodeRequest(json))) {
                HttpEntity entity1 = response.getEntity();
                String responseBody = EntityUtils.toString(entity1);
                System.out.println(responseBody);
                int statusCode = response.getStatusLine().getStatusCode();
                return statusCode;
            }
        }
    }
