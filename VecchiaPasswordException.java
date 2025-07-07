package com.example.the_knife.Exceptions;

/**
 * Eccezione runtime lanciata quando l'utente tenta di impostare
 * una nuova password uguale a quella attualmente in uso.
 * <p>
 * Utile per rafforzare le politiche di sicurezza, forzando l'utente
 * a scegliere una password diversa durante il cambio credenziali.
 * </p>
 *
 * Esempio d'uso:
 * <pre>
 * if (nuovaPassword.equals(vecchiaPassword)) {
 *     throw new VecchiaPasswordException("La nuova password non può essere uguale a quella precedente.");
 * }
 * </pre>
 */
public class VecchiaPasswordException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code VecchiaPasswordException}
     * con il messaggio di errore specificato.
     *
     * @param message messaggio descrittivo dell'errore
     */
    public VecchiaPasswordException(String message) {
        super(message);
    }
}
