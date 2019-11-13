package ru.keepdoing.service;

import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.keepdoing.model.Weather;
import ru.keepdoing.utils.PropertiesLoader;
import ru.keepdoing.utils.UrlBuilder;

import java.io.IOException;

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

    public GetWeatherService(String weatherProviderName) {
        temperatureTemplate = PropertiesLoader.get(weatherProviderName + ".temp");
        humidityTemplate = PropertiesLoader.get(weatherProviderName + ".hum");
        pressureTemplate = PropertiesLoader.get(weatherProviderName + ".press");
        url = PropertiesLoader.get(weatherProviderName + ".request.weather.url");
        apikey = PropertiesLoader.get(weatherProviderName + ".key");
        twoStepRequest = PropertiesLoader.getBool(weatherProviderName + ".two.step.request");

        if (twoStepRequest) {
            cityUrl = PropertiesLoader.get(weatherProviderName + ".request.city.url");
            cityTemplate = PropertiesLoader.get(weatherProviderName + ".city");
        }
    }

    public Weather requestWeatherData(String city) throws IOException {
        Weather weather = new Weather(city);

        if (twoStepRequest) {
            String requestUrl = UrlBuilder.replacePlaceholder(cityUrl, city, apikey);
            LOGGER.info(requestUrl);
            String json = ExternalWeatherHttpService.getResponse(requestUrl);
            city = JsonPath.read(json, cityTemplate).toString();
            LOGGER.info("CITY ID - {}", city);
        }

        String requestUrl = UrlBuilder.replacePlaceholder(url, city, apikey);
        LOGGER.info(requestUrl);
        String json = ExternalWeatherHttpService.getResponse(requestUrl);

        weather.setTemperature(JsonPath.read(json, temperatureTemplate).toString());
        weather.setHumidity(JsonPath.read(json, humidityTemplate).toString());
        weather.setPressure(JsonPath.read(json, pressureTemplate).toString());
        return weather;
    }
}
