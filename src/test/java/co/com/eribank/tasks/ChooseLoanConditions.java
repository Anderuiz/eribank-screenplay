package co.com.eribank.tasks;

import co.com.eribank.interactions.SelectOption;
import co.com.eribank.models.MortgageData;
import co.com.eribank.utils.WaitTime;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.eribank.userinterfaces.MortgageLoanTypeScreen.*;
import static net.serenitybdd.core.steps.Instrumented.instanceOf;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/** Paso 2 de Mortgage Request: elige las condiciones del préstamo y guarda. */
public class ChooseLoanConditions implements Task {

    private final MortgageData mortgage;

    public ChooseLoanConditions(MortgageData mortgage) {
        this.mortgage = mortgage;
    }

    public static ChooseLoanConditions of(MortgageData mortgage) {
        return instanceOf(ChooseLoanConditions.class).withProperties(mortgage);
    }

    @Override
    @Step("{0} elige las condiciones del préstamo")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(LOAN_TYPE_OPTIONS, isVisible()).forNoMoreThan(WaitTime.MEDIUM).seconds(),
                SelectOption.withTextInList(mortgage.loanType(), TYPE_OF_LOAN_LIST),
                SelectOption.withTextInList(mortgage.years(), NUMBER_OF_YEARS_LIST),
                SelectOption.withTextInList(mortgage.occupation(), TYPE_OF_OCCUPATION_LIST),
                SelectOption.withTextInList(mortgage.yearlyIncome(), YEARLY_INCOME_LIST),
                Click.on(SAVE_BUTTON)
        );
    }
}
