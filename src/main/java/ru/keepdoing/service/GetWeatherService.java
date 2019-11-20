package ru.keepdoing.service;

import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.keepdoing.model.Weather;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

@Service
@PropertySource("providers.properties")
@PropertySource("keys.properties")
public class GetWeatherService {

    private static Logger LOGGER = LoggerFactory.getLogger("Properties Loader");
    private String temperatureTemplate;
    private String humidityTemplate;
    private String pressureTemplate;
    private String cityTemplate;
    private String url;
    private String cityUrl;
    private String apikey;
    private boolean twoStepRequest;
    private Environment environment;

    @Value("${providers}")
    private ArrayList<String> providers;

    @Autowired
    public GetWeatherService(Environment environment) {
        this.environment = environment;
    }

    public void setupProviderParams(String weatherProviderName) throws IOException {
        if (!providers.contains(weatherProviderName)) throw new IOException("Non existent provider name");

        temperatureTemplate = environment.getProperty(weatherProviderName + ".temp");
        humidityTemplate = environment.getProperty(weatherProviderName + ".hum");
        pressureTemplate = environment.getProperty(weatherProviderName + ".press");
        url = environment.getProperty(weatherProviderName + ".request.weather.url");
        apikey = environment.getProperty(weatherProviderName + ".key");
        twoStepRequest = "true".equals(environment.getProperty(weatherProviderName + ".two.step.request"));

        if (twoStepRequest) {
            cityUrl = environment.getProperty(weatherProviderName + ".request.city.url");
            cityTemplate = environment.getProperty(weatherProviderName + ".city");
        }
    }

    public Weather requestWeatherData(String city) throws IOException {
        Weather weather = new Weather(city);

        if (twoStepRequest) {
            String requestUrl = replacePlaceholder(cityUrl, city, apikey);
            LOGGER.info(requestUrl);
            String json = ExternalWeatherHttpService.getResponse(requestUrl);
            city = JsonPath.read(json, cityTemplate).toString();
            LOGGER.info("CITY ID - {}", city);
        }

        String requestUrl = replacePlaceholder(url, city, apikey);
        LOGGER.info(requestUrl);
        String json = ExternalWeatherHttpService.getResponse(requestUrl);

        weather.setTemperature(JsonPath.read(json, temperatureTemplate).toString());
        weather.setHumidity(JsonPath.read(json, humidityTemplate).toString());
        weather.setPressure(JsonPath.read(json, pressureTemplate).toString());
        return weather;
    }

    private String replacePlaceholder(String template, String... target) {
        return MessageFormat.format(template, target);
    }

    public ArrayList<String> getProviders() {
        return providers;
    }
}
