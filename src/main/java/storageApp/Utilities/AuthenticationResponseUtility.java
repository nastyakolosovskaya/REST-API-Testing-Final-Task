package storageApp.Utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import storageApp.Data.HttpAuthenticationResponse;

public class AuthenticationResponseUtility {

    private AuthenticationResponseUtility() {
    }

    @SneakyThrows
    public static HttpAuthenticationResponse getAuthenticationResponse(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, HttpAuthenticationResponse.class);
    }
}
