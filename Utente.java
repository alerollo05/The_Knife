package com.example.the_knife.Utente;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Rappresenta un utente del sistema "The_Knife".
 * <p>
 * La classe modella le informazioni essenziali di un utente registrato, come nome,
 * cognome, email, indirizzo, credenziali, ruolo, data di nascita e preferenze.
 * <p>
 * È compatibile con la serializzazione/deserializzazione JSON tramite le annotazioni di Jackson.
 * </p>
 * <p>
 * Gli utenti possono essere di tipo <b>Cliente</b> o <b>Ristoratore</b>, e il loro comportamento
 * nel sistema è differenziato tramite il campo {@code ruolo}.
 * </p>
 *
 *
 */
public class Utente {
    //EVITO CHE RISCRIVA DUE VOLTE I DATI NEL FILE UNA VOLTA CHE LI INSERISCO O LI RICARICO
    /** Nome dell'utente. */
    @JsonProperty("Nome")
    private String nome;
    /** Cognome dell'utente. */
    @JsonProperty("Cognome")
    private String cognome;
    /** Indirizzo email dell'utente. */
    @JsonProperty("Email")
    private String email;
    /** Indirizzo fisico dell'utente. */
    @JsonProperty("Indirizzo")
    private String indirizzo;
    /** Username scelto dall'utente per accedere all'applicazione. */
    @JsonProperty("Username")
    private String username;
    /** Password dell'utente (hashata con BCrypt). */
    @JsonProperty("Password")
    private String password;
    /** Data di nascita dell'utente. */
    @JsonProperty("DataDiNascita")
    private LocalDate dataDiNascita;
    /** Numero di telefono dell'utente. */
    @JsonProperty("Telefono")
    private String telefono;
    /** Ruolo dell'utente nel sistema ("Cliente" o "Ristoratore"). */
    @JsonProperty("Ruolo")
    private String ruolo;
    /** Identificativo numerico univoco dell'utente. */
    @JsonProperty("Id")
    private int id;
    /** Lista degli ID dei ristoranti preferiti dall'utente (solo per utenti di tipo Cliente). */
    @JsonProperty("Preferiti")
    private List<Integer> preferiti = new ArrayList<>();
    /**
     * Costruttore di default richiesto per la deserializzazione JSON.
     */
    public Utente() {}
    /**
     * Costruttore completo per inizializzare un oggetto Utente con tutti i campi.
     *
     * @param nome          il nome dell'utente
     * @param cognome       il cognome dell'utente
     * @param indirizzo     l'indirizzo dell'utente
     * @param username      lo username per il login
     * @param email         l'email dell'utente
     * @param password      la password hashata dell'utente
     * @param dataDiNascita la data di nascita dell'utente
     * @param telefono      il numero di telefono dell'utente
     * @param ruolo         il ruolo (Cliente o Ristoratore)
     * @param id            identificativo numerico dell'utente
     * @param preferiti     lista degli ID dei ristoranti preferiti
     */
    public Utente(String nome, String cognome, String indirizzo, String username,
                  String email, String password, LocalDate dataDiNascita,
                  String telefono, String ruolo, int id, List<Integer> preferiti) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dataDiNascita = dataDiNascita;
        this.telefono = telefono;
        this.ruolo = ruolo;
        this.id = id;
        this.preferiti = preferiti;
    }
    /**
     * Restituisce il nome dell'utente.
     * @return il nome dell'utente.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Imposta il nome dell'utente.
     * @param nome il nome da assegnare all'utente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Restituisce il cognome dell'utente.
     * @return il cognome dell'utente.
     */
    public String getCognome() {
        return cognome;
    }
    /**
     * Imposta il cognome dell'utente.
     * @param cognome il cognome da assegnare all'utente.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    /**
     * Restituisce l'indirizzo email dell'utente.
     * @return l'indirizzo email dell'utente.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Imposta l'indirizzo email dell'utente.
     * @param email l'email da assegnare all'utente.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Restituisce l'indirizzo di residenza dell'utente.
     * @return l'indirizzo dell'utente.
     */
    public String getIndirizzo() {
        return indirizzo;
    }
    /**
     * Imposta l'indirizzo di residenza dell'utente.
     * @param indirizzo l'indirizzo da assegnare all'utente.
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    /**
     * Restituisce lo username dell'utente.
     * @return lo username dell'utente.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Imposta lo username dell'utente.
     * @param username lo username da assegnare all'utente.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Restituisce la password cifrata dell'utente.
     * @return la password dell'utente.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password cifrata dell'utente.
     * @param password la password da assegnare all'utente.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce la data di nascita dell'utente.
     * @return la data di nascita.
     */
    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    /**
     * Imposta la data di nascita dell'utente.
     * @param dataDiNascita la data da assegnare all'utente.
     */
    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    /**
     * Restituisce il numero di telefono dell'utente.
     * @return il numero di telefono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta il numero di telefono dell'utente.
     * @param telefono il numero di telefono da assegnare.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Restituisce il ruolo dell'utente (es. Cliente, Ristoratore).
     * @return il ruolo dell'utente.
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il ruolo dell'utente.
     * @param ruolo il ruolo da assegnare all'utente.
     */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    /**
     * Restituisce l'ID univoco dell'utente.
     * @return l'ID dell'utente.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'ID univoco dell'utente.
     * @param id l'ID da assegnare all'utente.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce la lista degli ID dei ristoranti preferiti dell'utente.
     * @return una lista di ID di ristoranti preferiti.
     */
    public List<Integer> getPreferiti() {
        return preferiti;
    }

    /**
     * Imposta la lista degli ID dei ristoranti preferiti dell'utente.
     * @param preferiti la lista di preferiti da assegnare.
     */
    public void setPreferiti(List<Integer> preferiti) {
        this.preferiti = preferiti;
    }
}