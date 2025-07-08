package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata che segnala un'email non valida inserita dall'utente.
 *
 * <p>Questa eccezione può essere lanciata durante la fase di registrazione o modifica
 * del profilo utente, quando il formato dell'indirizzo email fornito non rispetta
 * i criteri sintattici richiesti.</p>
 *
 * <p>Estende {@link RuntimeException}, quindi è un'eccezione non controllata.</p>
 */
public class MailNonValidaException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione {@code MailNonValidaException} con il messaggio specificato.
     *
     * @param message Messaggio descrittivo dell'errore.
     */
    public MailNonValidaException(String message) {
        super(message);
    }
}
