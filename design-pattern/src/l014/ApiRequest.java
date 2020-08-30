package l014;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiRequest {

    private final String baseUrl;
    private final String token;
    private final String appId;
    private final long timestamp;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getToken() {
        return token;
    }

    public String getAppId() {
        return appId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public ApiRequest(String baseUrl, String token, String appId, long timestamp) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.appId = appId;
        this.timestamp = timestamp;
    }

    public static ApiRequest buildFromUrl(String url) {
        String[] paths = url.split("/?");
        String baseUrl = paths[0];
        Map<String, String> params = Arrays.stream(paths[1].split("&"))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(keyValue -> keyValue[0], keyValue -> keyValue[1]));
        String appId = params.get("appid");
        String token = params.get("token");
        long timestamp = Long.parseLong(params.get("ts"));
        return new ApiRequest(baseUrl, token, appId, timestamp);
    }

}