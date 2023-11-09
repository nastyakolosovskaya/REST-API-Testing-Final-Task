package storageApp.Helpers;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import storageApp.Data.HttpAuthenticationResponse;
import storageApp.Utilities.AuthenticationResponseUtility;

import java.net.*;
public class HttpAuthenticateClient {

    @SneakyThrows
    public HttpAuthenticationResponse getHttpConnection(String value) {

        PropertyReader propertyReader = new PropertyReader();
        String username = propertyReader.getProperty("userName");
        String password = propertyReader.getProperty("password");
        String host = propertyReader.getProperty("host");
        String port = propertyReader.getProperty("port");

        HttpPost httppost = new HttpPost("http://" + host + ":" + port + "/oauth/token");
        CredentialsProvider creds = new BasicCredentialsProvider();
        creds.setCredentials(new AuthScope(host, Integer.parseInt(port)),
                new UsernamePasswordCredentials(username, password));

        CloseableHttpClient httpclient = HttpClients.
                custom().
                setDefaultCredentialsProvider(creds).
                build();

        URI uriBuilder = new URIBuilder(httppost.getURI()).
                addParameter("grant_type", "client_credentials").
                addParameter("scope", value).build();
        httppost.setURI(uriBuilder);

        try (CloseableHttpResponse response = httpclient.execute(httppost)) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            return AuthenticationResponseUtility.getAuthenticationResponse(responseBody);
        }
    }
}
