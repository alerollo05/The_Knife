package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata che segnala il superamento del numero massimo di caratteri consentiti
 * in un campo di input (come nome, descrizione, indirizzo, ecc.).
 *
 * <p>Questa eccezione viene sollevata quando l'utente inserisce un testo che supera
 * il limite prestabilito di caratteri in un determinato campo.</p>
 *
 * <p>Estende {@link RuntimeException}, pertanto è un'eccezione non verificata.</p>
 */
public class NumMaxCaratteriException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione {@code NumMaxCaratteriException} con il messaggio specificato.
     *
     * @param message messaggio che descrive l'errore di superamento del limite di caratteri.
     */
    public NumMaxCaratteriException(String message) {
        super(message);
    }
}
