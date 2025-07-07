package com.example.the_knife.Exceptions;

/**
 * Eccezione runtime lanciata quando un utente supera il numero massimo
 * consentito di recensioni per un determinato ristorante.
 * <p>
 * Può essere utilizzata per prevenire spam, abusi o comportamenti ripetitivi
 * non ammessi nel sistema di recensioni.
 * </p>
 *
 * Esempio d'uso:
 * <pre>
 * if (utente.haGiaRecensito(ristorante)) {
 *     throw new MaxNumRecensioniException("Hai già raggiunto il numero massimo di recensioni per questo ristorante.");
 * }
 * </pre>
 */
public class MaxNumRecensioniException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code MaxNumRecensioniException} con un messaggio descrittivo.
     *
     * @param message messaggio che descrive il motivo dell'eccezione
     */
    public MaxNumRecensioniException(String message) {
        super(message);
    }
}
