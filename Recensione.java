package com.example.the_knife.Ristoratore;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
/**
 * La classe {@code Recensione} rappresenta una recensione associata a un ristorante.
 * <p>
 * Contiene informazioni sull'autore della recensione, il punteggio assegnato,
 * il commento, la data di pubblicazione e una eventuale risposta da parte del ristoratore.
 * La classe è serializzabile/deserializzabile in JSON grazie alle annotazioni {@code @JsonProperty}.
 * </p>
 *
 * <p>Utilizzata principalmente per la gestione delle recensioni nei ristoranti.</p>
 *
 * @author [Tuo Nome]
 */
public class Recensione {

    /** Identificatore univoco della recensione. */
    @JsonProperty("idRec")
    public int idRec;

    /** Nome utente dell'autore della recensione. */
    @JsonProperty("author")
    public String author;

    /** Valutazione numerica data al ristorante (es. da 1 a 5). */
    @JsonProperty("rating")
    public int rating;

    /** Testo del commento scritto dall'utente. */
    @JsonProperty("comment")
    public String comment;

    /** Data di pubblicazione della recensione. */
    @JsonProperty("date")
    public LocalDate date;

    /** Risposta del ristoratore alla recensione (opzionale). */
    @JsonProperty("risposta")
    public String risposta;

    /**
     * Costruttore vuoto richiesto per la deserializzazione JSON.
     */
    public Recensione() {}

    /**
     * Costruttore completo per creare una nuova recensione.
     *
     * @param idRec     ID univoco della recensione
     * @param author    Autore della recensione
     * @param rating    Valutazione assegnata
     * @param comment   Testo del commento
     * @param date      Data della recensione
     * @param risposta  Risposta del ristoratore (facoltativa)
     */
    public Recensione(int idRec, String author, int rating, String comment, LocalDate date, String risposta) {
        this.idRec = idRec;
        this.author = author;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.risposta = risposta;
    }

    /**
     * Restituisce l'ID della recensione.
     *
     * @return ID della recensione
     */
    public int getId() {
        return idRec;
    }

    /**
     * Imposta l'ID della recensione.
     *
     * @param id nuovo ID da assegnare
     */
    public void setId(int id) {
        this.idRec = id;
    }

    /**
     * Restituisce il commento dell'utente.
     *
     * @return commento
     */
    public String getComment() {
        return comment;
    }

    /**
     * Imposta un nuovo commento per la recensione.
     *
     * @param comment commento aggiornato
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Restituisce il nome utente dell'autore della recensione.
     *
     * @return nome dell'autore
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Imposta il nome utente dell'autore della recensione.
     *
     * @param author nome utente dell'autore
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Restituisce la valutazione assegnata dal recensore.
     *
     * @return punteggio (es. da 1 a 5)
     */
    public int getRating() {
        return rating;
    }

    /**
     * Imposta la valutazione per la recensione.
     *
     * @param rating punteggio numerico
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Restituisce la data di pubblicazione della recensione.
     *
     * @return data della recensione
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Imposta la data di pubblicazione della recensione.
     *
     * @param date nuova data
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Restituisce la risposta fornita dal ristoratore alla recensione.
     *
     * @return risposta del ristoratore
     */
    public String getRisposta() {
        return risposta;
    }

    /**
     * Imposta la risposta del ristoratore alla recensione.
     *
     * @param risposta testo della risposta
     */
    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }
}