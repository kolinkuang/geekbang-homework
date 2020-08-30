package l014;

import java.util.HashMap;
import java.util.Map;

public class DefaultApiAuthenticator implements ApiAuthenticator {

    private final CredentialStorage credentialStorage;

    public DefaultApiAuthenticator() {
        this.credentialStorage = new MysqlCredentialStorage();
    }

//    public DefaultApiAuthenticatorImpl(CredentialStorage credentialStorage) {
//        this.credentialStorage = credentialStorage;
//    }

    @Override
    public void auth(String url) {
        ApiRequest apiRequest = ApiRequest.buildFromUrl(url);
        auth(apiRequest);
    }

    @Override
    public void auth(ApiRequest apiRequest) {
        String appId = apiRequest.getAppId();
        String token = apiRequest.getToken();
        long timestamp = apiRequest.getTimestamp();
        String baseUrl = apiRequest.getBaseUrl();

        AuthToken clientAuthToken = new AuthToken(token, timestamp);
        if (clientAuthToken.isExpired()) {
            throw new RuntimeException("Token is expired.");
        }

        String password = credentialStorage.getPasswordByAppId(appId);
        Map<String, String> params = new HashMap<String, String>() {{
            put("appId", appId);
            put("password", password);
        }};
        AuthToken serverAuthToken = AuthToken.create(baseUrl, timestamp, params);
        if (!serverAuthToken.match(clientAuthToken)) {
            System.out.println("Client: " + clientAuthToken.getToken());
            System.out.println("Server: " + serverAuthToken.getToken());
            throw new RuntimeException("Token verification failed.");
        }
    }

}
