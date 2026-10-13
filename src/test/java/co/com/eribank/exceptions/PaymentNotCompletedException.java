package co.com.eribank.exceptions;

/** Se lanza cuando el saldo no refleja el pago realizado. */
public class PaymentNotCompletedException extends AssertionError {

    public static final String BALANCE_NOT_UPDATED =
            "El pago no se completó: el saldo no se descontó correctamente";

    public PaymentNotCompletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
