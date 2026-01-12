package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvConfig {

    public static String getBaseUrl() {
        String env = System.getProperty("env", "qa");

        return switch (env) {
            case "stage" -> "https://practice.expandtesting.com"; // Stage environment
            case "qa" -> "https://practice.expandtesting.com"; // QA env
            default -> throw new RuntimeException("Unknown env: " + env);
        };
    }
}