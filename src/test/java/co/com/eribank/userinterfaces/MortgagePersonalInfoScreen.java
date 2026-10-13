package co.com.eribank.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

import static co.com.eribank.utils.AppLocators.resourceId;

/** Paso 1 de Mortgage Request: datos personales. */
public final class MortgagePersonalInfoScreen {

    public static final Target FIRST_NAME = Target.the("campo nombre")
            .located(resourceId("nameTextField"));
    public static final Target LAST_NAME = Target.the("campo apellido")
            .located(resourceId("lastNameTextField"));
    public static final Target AGE = Target.the("campo edad")
            .located(resourceId("ageTextField"));
    public static final Target ADDRESS_ONE = Target.the("campo dirección 1")
            .located(resourceId("addressOneTextField"));
    public static final Target ADDRESS_TWO = Target.the("campo dirección 2")
            .located(resourceId("addressTwoTextField"));
    public static final Target COUNTRY = Target.the("campo país")
            .located(resourceId("countryTextField"));
    public static final Target NEXT_BUTTON = Target.the("botón Next")
            .located(resourceId("nextButton"));

    private MortgagePersonalInfoScreen() {
    }
}
