package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata lanciata quando viene fornito un numero di telefono non valido
 * durante operazioni come registrazione, modifica del profilo o inserimento dati.
 *
 * <p>Questa eccezione estende {@link RuntimeException}, quindi non è obbligatorio
 * gestirla esplicitamente con un blocco {@code try-catch}, rendendola adatta
 * per applicazioni con gestione centralizzata degli errori.</p>
 *
 * <p>È utile quando si vuole fornire un feedback specifico all'utente
 * riguardo l'inserimento di un numero di telefono malformato o non conforme
 * a un formato atteso.</p>
 */
public class TelefonoNonValidoException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione {@code TelefonoNonValidoException}
     * con il messaggio di errore specificato.
     *
     * @param message Messaggio che descrive il motivo dell'errore.
     */
    public TelefonoNonValidoException(String message) {
        super(message);
    }
}
