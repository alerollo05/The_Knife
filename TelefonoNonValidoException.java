package com.example.the_knife.Exceptions;

/**
 * Eccezione runtime lanciata quando il numero di telefono fornito
 * non rispetta il formato richiesto o contiene caratteri non validi.
 * <p>
 * Può essere utilizzata per validare input nei moduli di registrazione,
 * modifica profilo o inserimento di contatti.
 * </p>
 *
 * Esempio d'uso:
 * <pre>
 * if (!telefono.matches("\\d{10}")) {
 *     throw new TelefonoNonValidoException("Numero di telefono non valido.");
 * }
 * </pre>
 */
public class TelefonoNonValidoException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code TelefonoNonValidoException}
     * con il messaggio di errore specificato.
     *
     * @param message descrizione dell'errore
     */
    public TelefonoNonValidoException(String message) {
        super(message);
    }
}
