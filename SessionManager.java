package com.example.the_knife.Utente;
//GESTIONE DELLA SESSIONE DOPO IL LOGIN
public class SessionManager {
    //CAMPI
    private static SessionManager instance;
    private String username;
    private int userId;
    private String ruolo;
    public static Integer idRist =null;
    public static int idScelta = 0;
    public static int idRecensione = 0;

    //CAMPI RISTORANTE BARRA RICERCA
    public static int counter;
    public static int counter1;
    public static int counter2;
    public static int pagina;
    public static String luogoNomeStatico = "";
    public static String tipoCucinaStatico = "";
    public static String prezzoStatico = "";
    public static boolean deliveryStatico;
    public static boolean bookingStatico;
    //COSTRUTTORE
    private SessionManager() {

    }
    //METODI
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(String username, int userId, String ruolo) {
        this.username = username;
        this.userId = userId;
        this.ruolo = ruolo;
    }

    public void logout() { //elimino la sessione resettando i campi
        this.username = null;
        this.userId = 0;
        this.ruolo = null;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public String getRuolo() {
        return ruolo;
    }

    public boolean isLoggedIn() { //se dovessi controllare che l'utente sia loggato
        return username != null;
    }
}

