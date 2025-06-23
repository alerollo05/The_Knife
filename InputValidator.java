package com.example.the_knife;

import com.example.the_knife.Exceptions.*;
import javafx.scene.control.Alert;

public class InputValidator {
    protected void handleInput() {
        //if(controllo che tutti gli input siano andati bene allora mando questo messaggio)
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrazione");
        alert.setHeaderText("Ti sei registrato correttamente");
        alert.setContentText("I tuoi dati sono stati salvati...");
        alert.showAndWait();
        //else mando un errore specifico su un tipo di input inserito dall'utente
    }
    public static void handleInput(String message1, String message2) {
        //if(controllo che tutti gli input siano andati bene allora mando questo messaggio)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registrazione");
        alert.setHeaderText(message1);
        alert.setContentText(message2);
        alert.showAndWait();
        //else mando un errore specifico su un tipo di input inserito dall'utente
    }

    public static void validaNomeRist(String nome) {
        if (nome == null || nome.isEmpty()) {
            handleInput("Errore", "Il campo nome è obbligatorio.");
            throw new InputMancanteException("Il campo nome è obbligatorio.");
        }
        if (nome.length() > 100) {
            handleInput("Errore", "Limite massimo di caratteri raggiunto, num max di caratteri per il nome è 100.");
            throw new NumMaxCaratteriException("Il nome può contenere al massimo 100 caratteri.");
        }
    }

    public static void validaIndirizzo(String indirizzo) {
        if (indirizzo == null || indirizzo.isEmpty()) {
            handleInput("Errore", "Il campo indirizzo è obbligatorio.");
            throw new InputMancanteException("Il campo indirizzo è obbligatorio.");
        }
        if (!indirizzo.matches(".*\\d.*") || !indirizzo.matches(".*[a-zA-Z].*")) {
            handleInput("Errore", "L'indirizzo deve contenere almeno una lettera e un numero.");
            throw new IllegalArgumentException("L'indirizzo deve contenere almeno una lettera e un numero.");
        }
        if (indirizzo.length() > 120) {
            handleInput("Errore", "L'indirizzo può contenere al massimo 120 caratteri..");
            throw new NumMaxCaratteriException("L'indirizzo può contenere al massimo 120 caratteri.");
        }
        if (!indirizzo.matches("^[\\p{L}0-9.,'\\-\\s]+$")) {
            handleInput("Errore", "L'indirizzo contiene caratteri non validi.");
            throw new IllegalArgumentException("L'indirizzo contiene caratteri non validi.");
        }
    }

    public static void validaLuogo(String luogo) {
        if (luogo == null || luogo.trim().isEmpty()) {
            handleInput("Errore", "Il campo luogo è obbligatorio.");
            throw new InputMancanteException("Il campo luogo è obbligatorio.");
        }
        if (!luogo.matches("\\s*[^,\\s].*?,\\s*[^,\\s].*")) {
            handleInput("Errore", "Formato del paese inserito non valido o non inserito.");
            throw new PaeseNonValidoException("Formato del paese inserito non valido.");
        }
        if (luogo.length() > 50) {
            handleInput("Errore", "Limite massimo di caratteri raggiunto, num max di caratteri per il luogo è 50.");
            throw new NumMaxCaratteriException("Il luogo può contenere al massimo 50 caratteri.");
        }
    }

    public static void validaPrezzo(String prezzo) {
        if (prezzo == null || prezzo.isEmpty()) {
            handleInput("Errore", "Il campo prezzo è obbligatorio.");
            throw new InputMancanteException("Il campo prezzo è obbligatorio.");
        }
        if (!prezzo.matches("([£]{1,4}|[$]{1,4}|[€]{1,4})")) {
            handleInput("Errore", "Formato prezzo non valido, formati ammessi : $/£/€.");
            throw new PrezzoNonValidoException("Formato prezzo non valido. Formati ammessi: $/£/€.");
        }
    }

    public static void validaCucina(String cucina) {
        if (cucina == null || cucina.isEmpty()) {
            handleInput("Errore", "Il campo cucina è obbligatorio.");
            throw new InputMancanteException("Il campo tipo di cucina è obbligatorio.");
        }
        if (!cucina.matches("[a-zA-Z\\s]+")) {
            handleInput("Errore", "Il campo tipo di cucina può contenere solo lettere.");
            throw new PaeseNonValidoException("Il campo tipo di cucina può contenere solo lettere.");
        }
    }

    public static void validaTelefono(String tel) {
        if (tel == null || tel.isEmpty()) {
            handleInput("Errore", "Il campo telefono è obbligatorio.");
            throw new InputMancanteException("Il campo telefono è obbligatorio.");
        }
        if (!tel.matches("^\\+\\d{1,4}(\\s?\\d){6,20}$")) {
            handleInput("Errore", "Numero di telefono non valido. Deve iniziare con + seguito da prefisso e numero, e deve avere minimo 6 e massimo 12 cifre.");
            throw new TelefonoNonValidoException("Numero di telefono non valido. Deve iniziare con + seguito da prefisso e numero.");
        }
    }

    public static void validaUrl(String url) {
        if (url == null || url.isEmpty()) {
            handleInput("Errore", "Il campo URL è obbligatorio.");
            throw new InputMancanteException("Il campo URL è obbligatorio.");
        }
        // Aggiungi controllo URL valido se necessario
    }

    public static void validaServizio(String servizio) {
        if (servizio == null || servizio.isEmpty()) {
            handleInput("Errore", "Il campo servizio è obbligatorio.");
            throw new InputMancanteException("Il campo servizio è obbligatorio.");
        }
        if (servizio.length() > 400) {
            handleInput("Errore", "I servizi possono contenere al massimo 400 caratteri.");
            throw new NumMaxCaratteriException("I servizi possono contenere al massimo 400 caratteri.");
        }
    }

    public static void validaDescrizione(String descri) {
        if (descri == null || descri.isEmpty()) {
            handleInput("Errore", "Il campo descrizione è obbligatorio.");
            throw new InputMancanteException("Il campo descrizione è obbligatorio.");
        }
        if (descri.length() > 500) {
            throw new NumMaxCaratteriException("La descrizione può contenere al massimo 500 caratteri.");
        }
    }

    public static void validaStelle(String stelle) {
        if (stelle == null || stelle.isEmpty()) {
            handleInput("Errore", "Il campo stelle è obbligatorio.");
            throw new InputMancanteException("Il campo stelle è obbligatorio.");
        }
        if (!stelle.matches("^[0-5]+$")) {
            handleInput("Errore", "Accetta solo numeri da 1 a 5.");
            throw new StelleException("Le stelle devono essere un numero da 0 a 5.");
        }
    }

    public static void validaEmail(String email) {
        if (email == null || email.isEmpty()) {
            handleInput("Errore", "Il campo mail è obbligatorio.");
            throw new InputMancanteException("Il campo email è obbligatorio.");
        }
        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            handleInput("Errore", "La mail non è in un formato corretto.");
            throw new MailNonValidaException("La mail non è in un formato corretto.");
        }
        if (email.length() > 254) {
            handleInput("Errore", "La mail può contenere al massimo 254 caratteri.");
            throw new NumMaxCaratteriException("La mail può contenere al massimo 254 caratteri.");
        }
    }
    public static void validaUsername(String username) {
        if (username == null || username.isEmpty()) {
            handleInput("Errore", "Il campo username è obbligatorio.");
            throw new InputMancanteException("Il campo username è obbligatorio.");
        }
        if(username.length()>30){
            handleInput("Errore", "Limite massimo di caratteri raggiunto,\n num max di caratteri per l'username è 30.");
            throw new NumMaxCaratteriException("Limite massimo di caratteri raggiunto, num max di caratteri per l'username è 30.");
        }
    }
    public static void validaPassword(String password) {
        if (password == null || password.isEmpty()) {
            handleInput("Errore", "Il campo password è obbligatorio.");
            throw new InputMancanteException("Il campo password è obbligatorio.");
        }
        if(!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")){
            handleInput("Errore", "Password non valida deve avere almeno 8 caratteri, una lettera \nmaiuscola una minuscola, un numero e un carattere speciale ");
            throw new PasswordNonValidaException("Password non valida deve avere almeno 8 caratteri, una lettera maiuscola una minuscola, un numero e un carattere speciale");
        }else if(password.length()>64){
            handleInput("Errore", "Limite massimo di caratteri raggiunto,\n num max di caratteri per la password è 64.");
            throw new NumMaxCaratteriException("Limite massimo di caratteri raggiunto, num max di caratteri per la password è 64.");
        }
    }
    public static void validaNomeUte(String nomeUte){
        if (nomeUte == null || nomeUte.isEmpty()) {
            handleInput("Errore", "Il campo nome è obbligatorio.");
            throw new InputMancanteException("Il campo nome è obbligatorio.");
        }
        if(nomeUte.length()>50){
            handleInput("Errore", "Limite massimo di caratteri raggiunto,\n num max di caratteri per il nome è 50.");
            throw new NumMaxCaratteriException("Limite massimo di caratteri raggiunto, num max di caratteri per il nome è 50.");
        }
    }
    public static void validaCogno(String cognome){
        if (cognome == null || cognome.isEmpty()) {
            handleInput("Errore", "Il campo cognome è obbligatorio.");
            throw new InputMancanteException("Il campo cognome è obbligatorio.");
        }
        if(cognome.length()>50){
            handleInput("Errore", "Limite massimo di caratteri raggiunto,\n num max di caratteri per il cognome è 50.");
            throw new NumMaxCaratteriException("Limite massimo di caratteri raggiunto, num max di caratteri per il cognome è 50.");
        }
    }
}
