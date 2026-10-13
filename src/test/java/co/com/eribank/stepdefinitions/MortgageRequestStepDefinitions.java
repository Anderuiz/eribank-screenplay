package co.com.eribank.stepdefinitions;

import co.com.eribank.exceptions.MortgageRequestNotSentException;
import co.com.eribank.models.MortgageData;
import co.com.eribank.questions.HomeScreenIsDisplayed;
import co.com.eribank.tasks.RequestMortgage;
import io.cucumber.java.DataTableType;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

import java.util.List;
import java.util.Map;

import static co.com.eribank.exceptions.MortgageRequestNotSentException.NOT_BACK_TO_HOME;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.is;

public class MortgageRequestStepDefinitions {

    @DataTableType
    public MortgageData mortgageEntry(Map<String, String> row) {
        return MortgageData.builder()
                .firstName(row.get("nombre"))
                .lastName(row.get("apellido"))
                .age(row.get("edad"))
                .addressOne(row.get("direccion1"))
                .addressTwo(row.get("direccion2"))
                .country(row.get("pais"))
                .loanType(row.get("tipoPrestamo"))
                .years(row.get("anios"))
                .occupation(row.get("ocupacion"))
                .yearlyIncome(row.get("ingresoAnual"))
                .build();
    }

    @Cuando("solicita un crédito con los siguientes datos")
    public void requestsAMortgage(List<MortgageData> mortgages) {
        theActorInTheSpotlight().attemptsTo(RequestMortgage.with(mortgages.get(0)));
    }

    @Entonces("la solicitud se envía y regresa a la pantalla principal")
    public void requestIsSent() {
        theActorInTheSpotlight().should(
                seeThat(HomeScreenIsDisplayed.now(), is(true))
                        .orComplainWith(MortgageRequestNotSentException.class, NOT_BACK_TO_HOME)
        );
    }
}
