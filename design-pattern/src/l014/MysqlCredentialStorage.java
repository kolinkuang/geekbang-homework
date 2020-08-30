package l014;

public class MysqlCredentialStorage implements CredentialStorage {

    @Override
    public String getPasswordByAppId(String appId) {
        return "my.password";
    }

}
