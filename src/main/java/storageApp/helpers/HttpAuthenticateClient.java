package storageApp.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import storageApp.data.HttpAuthenticationResponse;

import java.io.InputStream;
import java.net.*;

public class HttpAuthenticateClient {

    String username = "0oa157tvtugfFXEhU4x7";
    String password = "X7eBCXqlFC7x-mjxG5H91IRv_Bqe1oq7ZwXNA8aq";
    String host = "localhost";
    int port = 4044;

    @SneakyThrows
    public HttpAuthenticationResponse getHttpConnection(String value) {

        HttpPost httppost = new HttpPost("http://localhost:4044/oauth/token");
        CredentialsProvider creds = new BasicCredentialsProvider();
        creds.setCredentials(new AuthScope(host, port),
                new UsernamePasswordCredentials(username, password));

        CloseableHttpClient httpclient = HttpClients.
                custom().
                setDefaultCredentialsProvider(creds).
                build();

        URI uriBuilder = new URIBuilder(httppost.getURI()).
                addParameter("grant_type", "client_credentials").
                addParameter("scope", value).build();
        httppost.setURI(uriBuilder);

        CloseableHttpResponse response = httpclient.execute(httppost);
        try {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            ObjectMapper objectMapper = new ObjectMapper();

            HttpAuthenticationResponse authenticationResponse = objectMapper.readValue(responseBody, HttpAuthenticationResponse.class);

            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    //Printing the status line
                    //System.out.println(response.getStatusLine());
                } finally {
                    instream.close();
                }
            }
            return authenticationResponse;
        } finally {
            response.close();
        }
    }
}
