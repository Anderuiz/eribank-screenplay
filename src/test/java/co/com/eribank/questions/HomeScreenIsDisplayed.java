package co.com.eribank.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static co.com.eribank.userinterfaces.HomeScreen.MAKE_PAYMENT_BUTTON;
import static co.com.eribank.userinterfaces.HomeScreen.MORTGAGE_REQUEST_BUTTON;

/** Indica si la app está en la pantalla principal (después de enviar un formulario). */
@Subject("la pantalla principal está visible")
public class HomeScreenIsDisplayed implements Question<Boolean> {

    public static HomeScreenIsDisplayed now() {
        return new HomeScreenIsDisplayed();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return MAKE_PAYMENT_BUTTON.resolveFor(actor).isVisible()
                && MORTGAGE_REQUEST_BUTTON.resolveFor(actor).isVisible();
    }
}
