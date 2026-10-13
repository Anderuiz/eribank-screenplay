package co.com.eribank.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;

import static net.serenitybdd.core.steps.Instrumented.instanceOf;

/** Oculta el teclado si está abierto; si no lo está, no hace nada. */
public class HideKeyboard implements Interaction {

    private static final String HIDE_KEYBOARD_COMMAND = "mobile: hideKeyboard";

    public static HideKeyboard ifVisible() {
        return instanceOf(HideKeyboard.class).newInstance();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        try {
            ((JavascriptExecutor) BrowseTheWeb.as(actor).getDriver()).executeScript(HIDE_KEYBOARD_COMMAND);
        } catch (WebDriverException keyboardAlreadyHidden) {
            // El teclado no estaba visible: no es un error para el flujo.
        }
    }
}
