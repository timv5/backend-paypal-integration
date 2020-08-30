package com.paypal.integration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final PaypalConfig paypalConfig = new PaypalConfig();

    @Data
    public static class PaypalConfig {

        private String clientId;
        private String clientSecret;
        private String mode;
        private String method;
        private String intent;
        private String quantity;

    }

}
