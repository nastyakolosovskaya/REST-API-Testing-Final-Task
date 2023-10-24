package storageApp.helpers;

import storageApp.data.HttpAuthenticationResponse;

public class TokenSingleton {

    public static HttpAuthenticationResponse httpAuthenticationResponse;

    TokenSingleton() {
    }

    private static String SINGLETON_READ;
    private static String SINGLETON_WRITE;

    private static HttpAuthenticateClient httpAuthenticateClient = new HttpAuthenticateClient();


    public static HttpAuthenticationResponse initialize() {
        if (httpAuthenticationResponse == null) {

            SINGLETON_READ = httpAuthenticateClient.getHttpConnection("read").getAccess_token();
            SINGLETON_WRITE = httpAuthenticateClient.getHttpConnection("write").getAccess_token();

        }
        return httpAuthenticationResponse;
    }

    public static String getSingletonRead() {
        return SINGLETON_READ;
    }
    public static String getSingletonWrite() {
        return SINGLETON_WRITE;
    }
}
