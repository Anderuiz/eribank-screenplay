package co.com.eribank.tasks;

import co.com.eribank.interactions.HideKeyboard;
import co.com.eribank.models.PaymentData;
import co.com.eribank.utils.WaitTime;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.eribank.userinterfaces.HomeScreen.MAKE_PAYMENT_BUTTON;
import static co.com.eribank.userinterfaces.MakePaymentScreen.AMOUNT;
import static co.com.eribank.userinterfaces.MakePaymentScreen.CONFIRM_YES_BUTTON;
import static co.com.eribank.userinterfaces.MakePaymentScreen.NAME;
import static co.com.eribank.userinterfaces.MakePaymentScreen.PHONE;
import static co.com.eribank.userinterfaces.MakePaymentScreen.SEND_PAYMENT_BUTTON;
import static net.serenitybdd.core.steps.Instrumented.instanceOf;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/** Realiza un pago completo: abre el formulario, lo diligencia, envía y confirma. */
public class MakePayment implements Task {

    private final PaymentData payment;

    public MakePayment(PaymentData payment) {
        this.payment = payment;
    }

    public static MakePayment with(PaymentData payment) {
        return instanceOf(MakePayment.class).withProperties(payment);
    }

    @Override
    @Step("{0} realiza un pago")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(MAKE_PAYMENT_BUTTON),
                WaitUntil.the(PHONE, isVisible()).forNoMoreThan(WaitTime.MEDIUM).seconds(),
                Enter.theValue(payment.phone()).into(PHONE),
                Enter.theValue(payment.name()).into(NAME),
                Enter.theValue(payment.amountAsText()).into(AMOUNT),
                HideKeyboard.ifVisible(),
                SelectCountry.called(payment.country()),
                WaitUntil.the(SEND_PAYMENT_BUTTON, isEnabled()).forNoMoreThan(WaitTime.SHORT).seconds(),
                Click.on(SEND_PAYMENT_BUTTON),
                WaitUntil.the(CONFIRM_YES_BUTTON, isVisible()).forNoMoreThan(WaitTime.SHORT).seconds(),
                Click.on(CONFIRM_YES_BUTTON),
                WaitUntil.the(MAKE_PAYMENT_BUTTON, isVisible()).forNoMoreThan(WaitTime.MEDIUM).seconds()
        );
    }
}
