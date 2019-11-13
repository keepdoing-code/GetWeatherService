package ru.keepdoing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("providers.properties")
public class ProvidersConfig {

    @Value("${providers}")
    private String[] providers;

    public String[] getProviders(){
        return providers;
    }
}
