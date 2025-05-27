package com.example.the_knife.Utente;

import java.time.LocalDate;

public class Utente {
    public String Nome;
    public String Cognome;
    public String Indirizzo;
    public String Username;
    public String Password;
    public LocalDate DataDiNascita;
    public String Telefono;
    public String Ruolo;
    public int Id;

    public Utente(String nome, String cognome, String indirizzo, String username, String password, LocalDate datanascita, String telefono, String ruolo,int id) {
        this.Nome = nome;
        this.Cognome = cognome;
        this.Indirizzo = indirizzo;
        this.Username = username;
        this.Password = password;
        this.DataDiNascita = datanascita;
        this.Telefono = telefono;
        this.Ruolo = ruolo;
        this.Id = id;
    }


    public Utente() {
    }
}
