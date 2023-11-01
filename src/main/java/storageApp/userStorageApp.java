package storageApp;

import static storageApp.Helpers.TokenSingleton.*;

public class userStorageApp {

    public static void main(String[] args) {

        initialize();
        System.out.println(getSingletonWrite());
        System.out.println(getSingletonRead());
        System.out.println(getSingletonWrite());
        System.out.println(getSingletonRead());

    }
}
