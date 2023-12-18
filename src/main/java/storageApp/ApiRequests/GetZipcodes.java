package storageApp.ApiRequests;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;


public class GetZipcodes {

    private final PropertyReader propertyReader = new PropertyReader();
    static Logger logger = LoggerFactory.getLogger(GetZipcodes.class);

    @SneakyThrows
    public HttpGet getZipCodesRequest() {
        TokenSingleton.initialize();

        HttpGet request = new HttpGet(propertyReader.getProperty("basic.url") + propertyReader.getProperty("zipcodes"));
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonRead());

        return request;
    }

    @SneakyThrows
    public int getZipCodesStatusCode() {
        CloseableHttpClient client = HttpClients.createDefault();
        try (CloseableHttpResponse response = client.execute(getZipCodesRequest())) {
            int statusCode = response.getCode();
            return statusCode;
        }
    }

    @SneakyThrows
    public String getZipCodesResponse(int status) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = client.execute(getZipCodesRequest())) {
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
