package com.example.the_knife.Exceptions;

/**
 * Eccezione generica lanciata per errori relativi agli utenti.
 * <p>
 * Può essere estesa o utilizzata come base per rappresentare problemi comuni
 * legati alla gestione degli utenti, come errori di autenticazione,
 * registrazione, profilo non trovato, dati incoerenti, ecc.
 * </p>
 *
 * Esempio d'uso:
 * <pre>
 * if (utente == null) {
 *     throw new UtenteException("Utente non trovato.");
 * }
 * </pre>
 */
public class UtenteException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code UtenteException}
     * con il messaggio di errore specificato.
     *
     * @param message messaggio descrittivo dell'errore
     */
    public UtenteException(String message) {
        super(message);
    }
}
