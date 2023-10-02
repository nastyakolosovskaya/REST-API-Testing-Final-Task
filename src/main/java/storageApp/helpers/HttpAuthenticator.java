package storageApp.helpers;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Base64;

public class HttpAuthenticator {

    String login = "0oa157tvtugfFXEhU4x7";
    String password = "X7eBCXqlFC7x-mjxG5H91IRv_Bqe1oq7ZwXNA8aq";

    public String getAuthentication() {

        Authenticator.setDefault(new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password.toCharArray());
            }
        });

        String valueToEncode = login + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
}
