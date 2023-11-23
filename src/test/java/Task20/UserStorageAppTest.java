package Task20;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static storageApp.Helpers.TokenSingleton.getSingletonRead;
import static storageApp.Helpers.TokenSingleton.getSingletonWrite;

public class UserStorageAppTest {

    private final PropertyReader propertyReader = new PropertyReader();
    private final String URL = "http://" + propertyReader.getProperty("host") + ":" + propertyReader.getProperty("port");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeEach
    void setup() {
        TokenSingleton.initialize();
    }

    @SneakyThrows
    @Test
    void getAllAvailableZipCodeTest() {

        HttpGet request = new HttpGet(URL + "/zip-codes");
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + getSingletonRead());

        CloseableHttpClient client = HttpClients.createDefault();
        try (CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();

            String responseBody = EntityUtils.toString(entity);
            System.out.println(responseBody);

            int statusCode = response.getStatusLine().getStatusCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
            logger.debug("Response -> {}", EntityUtils.toString(entity));
        }
    }

    @Test
    @SneakyThrows
    void addZipCodeTest() {

        HttpPost httpPostAddZipCode = new HttpPost(URL + "/zip-codes/expand");
        httpPostAddZipCode.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + getSingletonWrite());

        final String json = "[" + "11111" + "]";
        final StringEntity entity = new StringEntity(json);
        httpPostAddZipCode.setEntity(entity);
        httpPostAddZipCode.setHeader("Accept", "application/json");
        httpPostAddZipCode.setHeader("Content-type", "application/json");

        CloseableHttpClient client = HttpClients.createDefault();
        try (CloseableHttpResponse response = client.execute(httpPostAddZipCode)) {
            HttpEntity entity1 = response.getEntity();

            String responseBody = EntityUtils.toString(entity1);
            System.out.println(responseBody);
            int statusCode = response.getStatusLine().getStatusCode();

            assertThat(statusCode, equalTo(HttpStatus.SC_CREATED));
        }
    }

    @Test
    @SneakyThrows
    void addSeveralZipCodesTest() {

        HttpPost httpPostAddSeveralZipCodes = new HttpPost(URL + "/zip-codes/expand");
        httpPostAddSeveralZipCodes.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + getSingletonWrite());

        final String json = "[" + "11111" + "," + "2222" + "]";
        final StringEntity entity = new StringEntity(json);
        httpPostAddSeveralZipCodes.setEntity(entity);
        httpPostAddSeveralZipCodes.setHeader("Accept", "application/json");
        httpPostAddSeveralZipCodes.setHeader("Content-type", "application/json");

        CloseableHttpClient client = HttpClients.createDefault();
        try (CloseableHttpResponse response = client.execute(httpPostAddSeveralZipCodes)) {
            HttpEntity entity1 = response.getEntity();

            String responseBody = EntityUtils.toString(entity1);
            System.out.println(responseBody);
            int statusCode = response.getStatusLine().getStatusCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_CREATED));
        }
    }

    @Test
    @SneakyThrows
    void duplicateValidationInZipCodeListTest() {

        HttpPost httpPost = new HttpPost(URL + "/zip-codes/expand");
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + getSingletonWrite());

        final String json = "[" + "33333" + "," + "33333" + "]";
        final StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpClient client = HttpClients.createDefault();
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            HttpEntity entity1 = response.getEntity();

            String responseBody = EntityUtils.toString(entity1);
            System.out.println(responseBody);
            int statusCode = response.getStatusLine().getStatusCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_CREATED));
        }
    }

    @Test
    @SneakyThrows
    void duplicationsAlreadyExistInZipCodeListTest() {

        HttpPost httpPost = new HttpPost(URL + "/zip-codes/expand");
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + getSingletonWrite());

        final String json = "[" + "22222" + "]";
        final StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpClient client = HttpClients.createDefault();
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            HttpEntity entity1 = response.getEntity();

            String responseBody = EntityUtils.toString(entity1);
            System.out.println(responseBody);
            int statusCode = response.getStatusLine().getStatusCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_CREATED));
        }
    }
}
