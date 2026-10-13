package co.com.eribank.exceptions;

/** Se lanza cuando la solicitud de crédito no termina en la pantalla principal. */
public class MortgageRequestNotSentException extends AssertionError {

    public static final String NOT_BACK_TO_HOME =
            "La solicitud de crédito no se envió: la app no regresó a la pantalla principal";

    public MortgageRequestNotSentException(String message, Throwable cause) {
        super(message, cause);
    }
}
