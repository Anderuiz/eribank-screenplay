package co.com.eribank.utils;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;

/**
 * Lee la configuración de serenity.conf (credenciales, etc.)
 * para no dejar datos sensibles quemados en el código.
 */
public final class AppConfig {

    private AppConfig() {
    }

    public static String username() {
        return property("eribank.username");
    }

    public static String password() {
        return property("eribank.password");
    }

    private static String property(String key) {
        return EnvironmentSpecificConfiguration
                .from(SystemEnvironmentVariables.currentEnvironmentVariables())
                .getProperty(key);
    }
}
