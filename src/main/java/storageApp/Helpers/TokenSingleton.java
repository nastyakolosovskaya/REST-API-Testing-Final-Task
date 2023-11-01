package storageApp.Helpers;

public class TokenSingleton {

    TokenSingleton() {
    }

    public static TokenSingleton tokenSingleton;
    private static String SINGLETON_READ;
    private static String SINGLETON_WRITE;

    public static TokenSingleton initialize() {
        if (tokenSingleton == null) {
            tokenSingleton = new TokenSingleton();
            HttpAuthenticateClient httpAuthenticateClient = new HttpAuthenticateClient();
            SINGLETON_READ = httpAuthenticateClient.getHttpConnection("read").getAccessToken();
            SINGLETON_WRITE = httpAuthenticateClient.getHttpConnection("write").getAccessToken();
        }
        return tokenSingleton;
    }

    public static String getSingletonRead() {
        return SINGLETON_READ;
    }

    public static String getSingletonWrite() {
        return SINGLETON_WRITE;
    }
}
