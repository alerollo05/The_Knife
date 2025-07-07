package com.example.the_knife.Exceptions;

/**
 * Eccezione runtime lanciata quando il valore del prezzo inserito
 * non è valido secondo le regole definite dall'applicazione.
 * <p>
 * Può essere usata, ad esempio, per impedire l'inserimento di caratteri
 * non numerici, valori negativi o prezzi fuori da un range accettabile.
 * </p>
 *
 * Esempio d'uso:
 *
 *  Lancia un'eccezione se il prezzo supera i limiti:
 *  {@code if (prezzo < 0 || prezzo > PREZZO_MASSIMO) {
 *      throw new PrezzoNonValidoException();
 *  }}
 *
 */
public class PrezzoNonValidoException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code PrezzoNonValidoException}
     * con il messaggio di errore specificato.
     *
     * @param message messaggio descrittivo dell'errore
     */
    public PrezzoNonValidoException(String message) {
        super(message);
    }
}
