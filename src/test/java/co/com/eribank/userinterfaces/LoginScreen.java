package co.com.eribank.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

import static co.com.eribank.utils.AppLocators.resourceId;

public final class LoginScreen {

    public static final Target USERNAME = Target.the("campo usuario")
            .located(resourceId("usernameTextField"));
    public static final Target PASSWORD = Target.the("campo contraseña")
            .located(resourceId("passwordTextField"));
    public static final Target LOGIN_BUTTON = Target.the("botón Login")
            .located(resourceId("loginButton"));

    private LoginScreen() {
    }
}
