package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata lanciata quando la password inserita
 * non rispetta i criteri di validazione definiti dall'applicazione.
 *
 * <p>Questa eccezione viene utilizzata per segnalare errori come:</p>
 * <ul>
 *   <li>Password troppo corta o troppo lunga</li>
 *   <li>Assenza di caratteri speciali, lettere maiuscole/minuscole o numeri</li>
 *   <li>Formato non conforme ai requisiti di sicurezza</li>
 * </ul>
 *
 * <p>Estende {@link RuntimeException}, quindi è un'eccezione non controllata
 * che può essere lanciata senza obbligo di dichiarazione nel metodo chiamante.</p>
 */
public class PasswordNonValidaException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione {@code PasswordNonValidaException} con un messaggio specifico.
     *
     * @param message Messaggio descrittivo dell'errore di validazione della password.
     */
    public PasswordNonValidaException(String message) {
        super(message);
    }
}
