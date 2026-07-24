package org.example.configuracon;

public class SSLConfig implements ISSLConfig {

    private IConfigReader configReader;

    public SSLConfig(IConfigReader configReader) {
        this.configReader = configReader;
    }

    @Override
    public String getKeyStorePath() {
        return configReader.getString("ssl.keystore.path");
    }

    @Override
    public String getKeyStorePassword() {
        return configReader.getString("ssl.keystore.password");
    }
}