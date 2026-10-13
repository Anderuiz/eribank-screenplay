package co.com.eribank.questions;

import co.com.eribank.exceptions.BalanceFormatException;
import co.com.eribank.utils.WaitTime;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static co.com.eribank.userinterfaces.HomeScreen.BALANCE;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/** Lee el saldo de la pantalla principal ("Your balance is: 100.00$"). */
@Subject("el saldo actual de la cuenta")
public class CurrentBalance implements Question<Double> {

    private static final Pattern AMOUNT = Pattern.compile("(\\d+(?:\\.\\d+)?)");

    public static CurrentBalance value() {
        return new CurrentBalance();
    }

    @Override
    public Double answeredBy(Actor actor) {
        actor.attemptsTo(WaitUntil.the(BALANCE, isVisible()).forNoMoreThan(WaitTime.MEDIUM).seconds());
        String rawText = readText(BALANCE.resolveFor(actor));
        return parse(rawText);
    }

    private String readText(WebElementFacade element) {
        String text = element.getAttribute("text");
        return (text == null || text.isBlank()) ? element.getAttribute("content-desc") : text;
    }

    private Double parse(String rawText) {
        Matcher matcher = AMOUNT.matcher(rawText == null ? "" : rawText.replace(",", ""));
        if (!matcher.find()) {
            throw new BalanceFormatException(rawText);
        }
        return Double.parseDouble(matcher.group(1));
    }
}
