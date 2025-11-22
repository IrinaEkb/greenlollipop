package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvConfig {
    private static Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config.properties");
        }
    }
    // Load properties from config file into memory
// Key-value pairs (qa1=https://...) become accessible via EnvConfig.getUrl(env)

    /**
     * Get URL for given env.
     * Example: "qa1" -> Walmart
     */
    public static String getUrl(String env) {

        return props.getProperty(env.toLowerCase());
    }
}
/**
 * EnvConfig.getUrl(env)
 * Получает значение из config.properties по ключу env.
 * Например:
 * env = "qa1" → вернёт URL https://www.walmart.com
 * env = "qa2" → вернёт URL https://www.amazon.com
 */