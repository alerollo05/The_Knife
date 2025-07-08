package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata lanciata quando il numero di stelle di un ristorante
 * inserito o modificato non è valido.
 *
 * <p>Questa eccezione può essere utilizzata per segnalare condizioni in cui il valore
 * delle stelle supera i limiti consentiti (es. da 0 a 3), è negativo, non numerico
 * o non coerente con la logica dell'applicazione.</p>
 *
 * <p>Estende {@link RuntimeException}, pertanto può essere lanciata senza
 * necessità di dichiarazione o gestione obbligatoria con blocchi {@code try-catch}.</p>
 */
public class StelleException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione {@code StelleException} con il messaggio specificato.
     *
     * @param message Messaggio descrittivo dell'errore relativo al numero di stelle.
     */
    public StelleException(String message) {
        super(message);
    }
}
