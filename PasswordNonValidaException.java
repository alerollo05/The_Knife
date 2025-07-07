package com.example.the_knife.Exceptions;

/**
 * Eccezione runtime lanciata quando la password fornita non rispetta
 * i criteri di validazione richiesti dal sistema.
 * <p>
 * I criteri possono includere lunghezza minima, presenza di numeri,
 * lettere maiuscole, caratteri speciali, ecc.
 * </p>
 *
 * Esempio d'uso:
 * <pre>
 * if (!password.matches(PASSWORD_REGEX)) {
 *     throw new PasswordNonValidaException("La password non è valida.");
 * }
 * </pre>
 */
public class PasswordNonValidaException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code PasswordNonValidaException}
     * con il messaggio specificato.
     *
     * @param message descrizione dettagliata dell'errore
     */
    public PasswordNonValidaException(String message) {
        super(message);
    }
}
