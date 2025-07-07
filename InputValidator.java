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
 * Classe di utilità per la validazione degli input utente
 * e per la gestione delle modifiche ai dati utente nel file JSON.
 */
public class InputValidator {

    /**
     * Mostra un messaggio di successo per l'avvenuta registrazione.
     */
    protected static void handleInput() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrazione");
        alert.setHeaderText("Ti sei registrato correttamente");
        alert.setContentText("I tuoi dati sono stati salvati...");
        alert.showAndWait();
    }

    /**
     * Mostra un messaggio di errore personalizzato.
     *
     * @param message1 Titolo del messaggio.
     * @param message2 Contenuto del messaggio.
     */
    public static void handleInput(String message1, String message2) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registrazione");
        alert.setHeaderText(message1);
        alert.setContentText(message2);
        alert.showAndWait();
    }

    /**
     * Valida il nome del ristorante.
     *
     * @param nome Nome da validare.
     * @throws InputMancanteException se il nome è nullo o vuoto.
     * @throws NumMaxCaratteriException se supera i 100 caratteri.
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
     * Valida l'indirizzo.
     *
     * @param indirizzo Indirizzo da validare.
     * @throws InputMancanteException se nullo o vuoto.
     * @throws IllegalArgumentException se non contiene almeno un numero e una lettera.
     * @throws NumMaxCaratteriException se supera i 120 caratteri.
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
     * Valida il campo luogo nel formato "Città, Paese".
     *
     * @param luogo Luogo da validare.
     * @throws InputMancanteException se vuoto o nullo.
     * @throws PaeseNonValidoException se il formato è errato.
     * @throws NumMaxCaratteriException se supera 50 caratteri.
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
     * Valida il prezzo con simboli monetari.
     *
     * @param prezzo Prezzo in stringa.
     * @throws InputMancanteException se vuoto.
     * @throws PrezzoNonValidoException se il simbolo è errato.
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
     * Valida il tipo di cucina.
     *
     * @param cucina Descrizione del tipo di cucina.
     * @throws InputMancanteException se vuoto.
     * @throws PaeseNonValidoException se contiene numeri o simboli.
     */
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

    /**
     * Valida il numero di telefono (formato internazionale).
     *
     * @param tel Numero da validare.
     * @throws InputMancanteException se vuoto.
     * @throws TelefonoNonValidoException se non inizia con + o supera i limiti.
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
     * Valida un URL (solo presenza).
     *
     * @param url URL da validare.
     * @throws InputMancanteException se nullo o vuoto.
     */
    public static void validaUrl(String url) {
        if (url == null || url.isEmpty()) {
            handleInput("Errore", "Il campo URL è obbligatorio.");
            throw new InputMancanteException("Il campo URL è obbligatorio.");
        }
    }

    /**
     * Valida il campo descrizione dei servizi offerti.
     *
     * @param servizio Testo descrittivo.
     * @throws InputMancanteException se vuoto.
     * @throws NumMaxCaratteriException se supera 400 caratteri.
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
     * Valida la descrizione di un ristorante o recensione.
     *
     * @param descri Testo descrittivo.
     * @throws InputMancanteException se vuoto.
     * @throws NumMaxCaratteriException se oltre 500 caratteri.
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
     * Valida il numero di stelle (0–5).
     *
     * @param stelle Valore numerico in stringa.
     * @throws InputMancanteException se vuoto.
     * @throws StelleException se fuori dal range 0-5.
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
     * Valida l'indirizzo email.
     *
     * @param email Email da controllare.
     * @throws InputMancanteException se vuota.
     * @throws MailNonValidaException se il formato non è valido.
     * @throws NumMaxCaratteriException se oltre 254 caratteri.
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
     * Valida il campo username.
     *
     * @param username Username da validare.
     * @throws InputMancanteException se nullo o vuoto.
     * @throws NumMaxCaratteriException se supera i 30 caratteri.
     */
    public static void validaUsername(String username) {
        if (username == null || username.isEmpty()) {
            handleInput("Errore", "Il campo username è obbligatorio.");
            throw new InputMancanteException("Il campo username è obbligatorio.");
        }
        if (username.length() > 30) {
            handleInput("Errore", "Limite massimo di caratteri raggiunto,\n num max di caratteri per l'username è 30.");
            throw new NumMaxCaratteriException("Limite massimo di caratteri raggiunto, num max di caratteri per l'username è 30.");
        }
    }

    /**
     * Valida il campo password con criteri di sicurezza.
     *
     * @param password Password da validare.
     * @throws InputMancanteException se nullo o vuoto.
     * @throws PasswordNonValidaException se non rispetta i requisiti minimi.
     * @throws NumMaxCaratteriException se supera 64 caratteri.
     */
    public static void validaPassword(String password) {
        if (password == null || password.isEmpty()) {
            handleInput("Errore", "Il campo password è obbligatorio.");
            throw new InputMancanteException("Il campo password è obbligatorio.");
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            handleInput("Errore", "Password non valida deve avere almeno 8 caratteri, una lettera \nmaiuscola una minuscola, un numero e un carattere speciale ");
            throw new PasswordNonValidaException("Password non valida deve avere almeno 8 caratteri, una lettera maiuscola una minuscola, un numero e un carattere speciale");
        } else if (password.length() > 64) {
            handleInput("Errore", "Limite massimo di caratteri raggiunto,\n num max di caratteri per la password è 64.");
            throw new NumMaxCaratteriException("Limite massimo di caratteri raggiunto, num max di caratteri per la password è 64.");
        }
    }

    /**
     * Valida il nome dell’utente.
     *
     * @param nomeUte Nome da validare.
     * @throws InputMancanteException se nullo o vuoto.
     * @throws NumMaxCaratteriException se oltre 50 caratteri.
     */
    public static void validaNomeUte(String nomeUte) {
        if (nomeUte == null || nomeUte.isEmpty()) {
            handleInput("Errore", "Il campo nome è obbligatorio.");
            throw new InputMancanteException("Il campo nome è obbligatorio.");
        }
        if (nomeUte.length() > 50) {
            handleInput("Errore", "Limite massimo di caratteri raggiunto,\n num max di caratteri per il nome è 50.");
            throw new NumMaxCaratteriException("Limite massimo di caratteri raggiunto, num max di caratteri per il nome è 50.");
        }
    }

    /**
     * Valida il cognome dell’utente.
     *
     * @param cognome Cognome da validare.
     * @throws InputMancanteException se nullo o vuoto.
     * @throws NumMaxCaratteriException se oltre 50 caratteri.
     */
    public static void validaCogno(String cognome) {
        if (cognome == null || cognome.isEmpty()) {
            handleInput("Errore", "Il campo cognome è obbligatorio.");
            throw new InputMancanteException("Il campo cognome è obbligatorio.");
        }
        if (cognome.length() > 50) {
            handleInput("Errore", "Limite massimo di caratteri raggiunto,\n num max di caratteri per il cognome è 50.");
            throw new NumMaxCaratteriException("Limite massimo di caratteri raggiunto, num max di caratteri per il cognome è 50.");
        }
    }

    // --- MODIFICHE FILE JSON ---

    /**
     * Modifica un campo testuale dell'utente nel file JSON.
     *
     * @param username Username dell'utente da aggiornare.
     * @param campo Campo da modificare (es. "nome", "telefono", ecc.).
     * @param newCampo Nuovo valore da assegnare.
     * @param fileJson Percorso al file JSON.
     * @throws IOException se avviene un errore di I/O.
     */
    public static void modificaUte(String username, String campo, String newCampo, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File fileUtenti = new File(fileJson);

        if (!fileUtenti.exists()) {
            InputStream inputDefault = InputValidator.class.getResourceAsStream("/com/example/the_knife/data/fileUtenti.json");
            if (inputDefault == null) {
                System.err.println("File iniziale non trovato nel classpath.");
                return;
            }
            fileUtenti.getParentFile().mkdirs();
            Files.copy(inputDefault, fileUtenti.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        JsonNode root = mapper.readTree(fileUtenti);
        JsonNode utenteNode = root.get("Utenti");

        if (utenteNode == null || !utenteNode.isArray()) {
            System.err.println("Formato JSON non valido.");
            return;
        }

        List<Utente> utenti = Arrays.asList(mapper.treeToValue(utenteNode, Utente[].class));
        List<Utente> listaModificabile = new ArrayList<>(utenti);

        boolean modificato = false;

        for (Utente u : listaModificabile) {
            if (u.getUsername().equals(username)) {
                switch (campo.toLowerCase()) {
                    case "nome": u.setNome(newCampo); break;
                    case "cognome": u.setCognome(newCampo); break;
                    case "telefono": u.setTelefono(newCampo); break;
                    case "indirizzo": u.setIndirizzo(newCampo); break;
                    case "email": u.setEmail(newCampo); break;
                    case "password": u.setPassword(LoginController.generaHash(newCampo)); break;
                    case "username": u.setUsername(newCampo); break;
                    default: System.out.println("Campo non riconosciuto: " + campo); return;
                }
                modificato = true;
                break;
            }
        }

        if (modificato) {
            ObjectNode nuovoRoot = mapper.createObjectNode();
            nuovoRoot.set("Utenti", mapper.valueToTree(listaModificabile));
            mapper.writerWithDefaultPrettyPrinter().writeValue(fileUtenti, nuovoRoot);
            System.out.println("Utente modificato correttamente.");
        } else {
            System.out.println("Nessuna modifica effettuata.");
        }
    }

    /**
     * Modifica la data di nascita dell'utente.
     *
     * @param username Username dell'utente.
     * @param campo Campo da modificare ("data").
     * @param newCampo Nuova data.
     * @param fileJson Percorso del file JSON.
     * @throws IOException se avviene un errore di I/O.
     */
    public static void modificaUteData(String username, String campo, LocalDate newCampo, String fileJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File fileUtenti = new File(fileJson);

        if (!fileUtenti.exists()) {
            InputStream inputDefault = InputValidator.class.getResourceAsStream("/com/example/the_knife/data/fileUtenti.json");
            if (inputDefault == null) {
                System.err.println("File iniziale non trovato nel classpath.");
                return;
            }
            fileUtenti.getParentFile().mkdirs();
            Files.copy(inputDefault, fileUtenti.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        JsonNode root = mapper.readTree(fileUtenti);
        JsonNode utenteNode = root.get("Utenti");

        if (utenteNode == null || !utenteNode.isArray()) {
            System.err.println("Formato JSON non valido.");
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
     * Verifica se la password fornita corrisponde a quella salvata.
     *
     * @param username Username da controllare.
     * @param password Password in chiaro.
     * @return true se coincide, altrimenti false.
     * @throws IOException se avviene un errore di I/O.
     */
    public static boolean verificaPassword(String username, String password) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        File fileUtenti = new File("data/fileUtenti.json");

        if (!fileUtenti.exists()) {
            InputStream input = InputValidator.class.getResourceAsStream("/com/example/the_knife/data/fileUtenti.json");
            if (input == null) {
                System.err.println("File iniziale non trovato nel classpath.");
                return false;
            }
            fileUtenti.getParentFile().mkdirs();
            Files.copy(input, fileUtenti.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        ListaUtenti lista = mapper.readValue(fileUtenti, ListaUtenti.class);

        for (Utente u : lista.Utenti) {
            if (u.getUsername().equals(username)) {
                return BCrypt.checkpw(password, u.getPassword());
            }
        }

        System.out.println("Username non trovato.");
        return false;
    }

    /**
     * Impedisce all'utente di recensire il proprio ristorante.
     *
     * @param FileJson Percorso al file JSON (non utilizzato direttamente).
     * @throws MioRistoException se l'utente tenta di recensire il suo ristorante.
     */
    public static void verificaRecensione(String FileJson) {
        if (SessionManager.getInstance().getUserId() == idRecensione) {
            handleInput("Errore", "Non puoi recensire il tuo ristorante.");
            throw new MioRistoException("Non puoi recensire il tuo ristorante.");
        }
    }

    /**
     * Valida il rating da 0 a 5.
     *
     * @param valutazione Numero in stringa.
     * @throws InputMancanteException se vuoto.
     * @throws StelleException se fuori dal range 0-5.
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
     * Valida il commento della recensione.
     *
     * @param commento Testo commento.
     * @throws InputMancanteException se vuoto.
     * @throws NumMaxCaratteriException se supera 500 caratteri.
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
