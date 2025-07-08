package com.example.the_knife.Exceptions;
/**
 * Eccezione personalizzata generica utilizzata per segnalare errori legati alle
 * operazioni sugli utenti nel sistema.
 *
 * <p>Questa eccezione può essere lanciata, ad esempio, quando si verifica un errore
 * durante la registrazione, il login, la modifica del profilo o altre operazioni
 * che coinvolgono un oggetto utente.</p>
 *
 * <p>Estende {@link RuntimeException} per consentire la propagazione automatica senza
 * obbligo di gestione con blocchi {@code try-catch}, utile nei contesti dove si preferisce
 * una gestione centralizzata degli errori.</p>
 */
public class UtenteException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione {@code UtenteException} con un messaggio personalizzato.
     *
     * @param message Il messaggio di errore che descrive la causa dell'eccezione.
     */
    public UtenteException(String message) {
        super(message);
    }
}
