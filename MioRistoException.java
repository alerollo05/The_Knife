package com.example.the_knife.Exceptions;

/**
 * Eccezione runtime lanciata quando un utente tenta di eseguire un'azione
 * non consentita sul proprio ristorante, ad esempio lasciare una recensione.
 * <p>
 * Può essere usata per evitare conflitti di interesse o comportamenti scorretti
 * da parte di gestori che cercano di interagire come clienti con il proprio locale.
 * </p>
 *
 * Esempio d'uso:
 * <pre>
 * if (utente.getId() == ristorante.getGestoreId()) {
 *     throw new MioRistoException("Non puoi recensire il tuo stesso ristorante.");
 * }
 * </pre>
 */
public class MioRistoException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code MioRistoException} con il messaggio specificato.
     *
     * @param message descrizione dell'errore
     */
    public MioRistoException(String message) {
        super(message);
    }
}
