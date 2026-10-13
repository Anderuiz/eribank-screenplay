package co.com.eribank.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

import static co.com.eribank.utils.AppLocators.androidId;
import static co.com.eribank.utils.AppLocators.resourceId;

public final class MakePaymentScreen {

    public static final Target PHONE = Target.the("campo teléfono")
            .located(resourceId("phoneTextField"));
    public static final Target NAME = Target.the("campo nombre")
            .located(resourceId("nameTextField"));
    public static final Target AMOUNT = Target.the("campo monto")
            .located(resourceId("amountTextField"));
    public static final Target COUNTRY = Target.the("campo país")
            .located(resourceId("countryTextField"));
    public static final Target SELECT_COUNTRY_BUTTON = Target.the("botón Select país")
            .located(resourceId("countryButton"));
    public static final Target SEND_PAYMENT_BUTTON = Target.the("botón Send Payment")
            .located(resourceId("sendPaymentButton"));
    public static final Target CONFIRM_YES_BUTTON = Target.the("botón Yes de confirmación")
            .located(androidId("button1"));

    private MakePaymentScreen() {
    }
}
