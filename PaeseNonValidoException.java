package com.example.the_knife.Exceptions;

/**
 * Eccezione runtime lanciata quando il nome di un paese (città, località o regione)
 * inserito dall'utente non è valido o non è accettato dal sistema.
 * <p>
 * Questa eccezione è utile durante la validazione di input geografici,
 * ad esempio nella ricerca di ristoranti o nella registrazione di un locale.
 * </p>
 *
 * Esempio d'uso:
 * <pre>
 * if (!listaPaesiValidi.contains(inputPaese)) {
 *     throw new PaeseNonValidoException("Il paese inserito non è valido.");
 * }
 * </pre>
 */
public class PaeseNonValidoException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code PaeseNonValidoException}
     * con il messaggio di errore specificato.
     *
     * @param message descrizione dell'errore
     */
    public PaeseNonValidoException(String message) {
        super(message);
    }
}
