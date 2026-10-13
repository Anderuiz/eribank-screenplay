package co.com.eribank.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

import static co.com.eribank.utils.AppLocators.resourceId;

public final class HomeScreen {

    public static final Target BALANCE = Target.the("saldo actual")
            .located(By.xpath("//*[contains(@text,'Your balance is')"
                    + " or contains(@content-desc,'Your balance is')]"));
    public static final Target MAKE_PAYMENT_BUTTON = Target.the("botón Make Payment")
            .located(resourceId("makePaymentButton"));
    public static final Target MORTGAGE_REQUEST_BUTTON = Target.the("botón Mortgage Request")
            .located(resourceId("mortageRequestButton"));
    public static final Target LOGOUT_BUTTON = Target.the("botón Logout")
            .located(resourceId("logoutButton"));

    private HomeScreen() {
    }
}
