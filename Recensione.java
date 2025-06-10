package com.example.the_knife.Ristoratore;

public class Recensione {

    public int idRec;
    public String author;
    public int rating;
    public String comment;
    public String date;
    public String risposta;


    public int getId() {
        return idRec;
    }

    public void setId(int id) {
        idRec = id;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }
}
