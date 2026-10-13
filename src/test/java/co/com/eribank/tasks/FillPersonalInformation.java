package co.com.eribank.tasks;

import co.com.eribank.interactions.HideKeyboard;
import co.com.eribank.models.MortgageData;
import co.com.eribank.utils.WaitTime;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.eribank.userinterfaces.MortgagePersonalInfoScreen.*;
import static net.serenitybdd.core.steps.Instrumented.instanceOf;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/** Paso 1 de Mortgage Request: diligencia los datos personales y avanza. */
public class FillPersonalInformation implements Task {

    private final MortgageData mortgage;

    public FillPersonalInformation(MortgageData mortgage) {
        this.mortgage = mortgage;
    }

    public static FillPersonalInformation of(MortgageData mortgage) {
        return instanceOf(FillPersonalInformation.class).withProperties(mortgage);
    }

    @Override
    @Step("{0} diligencia sus datos personales")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(FIRST_NAME, isVisible()).forNoMoreThan(WaitTime.MEDIUM).seconds(),
                Enter.theValue(mortgage.firstName()).into(FIRST_NAME),
                Enter.theValue(mortgage.lastName()).into(LAST_NAME),
                Enter.theValue(mortgage.age()).into(AGE),
                Enter.theValue(mortgage.addressOne()).into(ADDRESS_ONE),
                Enter.theValue(mortgage.addressTwo()).into(ADDRESS_TWO),
                Enter.theValue(mortgage.country()).into(COUNTRY),
                HideKeyboard.ifVisible(),
                Click.on(NEXT_BUTTON)
        );
    }
}
