package co.com.eribank.tasks;

import co.com.eribank.interactions.SelectOption;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import static co.com.eribank.userinterfaces.MakePaymentScreen.SELECT_COUNTRY_BUTTON;
import static net.serenitybdd.core.steps.Instrumented.instanceOf;

/** Abre la lista de países del formulario de pago y elige uno. */
public class SelectCountry implements Task {

    private final String country;

    public SelectCountry(String country) {
        this.country = country;
    }

    public static SelectCountry called(String country) {
        return instanceOf(SelectCountry.class).withProperties(country);
    }

    @Override
    @Step("{0} selecciona el país #country")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(SELECT_COUNTRY_BUTTON),
                SelectOption.withText(country)
        );
    }
}
