package storageApp;

import storageApp.helpers.TokenSingleton;

public class userStorageApp {

    public static void main(String[] args) {

        TokenSingleton.initialize();
        System.out.println(TokenSingleton.getSingletonWrite());
        System.out.println(TokenSingleton.getSingletonRead());
        System.out.println(TokenSingleton.getSingletonWrite());
        System.out.println(TokenSingleton.getSingletonRead());

    }
}
