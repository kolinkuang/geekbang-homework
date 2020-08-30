package l014;

import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class AuthToken {

    private static final long DEFAULT_EXPIRED_TIME_INTERVAL = 60 * 1000;

    private final String token;
    private final long createTime;

    public AuthToken(String token, long createTime) {
        this.token = token;
        this.createTime = createTime;
    }

//    public AuthToken(String token, long createTime, long expiredTimeInterval) {
//        this.token = token;
//        this.createTime = createTime;
//        this.expiredTimeInterval = expiredTimeInterval;
//    }

    public static AuthToken create(String baseUrl, long createTime, Map<String, String> params){
        String id = params.get("id");
        String appId = params.get("appId");
        String password = params.get("password");
        String rawValue = String.format("%s?id=%s&appid=%s&pwd=%s&ts=%s", baseUrl, id, appId, password, createTime);
        return new AuthToken(generateToken(rawValue), createTime);
    }

    private static String generateToken(String rawValue) {
        byte[] encoded = Base64.getEncoder().encode(rawValue.getBytes());
        return Arrays.toString(encoded);
    }

    public String getToken() {
        return this.token;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - this.createTime >= DEFAULT_EXPIRED_TIME_INTERVAL;
    }

    public boolean match(AuthToken authToken) {
        return this.token.equals(authToken.getToken());
    }

}
