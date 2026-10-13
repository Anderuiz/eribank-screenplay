package co.com.eribank.utils;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * Construye localizadores de la aplicación EriBank.
 * Centraliza el paquete de la app para no repetirlo en cada pantalla (DRY).
 */
public final class AppLocators {

    public static final String APP_PACKAGE = "com.experitest.ExperiBank";

    private AppLocators() {
    }

    public static By resourceId(String id) {
        return AppiumBy.id(fullId(id));
    }

    public static By androidId(String id) {
        return AppiumBy.id("android:id/" + id);
    }

    public static String fullId(String id) {
        return APP_PACKAGE + ":id/" + id;
    }

    /** Texto exacto en cualquier parte de la pantalla. */
    public static By exactText(String text) {
        return uiAutomator(textSelector(text));
    }

    /** Texto exacto dentro de una lista concreta. */
    public static By exactTextInList(String listId, String text) {
        return uiAutomator(listSelector(listId) + ".childSelector(" + textSelector(text) + ")");
    }

    /** Hace scroll en la primera vista desplazable hasta encontrar el texto. */
    public static By scrollToText(String text) {
        return uiAutomator(scrollable("new UiSelector().scrollable(true)") + textSelector(text) + ")");
    }

    /** Hace scroll en la primera vista desplazable hasta encontrar el elemento con ese resource-id. */
    public static By scrollToResourceId(String id) {
        return uiAutomator(scrollable("new UiSelector().scrollable(true)")
                + "new UiSelector().resourceId(\"" + fullId(id) + "\"))");
    }

    /** Hace scroll dentro de una lista concreta hasta encontrar el texto. */
    public static By scrollToTextInList(String listId, String text) {
        return uiAutomator(scrollable(listSelector(listId)) + textSelector(text) + ")");
    }

    private static String scrollable(String container) {
        return "new UiScrollable(" + container + ").scrollIntoView(";
    }

    private static String listSelector(String listId) {
        return "new UiSelector().resourceId(\"" + fullId(listId) + "\")";
    }

    private static String textSelector(String text) {
        return "new UiSelector().text(\"" + text + "\")";
    }

    private static By uiAutomator(String expression) {
        return AppiumBy.androidUIAutomator(expression);
    }
}
