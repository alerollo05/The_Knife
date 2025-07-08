package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata che segnala l'inserimento di un paese non valido
 * durante la registrazione o modifica del profilo utente o ristorante.
 *
 * <p>Questa eccezione viene sollevata quando il nome del paese non rispetta
 * i criteri di validazione, ad esempio:</p>
 * <ul>
 *   <li>Paese nullo o vuoto</li>
 *   <li>Contiene caratteri non ammessi</li>
 *   <li>Non è tra quelli supportati dall'applicazione</li>
 * </ul>
 *
 * <p>Estende {@link RuntimeException}, pertanto non è obbligatorio gestirla esplicitamente con `try-catch`.</p>
 */
public class PaeseNonValidoException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione {@code PaeseNonValidoException} con il messaggio specificato.
     *
     * @param message messaggio descrittivo dell'errore relativo al paese non valido.
     */
    public PaeseNonValidoException(String message) {
        super(message);
    }
}
