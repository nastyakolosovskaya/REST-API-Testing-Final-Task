package Task20.ZipCodesApiRequests;

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

public class PostZipCodes {

    private final PropertyReader propertyReader = new PropertyReader();
    static Logger logger = LoggerFactory.getLogger(PostZipCodes.class);

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
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            try (
                    CloseableHttpResponse response = client.execute(postZipCodeRequest(json))) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                logger.info(responseBody);
                int statusCode = response.getCode();
                return statusCode;
            }
        }
    }

    public int numberOfOccurrences(String source, String sentence) {
        int occurrences = 0;

        if (source.contains(sentence)) {
            int withSentenceLength    = source.length();
            int withoutSentenceLength = source.replace(sentence, "").length();
            occurrences = (withSentenceLength - withoutSentenceLength) / sentence.length();
        }

        return occurrences;
    }
}
