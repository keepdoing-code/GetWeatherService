package ru.keepdoing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.keepdoing.utils.PropertiesLoader;

import java.util.Map;

@Component
@PropertySource("providers.properties")
public class ProvidersConfig {

    @Value("${providers}")
    private String[] providers;

    public String[] getProviders() {
        return providers;
    }

    @Value("#{${map.test}}")
    private Map<String, String> map;


    public Map<String, String> getMap() {
        return map;
    }
}
