package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata lanciata quando il prezzo inserito per un ristorante
 * non rispetta i criteri di validazione definiti dall'applicazione.
 *
 * <p>Ad esempio, può essere utilizzata nei seguenti casi:</p>
 * <ul>
 *   <li>Prezzo vuoto o nullo</li>
 *   <li>Prezzo con simboli non consentiti</li>
 *   <li>Prezzo diverso da uno tra: €, €€, €€€, €€€€</li>
 * </ul>
 *
 * <p>Questa eccezione estende {@link RuntimeException}, pertanto può essere lanciata
 * senza essere obbligatoriamente dichiarata o gestita tramite blocchi {@code try-catch}.</p>
 */
public class PrezzoNonValidoException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione {@code PrezzoNonValidoException} con un messaggio personalizzato.
     *
     * @param message Messaggio descrittivo dell'errore di validazione del prezzo.
     */
    public PrezzoNonValidoException(String message) {
        super(message);
    }
}
