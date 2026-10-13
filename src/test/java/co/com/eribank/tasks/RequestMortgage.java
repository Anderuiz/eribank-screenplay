package co.com.eribank.tasks;

import co.com.eribank.models.MortgageData;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import static co.com.eribank.userinterfaces.HomeScreen.MORTGAGE_REQUEST_BUTTON;
import static net.serenitybdd.core.steps.Instrumented.instanceOf;

/** Flujo completo de solicitud de crédito (compone las dos pantallas del formulario). */
public class RequestMortgage implements Task {

    private final MortgageData mortgage;

    public RequestMortgage(MortgageData mortgage) {
        this.mortgage = mortgage;
    }

    public static RequestMortgage with(MortgageData mortgage) {
        return instanceOf(RequestMortgage.class).withProperties(mortgage);
    }

    @Override
    @Step("{0} solicita un crédito")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(MORTGAGE_REQUEST_BUTTON),
                FillPersonalInformation.of(mortgage),
                ChooseLoanConditions.of(mortgage)
        );
    }
}
