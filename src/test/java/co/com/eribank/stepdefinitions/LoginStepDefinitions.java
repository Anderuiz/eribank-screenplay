package co.com.eribank.stepdefinitions;

import co.com.eribank.questions.CurrentBalance;
import co.com.eribank.tasks.Login;
import co.com.eribank.utils.MemoryKeys;
import io.cucumber.java.es.Dado;
import net.serenitybdd.screenplay.Actor;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

public class LoginStepDefinitions {

    @Dado("que {word} inició sesión en EriBank")
    public void hasLoggedIn(String actorName) {
        Actor actor = theActorCalled(actorName);
        actor.wasAbleTo(Login.withValidCredentials());
        actor.remember(MemoryKeys.INITIAL_BALANCE.name(), actor.asksFor(CurrentBalance.value()));
    }
}
