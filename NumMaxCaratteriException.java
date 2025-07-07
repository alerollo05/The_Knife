package com.example.the_knife.Exceptions;

/**
 * Eccezione runtime lanciata quando l'input dell'utente supera
 * il numero massimo di caratteri consentito in un campo.
 * <p>
 * Utile per convalidare lunghezze di descrizioni, recensioni,
 * titoli o altri campi testuali prima del salvataggio.
 * </p>
 *
 * Esempio d'uso:
 * <pre>
 * if (commento.length() > MAX_CARATTERI) {
 *     throw new NumMaxCaratteriException("Il testo supera il limite massimo di caratteri.");
 * }
 * </pre>
 */
public class NumMaxCaratteriException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code NumMaxCaratteriException}
     * con il messaggio di errore specificato.
     *
     * @param message messaggio descrittivo dell'errore
     */
    public NumMaxCaratteriException(String message) {
        super(message);
    }
}
