package l014;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DefaultApiAuthenticatorTest {

    private ApiAuthenticator apiAuthenticator;

    @BeforeEach
    public void setup() {
        apiAuthenticator = new DefaultApiAuthenticator();
    }

    @Test
    void testAuth() {
        String url = prepareUrl();
        assertDoesNotThrow(() -> apiAuthenticator.auth(url));
    }

    private String prepareUrl() {
        String appId = "algo";
        String password = "my.password";
        long timestamp = System.currentTimeMillis();
        String rawValue = String.format("http://www.xzg.com/user?appid=%s&pwd=%s&ts=%s", appId, password, timestamp);
        byte[] encoded = Base64.getEncoder().encode(rawValue.getBytes());
        String token = new String(encoded, StandardCharsets.UTF_8).replace("=", "");
        return String.format("http://www.xzg.com/user?appid=%s&token=%s&ts=%s", appId, token, timestamp);
    }

}