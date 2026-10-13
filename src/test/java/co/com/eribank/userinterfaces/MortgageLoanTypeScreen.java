package co.com.eribank.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

import static co.com.eribank.utils.AppLocators.resourceId;
import static co.com.eribank.utils.AppLocators.scrollToResourceId;

/** Paso 2 de Mortgage Request: tipo de préstamo, años, ocupación e ingresos. */
public final class MortgageLoanTypeScreen {

    public static final String TYPE_OF_LOAN_LIST = "typeOfLoanListView";
    public static final String NUMBER_OF_YEARS_LIST = "numberOfYearsListView";
    public static final String TYPE_OF_OCCUPATION_LIST = "typeOfOccupationListView";
    public static final String YEARLY_INCOME_LIST = "yearlyIncomeListView";

    public static final Target LOAN_TYPE_OPTIONS = Target.the("lista tipo de préstamo")
            .located(resourceId(TYPE_OF_LOAN_LIST));

    /** El botón queda debajo de las listas: el localizador hace scroll hasta él. */
    public static final Target SAVE_BUTTON = Target.the("botón Save")
            .located(scrollToResourceId("saveButton"));

    private MortgageLoanTypeScreen() {
    }
}
