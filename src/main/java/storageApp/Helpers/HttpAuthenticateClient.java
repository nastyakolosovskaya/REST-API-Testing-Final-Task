package storageApp.Helpers;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;
import storageApp.Data.TokenData;
import storageApp.Utilities.AuthenticationResponseUtility;

import java.net.URI;

public class HttpAuthenticateClient {

    @SneakyThrows
    public TokenData getHttpConnection(String value) {

        PropertyReader propertyReader = new PropertyReader();
        String username = propertyReader.getProperty("userName");
        String password = propertyReader.getProperty("password");
        String host = propertyReader.getProperty("host");
        String port = propertyReader.getProperty("port");

        HttpPost httppost = new HttpPost(propertyReader.getProperty("basic.url") + propertyReader.getProperty("auth.token"));
        BasicCredentialsProvider creds = new BasicCredentialsProvider();
        creds.setCredentials(new AuthScope(host, Integer.parseInt(port)),
                new UsernamePasswordCredentials(username, password.toCharArray()));

        try (CloseableHttpClient httpclient = HttpClients.
                custom().
                setDefaultCredentialsProvider(creds).
                build()) {

            URI uriBuilder = new URIBuilder(httppost.getUri()).
                    addParameter("grant_type", "client_credentials").
                    addParameter("scope", value).build();
            httppost.setUri(uriBuilder);

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                return AuthenticationResponseUtility.getAuthenticationResponse(responseBody);
            }
        }
    }
}
