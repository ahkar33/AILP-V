package com.ace.ailpv;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "secret")
public class SecretConfigProperties {
    private String dbuser;
    private String dbpass;
    private String defaultPin;
    private String defaultStdPassword;
    private String defaultTchPassword;
    
    public String getDefaultPin() {
        return defaultPin;
    }
    public String getDefaultStdPassword() {
        return defaultStdPassword;
    }
    public void setDefaultStdPassword(String defaultStdPassword) {
        this.defaultStdPassword = defaultStdPassword;
    }
    public String getDefaultTchPassword() {
        return defaultTchPassword;
    }
    public void setDefaultTchPassword(String defaultTchPassword) {
        this.defaultTchPassword = defaultTchPassword;
    }
    public void setDefaultPin(String defaultPin) {
        this.defaultPin = defaultPin;
    }
    
    
    public String getDbuser() {
        return dbuser;
    }
    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
    }
    public String getDbpass() {
        return dbpass;
    }
    public void setDbpass(String dbpass) {
        this.dbpass = dbpass;
    }

    
    
}
