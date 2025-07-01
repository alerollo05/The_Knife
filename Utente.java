package com.example.the_knife.Utente;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;

public class Utente {
    //EVITO CHE RISCRIVA DUE VOLTE I DATI NEL FILE UNA VOLTA CHE LI INSERISCO O LI RICARICO
    @JsonProperty("Nome")
    private String nome;

    @JsonProperty("Cognome")
    private String cognome;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Indirizzo")
    private String indirizzo;

    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("DataDiNascita")
    private LocalDate dataDiNascita;

    @JsonProperty("Telefono")
    private String telefono;

    @JsonProperty("Ruolo")
    private String ruolo;

    @JsonProperty("Id")
    private int id;

    @JsonProperty("Preferiti")
    private List<Integer> preferiti;
    public Utente() {}

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getPreferiti() {return preferiti; }

    public void setPreferiti(List<Integer> preferiti) {this.preferiti = preferiti;}
}
