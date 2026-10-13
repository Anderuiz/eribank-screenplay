package co.com.eribank.exceptions;

/** Se lanza cuando una opción de una lista no existe, ni siquiera haciendo scroll. */
public class OptionNotFoundException extends RuntimeException {

    public OptionNotFoundException(String option, Throwable cause) {
        super("No se encontró la opción '" + option + "' en la lista", cause);
    }
}
