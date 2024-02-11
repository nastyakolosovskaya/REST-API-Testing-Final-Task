package storageApp.Utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import storageApp.Data.TokenData;

public class AuthenticationResponseUtility {

    private AuthenticationResponseUtility() {
    }

    @SneakyThrows
    public static TokenData getAuthenticationResponse(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, TokenData.class);
    }
}
