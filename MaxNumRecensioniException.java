package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata che segnala il superamento del numero massimo consentito di recensioni
 * da parte di un utente per un determinato ristorante.
 *
 * <p>Questa eccezione può essere lanciata nel caso in cui si voglia impedire che un utente
 * possa scrivere più recensioni di quante siano permesse per uno stesso ristorante,
 * violando una regola di business dell'applicazione.</p>
 *
 * <p>Estende {@link RuntimeException}, quindi è un'eccezione non controllata.</p>
 */
public class MaxNumRecensioniException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione {@code MaxNumRecensioniException} con il messaggio specificato.
     *
     * @param message Messaggio descrittivo dell'errore.
     */
    public MaxNumRecensioniException(String message) {
        super(message);
    }
}
