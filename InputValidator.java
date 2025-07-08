package com.example.the_knife;

import com.example.the_knife.Exceptions.*;
import com.example.the_knife.Utente.ListaUtenti;
import com.example.the_knife.Utente.SessionManager;
import com.example.the_knife.Utente.Utente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.scene.control.Alert;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.the_knife.Utente.SessionManager.idRecensione;
import static com.example.the_knife.Utente.SessionManager.idRist;
/**
 * Classe utility per la validazione degli input utente e dei dati dei ristoranti o recensioni.
 * <p>
 * Fornisce metodi statici per:
 * <ul>
 *     <li>Validare campi obbligatori (nome, email, indirizzo, ecc.)</li>
 *     <li>Verificare formati e lunghezze accettabili</li>
 *     <li>Gestire modifiche ai dati utente nel file JSON</li>
 *     <li>Supportare messaggi di errore interattivi tramite JavaFX {@link Alert}</li>
 * </ul>
 */
public class InputValidator {
    /**
     * Mostra un messaggio informativo di successo dopo una registrazione.
     */
    protected static void handleInput() {
        //if(controllo che tutti gli input siano andati bene allora mando questo messaggio)
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERRORE");
        alert.setHeaderText("Ti sei registrato correttamente");
        alert.setContentText("I tuoi dati sono stati salvati...");
        alert.showAndWait();
        //else mando un errore specifico su un tipo di input inserito dall'utente
    }
    /**
     * Mostra un messaggio di errore personalizzato in un {@link Alert}.
     *
     * @param message1 titolo dell'errore
     * @param message2 descrizione del problema
     */
    public static void handleInput(String message1, String message2) {
        //if(controllo che tutti gli input siano andati bene allora mando questo messaggio)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText(message1);
        alert.setContentText(message2);
        alert.showAndWait();
        //else mando un errore specifico su un tipo di input inserito dall'utente
    }
    /**
     * Valida il campo nome del ristorante.
     *
     * @param nome nome del ristorante
     * @throws InputMancanteException se il campo è vuoto
     * @throws NumMaxCaratteriException se il campo supera i 100 caratteri
     */
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
    /**
     * Valida l'indirizzo: deve contenere lettere e numeri, senza caratteri speciali non consentiti.
     *
     * @param indirizzo indirizzo del ristorante o utente
     * @throws InputMancanteException se l'input è nullo
     * @throws IllegalArgumentException se l'argomento non è valido
     * @throws NumMaxCaratteriException se l'input è troppo lungo
     */
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
    /**
     * Valida il formato del luogo nel formato "Città, Paese".
     *
     * @param luogo stringa contenente luogo/città
     * @throws InputMancanteException Il campo luogo è obbligatorio.
     * @throws PaeseNonValidoException Formato del paese inserito non valido o non inserito.
     * @throws  NumMaxCaratteriException Limite massimo di caratteri raggiunto, num max di caratteri per il luogo è 50.
     */
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
    /**
     * Valida il campo prezzo: deve contenere 1-4 simboli di valuta.
     *
     * @param prezzo stringa con simboli come €, $, £
     * @throws InputMancanteException Il campo prezzo è obbligatorio.
     * @throws  PrezzoNonValidoException Formato prezzo non valido. Formati ammessi: $/£/€.
     */
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
    /**
     *
     * @param cucina tipo di cucina (es. Italiana, Cinese)
     * @throws InputMancanteException Il campo cucina è obbligatorio.
     */
    public static void validaCucina(String cucina) {
        if (cucina == null || cucina.isEmpty()) {
            handleInput("Errore", "Il campo cucina è obbligatorio.");
            throw new InputMancanteException("Il campo tipo di cucina è obbligatorio.");
        }
    }
    /**
     * Valida il numero di telefono internazionale (prefisso + numero).
     *
     * @param tel numero di telefono
     * @throws InputMancanteException Il campo telefono è obbligatorio.
     * @throws  TelefonoNonValidoException Numero di telefono non valido. Deve iniziare con + seguito da prefisso e numero, e deve avere minimo 6 e massimo 12 cifre.
     */
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
    /**
     * Valida l'URL se necessario (attualmente solo presenza).
     *
     * @param url URL da controllare
     * @throws InputMancanteException
     */
    public static void validaUrl(String url) {
        if (url == null || url.isEmpty()) {
            handleInput("Errore", "Il campo URL è obbligatorio.");
            throw new InputMancanteException("Il campo URL è obbligatorio.");
        }
        // Aggiungi controllo URL valido se necessario
    }
    /**
     * Valida la descrizione dei servizi offerti da un ristorante.
     *
     * @param servizio stringa contenente i servizi
     * @throws InputMancanteException Il campo servizio è obbligatorio
     * @throws NumMaxCaratteriException I servizi possono contenere al massimo 400 caratteri
     */
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
    /**
     * Valida una descrizione generica o commento lungo.
     *
     * @param descri testo descrittivo
     * @throws InputMancanteException Il campo descrizione è obbligatorio.
     * @throws  NumMaxCaratteriException Il campo descrizione è obbligatorio.
     */
    public static void validaDescrizione(String descri) {
        if (descri == null || descri.isEmpty()) {
            handleInput("Errore", "Il campo descrizione è obbligatorio.");
            throw new InputMancanteException("Il campo descrizione è obbligatorio.");
        }
        if (descri.length() > 500) {
            throw new NumMaxCaratteriException("La descrizione può contenere al massimo 500 caratteri.");
        }
    }
    /**
     * Valida il numero di stelle (rating da 0 a 5).
     *
     * @param stelle valore da validare
     * @throws InputMancanteException Il campo stelle è obbligatorio
     * @throws StelleException Le stelle devono essere un numero da 0 a 5
     */
    public static void validaStelle(String stelle) {
        if (stelle == null || stelle.isEmpty()) {
            handleInput("Errore", "Il campo stelle è obbligatorio.");
            throw new InputMancanteException("Il campo stelle è obbligatorio.");
        }
        if (!stelle.matches("^[0-5]$")) {
            handleInput("Errore", "Accetta solo numeri da 0 a 5.");
            throw new StelleException("Le stelle devono essere un numero da 0 a 5.");
        }
    }
    /**
     * Valida il campo email.
     *
     * @param email indirizzo email
     * @throws InputMancanteException Il campo mail è obbligatorio.
     * @throws  MailNonValidaException La mail non è in un formato corretto.
     * @throws  NumMaxCaratteriException La mail può contenere al massimo 254 caratteri.
     */
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
    /**
     * Valida lo username (max 30 caratteri).
     *
     * @param username nome utente
     * @throws InputMancanteException Il campo username è obbligatorio.
     * @throws  NumMaxCaratteriException Limite massimo di caratteri raggiunto,
     *  num max di caratteri per l'username è 30.
     */
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
    /**
     * Valida la password secondo i criteri di sicurezza:
     * almeno 8 caratteri, una maiuscola, una minuscola, un numero e un carattere speciale.
     *
     * @param password password inserita
     * @throws InputMancanteException Il campo password è obbligatorio
     * @throws PasswordNonValidaException Password non valida deve avere almeno 8 caratteri, una lettera maiuscola una minuscola, un numero e un carattere speciale
     * @throws NumMaxCaratteriException Limite massimo di caratteri raggiunto, num max di caratteri per la password è 64
     */
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
    /**
     * Valida il nome dell'utente.
     *
     * @param nomeUte nome dell'utente
     * @throws InputMancanteException Il campo nome è obbligatorio
     * @throws NumMaxCaratteriException Limite massimo di caratteri raggiunto, num max di caratteri per il nome è 50.
     */
    public static void validaNomeUte(String nomeUte) {
        if (nomeUte == null || nomeUte.isEmpty()) {
            handleInput("Errore", "Il campo nome è obbligatorio.");
            throw new InputMancanteException("Il campo nome è obbligatorio.");
        }
        if (nomeUte.length() > 50) {
            handleInput("Errore", "Limite massimo di caratteri raggiunto,\nnum max di caratteri per il nome è 50.");
            throw new NumMaxCaratteriException("Limite massimo di caratteri raggiunto, num max di caratteri per il nome è 50.");
        }
        if (!nomeUte.matches("^[a-zA-ZÀ-ÿ'\s]+$")) {
            handleInput("Errore", "Il nome può contenere solo lettere, spazi o apostrofi.");
            throw new IllegalArgumentException("Il nome può contenere solo lettere, spazi o apostrofi.");
        }
    }
    /**
     * Valida il cognome dell'utente.
     *
     * @param cognome cognome dell'utente
     * @throws InputMancanteException Il campo cognome è obbligatorio
     * @throws NumMaxCaratteriException Limite massimo di caratteri raggiunto, num max di caratteri per il cognome è 50
     */
    public static void validaCogno(String cognome) {
        if (cognome == null || cognome.isEmpty()) {
            handleInput("Errore", "Il campo cognome è obbligatorio.");
            throw new InputMancanteException("Il campo cognome è obbligatorio.");
        }
        if (cognome.length() > 50) {
            handleInput("Errore", "Limite massimo di caratteri raggiunto,\nnum max di caratteri per il cognome è 50.");
            throw new NumMaxCaratteriException("Limite massimo di caratteri raggiunto, num max di caratteri per il cognome è 50.");
        }
        if (!cognome.matches("^[a-zA-ZÀ-ÿ'\s]+$")) {
            handleInput("Errore", "Il cognome può contenere solo lettere, spazi o apostrofi.");
            throw new IllegalArgumentException("Il cognome può contenere solo lettere, spazi o apostrofi.");
        }
    }
    /**
     * Modifica un campo generico di un utente (nome, email, username...).
     *
     * @param username nome utente identificativo
     * @param campo campo da modificare (nome, email, telefono, ecc.)
     * @param newCampo nuovo valore da assegnare
     * @param fileJson percorso del file JSON
     * @throws IOException in caso di errore di accesso al file
     */
    public static void modificaUte(String username, String campo, String newCampo, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File fileUtenti = new File(fileJson);
        JsonNode root = mapper.readTree(fileUtenti);
        JsonNode utentiNode = root.path("Utenti");

        if (!utentiNode.isArray()) {
            throw new IOException("Formato JSON non valido: nodo 'Utenti' mancante o non array.");
        }

        List<Utente> listaUtenti = new ArrayList<>(Arrays.asList(
                mapper.treeToValue(utentiNode, Utente[].class)
        ));

        boolean modificato = false;

        for (Utente u : listaUtenti) {
            if (u.getUsername().equals(username)) {
                switch (campo.toLowerCase()) {
                    case "nome":
                        u.setNome(newCampo);
                        break;
                    case "cognome":
                        u.setCognome(newCampo);
                        break;
                    case "telefono":
                        u.setTelefono(newCampo);
                        break;
                    case "indirizzo":
                        u.setIndirizzo(newCampo);
                        break;
                    case "email":
                        u.setEmail(newCampo);
                        break;
                    case "password":
                        u.setPassword(LoginController.generaHash(newCampo));
                        break;
                    case "username":
                        u.setUsername(newCampo);
                        break;
                    default:
                        System.out.println("Campo non riconosciuto: " + campo);
                        return;
                }
                modificato = true;
                break;
            }
        }

        if (modificato) {
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("Utenti", mapper.valueToTree(listaUtenti));
            mapper.writerWithDefaultPrettyPrinter().writeValue(fileUtenti, nuovoRoot);
            System.out.println("Utente modificato correttamente.");
        } else {
            System.out.println("Nessun utente trovato con username: " + username);
        }
    }

    /**
     * Modifica la data di nascita dell'utente.
     *
     * @param username nome utente
     * @param campo campo (deve essere "data")
     * @param newCampo nuova data di nascita
     * @param fileJson file JSON dove salvare
     * @throws IOException in caso di errore file
     */
    public static void modificaUteData(String username, String campo, LocalDate newCampo, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File fileUtenti = new File(fileJson);

        //  Lettura dal file esterno
        JsonNode root = mapper.readTree(fileUtenti);
        JsonNode utenteNode = root.get("Utenti");

        if (utenteNode == null || !utenteNode.isArray()) {
            System.err.println("Formato JSON non valido: nodo 'Utenti' mancante o non è un array.");
            return;
        }

        List<Utente> utenti = Arrays.asList(mapper.treeToValue(utenteNode, Utente[].class));
        List<Utente> listaModificabile = new ArrayList<>(utenti);

        boolean modificato = false;

        for (Utente u : listaModificabile) {
            if (u.getUsername().equals(username)) {
                if ("data".equalsIgnoreCase(campo)) {
                    u.setDataDiNascita(newCampo);
                    modificato = true;
                    System.out.println("Data di nascita aggiornata per " + username);
                }
                break;
            }
        }

        if (modificato) {
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("Utenti", mapper.valueToTree(listaModificabile));
            mapper.writerWithDefaultPrettyPrinter().writeValue(fileUtenti, nuovoRoot);
            System.out.println("File JSON aggiornato con successo.");
        } else {
            System.out.println("Nessuna modifica effettuata.");
        }
    }
    /**
     * Verifica se la password fornita corrisponde a quella attuale dell'utente.
     *
     * @param username nome utente
     * @param password password da verificare
     * @return true se la password corrisponde, false altrimenti
     * @throws IOException se si verifica un errore di I/O
     */
    public static boolean verificaPassword(String username, String password) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File fileUtenti = new File("fileUtenti.json");


        // Deserializza il file JSON in ListaUtenti
        ListaUtenti lista = mapper.readValue(fileUtenti, ListaUtenti.class);

        for (Utente u : lista.Utenti) {
            if (u.getUsername().equals(username)) {
                if (BCrypt.checkpw(password, u.getPassword())) {
                    System.out.println("Password vecchia corretta.");
                    return true;
                } else {
                    System.out.println("Password errata.");
                    return false;
                }
            }
        }

        System.out.println("Username non trovato.");
        return false;
    }
    /**
     * Valida la valutazione (rating) in una recensione.
     *
     * @param valutazione valore tra 0 e 5
     * @throws InputMancanteException Il rating stelle è obbligatorio
     * @throws StelleException Inserire valutazione da 0 a 5
     */
    public static void validaRating(String valutazione) {
        if (valutazione == null || valutazione.isEmpty()) {
            handleInput("Errore", "Il rating stelle è obbligatorio.");
            throw new InputMancanteException("Il rating stelle è obbligatorio.");
        }
        if (!valutazione.matches("^[0-5]$")) {
            handleInput("Errore", "Inserire valutazione da 0 a 5.");
            throw new StelleException("Inserire valutazione da 0 a 5.");
        }
    }
    /**
     * Valida il contenuto del commento nella recensione.
     *
     * @param commento testo del commento
     * @throws InputMancanteException Il campo commento è obbligatorio.
     * @throws  NumMaxCaratteriException La descrizione può contenere al massimo 500 caratteri.
     */
    public static void validaCommento(String commento) {
        if (commento == null || commento.isEmpty()) {
            handleInput("Errore", "Il campo commento è obbligatorio.");
            throw new InputMancanteException("Il campo commento è obbligatorio.");
        }
        if (commento.length() > 500) {
            throw new NumMaxCaratteriException("La descrizione può contenere al massimo 500 caratteri.");
        }
    }

}
