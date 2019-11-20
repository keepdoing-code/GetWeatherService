package ru.keepdoing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.keepdoing.model.Weather;
import ru.keepdoing.service.GetWeatherService;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("weather")
public class WeatherController {

    @Autowired
    private GetWeatherService weatherService;

    @RequestMapping("get")
    public Weather getWeather(@RequestParam("city") String city, @RequestParam("provider") String provider) throws IOException {
        weatherService.setupProviderParams(provider);
        return weatherService.requestWeatherData(city);
    }

    @RequestMapping("providers")
    public ArrayList<String> getProviders() {
        return weatherService.getProviders();
    }

    @RequestMapping("test")
    public Weather getModel() {
        return new Weather("Moscow", "10", "75", "1023");
    }

}
