package co.com.eribank.exceptions;

/** Se lanza cuando el texto del saldo no tiene un formato numérico reconocible. */
public class BalanceFormatException extends RuntimeException {

    public BalanceFormatException(String rawText) {
        super("No fue posible leer el saldo a partir del texto: '" + rawText + "'");
    }
}
