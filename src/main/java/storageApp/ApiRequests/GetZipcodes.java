package storageApp.ApiRequests;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;


public class GetZipcodes {

    private final PropertyReader propertyReader = new PropertyReader();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            int statusCode = response.getStatusLine().getStatusCode();
            return statusCode;
        }
    }

    @SneakyThrows
    public String getZipCodesResponse() {
        CloseableHttpClient client = HttpClients.createDefault();
        try (CloseableHttpResponse response = client.execute(getZipCodesRequest())) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            //System.out.println(ZipCodesDataUtility.retrieveResourceFromResponse(response, ZipCodesData.class));
            //logger.info("Response -> {}", EntityUtils.toString(entity));
            return responseBody;
        }
    }
}
