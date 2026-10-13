package co.com.eribank.tasks;

import co.com.eribank.interactions.DismissSystemDialogs;
import co.com.eribank.utils.AppConfig;
import co.com.eribank.utils.WaitTime;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.eribank.userinterfaces.HomeScreen.MAKE_PAYMENT_BUTTON;
import static co.com.eribank.userinterfaces.LoginScreen.LOGIN_BUTTON;
import static co.com.eribank.userinterfaces.LoginScreen.PASSWORD;
import static co.com.eribank.userinterfaces.LoginScreen.USERNAME;
import static net.serenitybdd.core.steps.Instrumented.instanceOf;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Login implements Task {

    private final String username;
    private final String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Login withValidCredentials() {
        return with(AppConfig.username(), AppConfig.password());
    }

    public static Login with(String username, String password) {
        return instanceOf(Login.class).withProperties(username, password);
    }

    @Override
    @Step("{0} inicia sesión en EriBank con el usuario #username")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                DismissSystemDialogs.ifPresent(),
                WaitUntil.the(USERNAME, isVisible()).forNoMoreThan(WaitTime.MEDIUM).seconds(),
                Enter.theValue(username).into(USERNAME),
                Enter.theValue(password).into(PASSWORD),
                Click.on(LOGIN_BUTTON),
                WaitUntil.the(MAKE_PAYMENT_BUTTON, isVisible()).forNoMoreThan(WaitTime.MEDIUM).seconds()
        );
    }
}
