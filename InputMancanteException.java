package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata che segnala l'assenza di un input obbligatorio da parte dell'utente.
 *
 * <p>Questa eccezione può essere lanciata nei casi in cui un campo richiesto (come nome, email, ecc.)
 * venga lasciato vuoto o non venga compilato correttamente durante operazioni come registrazione,
 * modifica del profilo o inserimento di un ristorante.</p>
 *
 * <p>Estende {@link RuntimeException}, quindi è un'eccezione non controllata.</p>
 */
public class InputMancanteException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione {@code InputMancanteException} con il messaggio specificato.
     *
     * @param message Messaggio descrittivo dell'errore.
     */
    public InputMancanteException(String message) {
        super(message);
    }
}
