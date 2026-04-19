package utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {

    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();

    // Static block loads the properties into memory once when the class is loaded
    static {
        logger.info("Initializing ConfigManager and loading config.properties...");
        try (InputStream stream = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (stream == null) {
                logger.error("Configuration file 'config.properties' not found in the classpath (src/main/resources).");
                throw new RuntimeException("config.properties not found");
            }
            properties.load(stream);
            logger.info("Configuration properties loaded successfully.");
        } catch (Exception e) {
            logger.error("Failed to load configuration properties: " + e.getMessage());
            throw new RuntimeException("Configuration loading failed", e);
        }
    }

    // Private constructor prevents object instantiation
    private ConfigManager() {
    }

    /**
     * Retrieves a property value by its key.
     * @param key The key of the property to retrieve.
     * @return The value of the property, or null if not found.
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property key '" + key + "' was requested but not found in config.properties.");
        }
        return value;
    }

    /**
     * Retrieves a property value by its key, returning a default value if the key does not exist.
     * @param key The key of the property to retrieve.
     * @param defaultValue The fallback value if the key is missing.
     * @return The value of the property, or the defaultValue.
     */
    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.debug("Property key '" + key + "' not found. Using default value: " + defaultValue);
            return defaultValue;
        }
        return value;
    }
}