package co.com.eribank.interactions;

import co.com.eribank.exceptions.OptionNotFoundException;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.util.List;

import static co.com.eribank.utils.AppLocators.exactText;
import static co.com.eribank.utils.AppLocators.exactTextInList;
import static co.com.eribank.utils.AppLocators.scrollToText;
import static co.com.eribank.utils.AppLocators.scrollToTextInList;
import static net.serenitybdd.core.steps.Instrumented.instanceOf;

/**
 * Selecciona una opción de una lista por su texto.
 * Prueba primero sin scroll y luego haciendo scroll, en ese orden.
 * Si se indica una lista (resource-id), busca solo dentro de ella.
 */
public class SelectOption implements Interaction {

    private static final String ANY_LIST = "";

    private final String option;
    private final String listId;

    public SelectOption(String option, String listId) {
        this.option = option;
        this.listId = listId;
    }

    public static SelectOption withText(String option) {
        return instanceOf(SelectOption.class).withProperties(option, ANY_LIST);
    }

    public static SelectOption withTextInList(String option, String listId) {
        return instanceOf(SelectOption.class).withProperties(option, listId);
    }

    @Override
    @Step("{0} selecciona la opción '#option'")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        WebDriverException lastError = null;
        for (By locator : candidateLocators()) {
            try {
                driver.findElement(locator).click();
                return;
            } catch (NoSuchElementException | InvalidSelectorException notFound) {
                lastError = notFound;
            }
        }
        throw new OptionNotFoundException(option, lastError);
    }

    private List<By> candidateLocators() {
        if (ANY_LIST.equals(listId)) {
            return List.of(exactText(option), scrollToText(option));
        }
        return List.of(
                exactTextInList(listId, option),
                scrollToTextInList(listId, option),
                scrollToText(option)
        );
    }
}
