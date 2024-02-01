package Task70.UploadUsersApiRequests;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storageApp.Helpers.PropertyReader;
import storageApp.Helpers.TokenSingleton;

import java.io.File;

public class UploadUser {

    private final PropertyReader propertyReader = new PropertyReader();
    static Logger logger = LoggerFactory.getLogger(UploadUser.class);

    @SneakyThrows
    public HttpPost postUserUploadRequest(String filePath) {

        HttpPost httpPostUser = new HttpPost(propertyReader.getProperty("basic.url") + propertyReader.getProperty("usersUpload"));
        httpPostUser.setHeader(HttpHeaders.AUTHORIZATION, "Bearer" + TokenSingleton.getSingletonWrite());

        final File file = new File(propertyReader.getProperty(filePath));
        final FileBody fileBody = new FileBody(file, ContentType.MULTIPART_FORM_DATA);
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.LEGACY);
        builder.addPart("file", fileBody);
        final HttpEntity entity = builder.build();
        httpPostUser.setEntity(entity);

        return httpPostUser;
    }

    @SneakyThrows
    public int postUserUploadResponse(String filePath) {

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            CloseableHttpResponse response = client.execute(postUserUploadRequest(filePath));
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
