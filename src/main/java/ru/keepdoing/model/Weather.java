package ru.keepdoing.model;

public class Weather {
    private static final String temperatureUnit = "\u00B0C";
    private static final String humidityUnit = "%";
    private static final String pressureUnit = "mBar";
    private String city;
    private String temperature;
    private String humidity;
    private String pressure;

    public Weather(String city) {
        this.city = city;
    }

    public Weather(String city, String temperature, String humidity, String pressure) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature + temperatureUnit;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity + humidityUnit;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure + pressureUnit;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return city + " {" +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                '}';
    }
}
