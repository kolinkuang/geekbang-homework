package l014;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultApiAuthenticatorImplTest {

    @Test
    void testAuth() {
        String url = "http://www.xzg.com/user?id=123&appid=abc&pwd=my.password&ts=1561523435";
        ApiAuthenticator apiAuthenticator = new DefaultApiAuthenticatorImpl();
        assertDoesNotThrow(() -> apiAuthenticator.auth(url));
    }

}