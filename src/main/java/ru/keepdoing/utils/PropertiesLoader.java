package ru.keepdoing.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private static Properties property = new Properties();
    private static Logger LOGGER = LoggerFactory.getLogger("Properties Loader");

    //TODO перенести загрузку настроек в конфиг
    static {
        loadFile("providers.properties");
        loadFile("keys.properties");
    }

    public static String get(String propertyKey) {
        return property.getProperty(propertyKey);
    }

    public static boolean getBool(String propertyKey) {
        if ("true".equals(property.getProperty(propertyKey))) {
            return true;
        }
        return false;
    }

    private static void loadFile(String file) {
        try {
            FileInputStream stream = new FileInputStream("src/main/resources/" + file);
            property.load(stream);
        } catch (IOException e) {
            LOGGER.error("Error loading properties. {}", e.getMessage());
        }
    }

}
