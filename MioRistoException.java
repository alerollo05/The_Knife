package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata che segnala un errore relativo alla gestione di un ristorante
 * associato all'utente (tipicamente un ristoratore).
 *
 * <p>Questa eccezione può essere utilizzata quando si tenta di eseguire un'operazione
 * non valida su un ristorante che appartiene o meno all'utente loggato, oppure quando
 * si rileva un'incongruenza nei dati relativi ai ristoranti gestiti.</p>
 *
 * <p>Estende {@link RuntimeException}, quindi è un'eccezione non controllata.</p>
 */
public class MioRistoException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione {@code MioRistoException} con il messaggio specificato.
     *
     * @param message messaggio che descrive il motivo dell'eccezione.
     */
    public MioRistoException(String message) {
        super(message);
    }
}
