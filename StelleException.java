package com.example.the_knife.Exceptions;

/**
 * Eccezione runtime lanciata quando il numero di stelle fornito per una recensione
 * non rientra nell'intervallo accettato dal sistema.
 * <p>
 * Solitamente il numero di stelle deve essere compreso tra 1 e 5.
 * Questa eccezione consente di bloccare valori non validi in fase di input.
 * </p>
 *
 * Esempio d'uso:
 * <pre>
 *
 *  Lancia l'eccezione se le stelle non sono comprese tra 1 e 5.
 *  {@code if (stelle < 1 || stelle > 5)}
 *
 * </pre>
 */
public class StelleException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code StelleException}
     * con il messaggio di errore specificato.
     *
     * @param message descrizione dell'errore da mostrare
     */
    public StelleException(String message) {
        super(message);
    }
}
