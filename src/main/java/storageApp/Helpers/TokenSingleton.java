package storageApp.Helpers;

public class TokenSingleton {

    TokenSingleton() {
    }

    public static TokenSingleton tokenSingleton;
    private static String singletonRead;
    private static String singletonWrite;

    public static TokenSingleton initialize() {
        if (tokenSingleton == null) {
            tokenSingleton = new TokenSingleton();
            HttpAuthenticateClient httpAuthenticateClient = new HttpAuthenticateClient();
            singletonRead = httpAuthenticateClient.getHttpConnection("read").getAccessToken();
            singletonWrite = httpAuthenticateClient.getHttpConnection("write").getAccessToken();
        }
        return tokenSingleton;
    }

    public static String getSingletonRead() {
        return singletonRead;
    }

    public static String getSingletonWrite() {
        return singletonWrite;
    }
}
