package co.com.eribank.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static co.com.eribank.utils.AppLocators.APP_PACKAGE;
import static io.appium.java_client.AppiumBy.androidUIAutomator;
import static net.serenitybdd.core.steps.Instrumented.instanceOf;

/**
 * EriBank es una app antigua (targetSdk 10). Al abrirla, Android muestra la revisión de
 * permisos ("Choose what to allow...") y el aviso "built for an older version of Android".
 * Al aceptar la revisión, Android regresa al escritorio en vez de abrir la app. Esta interacción acepta esas pantallas y vuelve a traer EriBank al frente.
 * Si no aparece ninguna pantalla del sistema, no hace nada.
 */
public class DismissSystemDialogs implements Interaction {

    private static final By SYSTEM_BUTTON = androidUIAutomator(
            "new UiSelector().packageNameMatches(\"^(android|com\\.(google\\.)?android\\..*)$\")"
                    + ".textMatches(\"(?i)^(continue|continuar|ok|aceptar)$\")");
    private static final String ACTIVATE_APP = "mobile: activateApp";
    private static final Duration QUICK_LOOKUP = Duration.ofSeconds(3);
    private static final Duration DEFAULT_LOOKUP = Duration.ofSeconds(5);
    private static final int MAX_DIALOGS = 3;

    public static DismissSystemDialogs ifPresent() {
        return instanceOf(DismissSystemDialogs.class).newInstance();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        driver.manage().timeouts().implicitlyWait(QUICK_LOOKUP);
        try {
            for (int attempt = 0; attempt < MAX_DIALOGS; attempt++) {
                List<WebElement> buttons = driver.findElements(SYSTEM_BUTTON);
                if (buttons.isEmpty()) {
                    return;
                }
                accept(buttons.get(0));
                bringAppToForeground(driver);
            }
        } finally {
            driver.manage().timeouts().implicitlyWait(DEFAULT_LOOKUP);
        }
    }

    private void accept(WebElement button) {
        try {
            button.click();
        } catch (StaleElementReferenceException alreadyClosed) {
            // La pantalla se cerró sola entre la búsqueda y el clic: no es un error.
        }
    }

    private void bringAppToForeground(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript(ACTIVATE_APP, Map.of("appId", APP_PACKAGE));
    }
}
