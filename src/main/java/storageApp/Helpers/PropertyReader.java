package storageApp.Helpers;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
    private final String PATH = "src/main/resources/credentials.properties";

    @SneakyThrows
    public String getUserName() {
        FileInputStream fis = new FileInputStream(PATH);
        Properties appProps = new Properties();
        appProps.load(fis);
        return appProps.getProperty("userName");
    }

    @SneakyThrows
    public String getPassword() {
        FileInputStream fis = new FileInputStream(PATH);
        Properties appProps = new Properties();
        appProps.load(fis);
        return appProps.getProperty("password");
    }

    @SneakyThrows
    public String getHost() {
        FileInputStream fis = new FileInputStream(PATH);
        Properties appProps = new Properties();
        appProps.load(fis);
        return appProps.getProperty("host");
    }

    @SneakyThrows
    public String getPort() {
        FileInputStream fis = new FileInputStream(PATH);
        Properties appProps = new Properties();
        appProps.load(fis);
        return appProps.getProperty("port");
    }
}
