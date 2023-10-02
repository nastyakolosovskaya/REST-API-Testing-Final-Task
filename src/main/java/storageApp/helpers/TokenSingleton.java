package storageApp.helpers;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TokenSingleton {

    private TokenSingleton() {
    }

    static TokenSingleton SINGLETON;

    public static TokenSingleton getInstance(String value) {
        if (SINGLETON == null) {
            SINGLETON = new TokenSingleton();
            SINGLETON.initialize(value);
        }
        return SINGLETON;
    }

    @SneakyThrows
    private void initialize(String scopeValue) {

        HttpAuthenticator httpAuthenticator = new HttpAuthenticator();
        String encodedAuthorizedUser = httpAuthenticator.getAuthentication();
        String urlParameters = "grant_type=client_credentials&scope=" + scopeValue;
        HttpURLConnection conn = getHttpURLConnection(urlParameters, encodedAuthorizedUser);

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response);
    }
    @SneakyThrows
    private static HttpURLConnection getHttpURLConnection(String urlParameters, String encodedAuthorizedUser){
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        String request = "http://localhost:4044/oauth/token";
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Basic " + encodedAuthorizedUser);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }
        return conn;
    }
}
