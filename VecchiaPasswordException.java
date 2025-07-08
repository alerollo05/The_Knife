package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata che viene lanciata quando la vecchia password fornita
 * da un utente non corrisponde a quella attualmente registrata nel sistema.
 *
 * <p>Questa eccezione viene solitamente utilizzata durante il processo di cambio
 * password, per evitare che l'utente imposti una nuova password senza aver
 * confermato correttamente quella attuale.</p>
 *
 * <p>Estende {@link RuntimeException} per permettere la propagazione non controllata
 * nei flussi in cui non si vuole forzare la gestione con blocchi try-catch.</p>
 */
public class VecchiaPasswordException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione con un messaggio personalizzato.
     *
     * @param message Il messaggio di errore che descrive la causa dell'eccezione.
     */
    public VecchiaPasswordException(String message) {
        super(message);
    }
}
