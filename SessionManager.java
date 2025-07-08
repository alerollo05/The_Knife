package com.example.the_knife.Utente;
//GESTIONE DELLA SESSIONE DOPO IL LOGIN
/**
 * La classe {@code SessionManager} gestisce le informazioni di sessione
 * relative a un utente autenticato all'interno dell'applicazione.
 *
 * È implementata come Singleton per garantire una singola istanza attiva
 * durante l'intera esecuzione del programma.
 *
 * Gestisce inoltre variabili statiche utilizzate per la navigazione tra
 * schermate, filtri di ricerca, ID selezionati e preferenze.
 */
public class SessionManager {
    //CAMPI
    /** Istanza singleton della sessione. */
    private static SessionManager instance;

    /** Username dell'utente loggato. */
    private String username;

    /** ID univoco dell'utente loggato. */
    private int userId;

    /** Ruolo dell'utente loggato (es. Cliente, Ristoratore). */
    private String ruolo;

    /** ID del ristorante attualmente selezionato. */
    public static Integer idRist = null;

    /** ID del filtro o elemento selezionato, usato in diverse schermate. */
    public static int idScelta = 0;

    /** ID della recensione selezionata. */
    public static int idRecensione = 0;

    //CAMPI RISTORANTE BARRA RICERCA
    /** Contatore per il filtro posizione o nome nel modulo di ricerca. */
    public static int counter;

    /** Contatore per tipo di cucina nel modulo di ricerca. */
    public static int counter1;

    /** Contatore per prezzo nel modulo di ricerca. */
    public static int counter2;

    /** Pagina attiva nel flusso di navigazione (es. Cliente, Ristoratore). */
    public static int pagina;

    /** Nome del luogo o città selezionato staticamente nella ricerca. */
    public static String luogoNomeStatico = "";

    /** Tipo di cucina selezionato staticamente nella ricerca. */
    public static String tipoCucinaStatico = "";

    /** Fascia di prezzo selezionata staticamente nella ricerca. */
    public static String prezzoStatico = "";

    /** Flag per il filtro "delivery disponibile". */
    public static boolean deliveryStatico;

    /** Flag per il filtro "prenotazione online disponibile". */
    public static boolean bookingStatico;

    /** Codice che identifica da quale menu proviene l'utente. */
    public static int menu;

    //COSTRUTTORE
    /**
     * Costruttore privato per impedire l'istanziazione diretta.
     * Utilizzare {@link #getInstance()} per ottenere l'istanza.
     */
    private SessionManager() {

    }
    //METODI
    /**
     * Restituisce l'unica istanza di {@code SessionManager}.
     *
     * @return l'istanza singleton di {@code SessionManager}.
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    /**
     * Inizializza la sessione utente impostando username, ID e ruolo.
     *
     * @param username lo username dell'utente.
     * @param userId l'ID univoco dell'utente.
     * @param ruolo il ruolo dell'utente (Cliente o Ristoratore).
     */
    public void login(String username, int userId, String ruolo) {
        this.username = username;
        this.userId = userId;
        this.ruolo = ruolo;
    }
    /**
     * Esegue il logout dell'utente, azzerando i dati della sessione.
     */
    public void logout() { //elimino la sessione resettando i campi
        this.username = null;
        this.userId = 0;
        this.ruolo = null;
    }
    /**
     * Restituisce lo username dell'utente attualmente loggato.
     *
     * @return lo username dell'utente, o {@code null} se non loggato.
     */
    public String getUsername() {
        return username;
    }
    /**
     * Restituisce l'ID dell'utente loggato.
     *
     * @return l'ID utente.
     */
    public int getUserId() {
        return userId;
    }
    /**
     * Restituisce il ruolo dell'utente loggato.
     *
     * @return il ruolo dell'utente (es. Cliente o Ristoratore).
     */
    public String getRuolo() {
        return ruolo;
    }
    /**
     * Verifica se un utente è attualmente loggato nella sessione.
     *
     * @return {@code true} se l'utente è loggato, altrimenti {@code false}.
     */
    public boolean isLoggedIn() { //se dovessi controllare che l'utente sia loggato
        return username != null;
    }
}

