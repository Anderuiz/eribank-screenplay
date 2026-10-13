package co.com.eribank.stepdefinitions;

import co.com.eribank.exceptions.PaymentNotCompletedException;
import co.com.eribank.models.PaymentData;
import co.com.eribank.questions.CurrentBalance;
import co.com.eribank.tasks.MakePayment;
import co.com.eribank.utils.MemoryKeys;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;

import static co.com.eribank.exceptions.PaymentNotCompletedException.BALANCE_NOT_UPDATED;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.closeTo;

public class MakePaymentStepDefinitions {

    private static final double TOLERANCE = 0.01;
    private PaymentData payment;

    @Cuando("realiza un pago de {double} a {string} con teléfono {string} hacia {string}")
    public void makesAPayment(double amount, String name, String phone, String country) {
        payment = PaymentData.builder()
                .amount(amount)
                .name(name)
                .phone(phone)
                .country(country)
                .build();
        theActorInTheSpotlight().attemptsTo(MakePayment.with(payment));
    }

    @Entonces("su saldo se descuenta con el valor del pago")
    public void balanceIsReduced() {
        Actor actor = theActorInTheSpotlight();
        double initialBalance = actor.recall(MemoryKeys.INITIAL_BALANCE.name());
        double expectedBalance = initialBalance - payment.amount();

        actor.should(
                seeThat(CurrentBalance.value(), closeTo(expectedBalance, TOLERANCE))
                        .orComplainWith(PaymentNotCompletedException.class, BALANCE_NOT_UPDATED)
        );
    }
}
