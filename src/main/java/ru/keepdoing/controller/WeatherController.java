package ru.keepdoing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.keepdoing.config.ProvidersConfig;
import ru.keepdoing.model.Weather;
import ru.keepdoing.service.GetWeatherService;
import ru.keepdoing.utils.PropertiesLoader;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("weather")
public class WeatherController {

    @Autowired
    private ProvidersConfig config;

    @RequestMapping("get")
    public Weather getWeather(@RequestParam("city") String city, @RequestParam("provider") String provider) throws IOException {
        GetWeatherService weatherService = new GetWeatherService(provider);
        return weatherService.requestWeatherData(city);
    }

    @RequestMapping("providers")
    public String[] heartBeat() {
        return config.getProviders();
    }

    @RequestMapping("test")
    public Weather getModel() {
        return new Weather("Moscow", "10", "75", "1023");
    }

    @RequestMapping("property")
    public String getVal(@RequestParam("val") String prop) {
        return PropertiesLoader.get(prop);
    }

    @RequestMapping("map")
    public Map getMap() {
        return config.getMap();
    }

}
