package storageApp;

import storageApp.helpers.TokenSingleton;

public class userStorageApp {

    public static void main(String[] args) {

        TokenSingleton.getInstance("read");
    }
}


