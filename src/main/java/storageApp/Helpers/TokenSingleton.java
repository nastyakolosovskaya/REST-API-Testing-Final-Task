package storageApp.Helpers;

import lombok.Getter;

public class TokenSingleton {

    TokenSingleton() {
    }

    public static TokenSingleton tokenSingleton;
    @Getter
    private static String singletonRead;
    @Getter
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
}
