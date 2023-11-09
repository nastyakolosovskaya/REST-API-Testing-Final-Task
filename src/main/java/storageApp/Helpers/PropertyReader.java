package storageApp.Helpers;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
    private final String PATH = "src/main/resources/credentials.properties";

    @SneakyThrows
    public String getProperty(String property) {
        FileInputStream fis = new FileInputStream(PATH);
        Properties appProps = new Properties();
        appProps.load(fis);
        return appProps.getProperty(property);
    }
}
