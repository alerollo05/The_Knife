package com.example.the_knife.Utente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * La classe {@code ListaUtenti} rappresenta una struttura di supporto per
 * la deserializzazione di una lista di oggetti {@link Utente} da un file JSON.
 *
 * Viene usata in combinazione con la libreria Jackson per leggere e scrivere
 * file JSON contenenti dati relativi agli utenti del sistema.
 *
 * Il campo {@code Utenti} rappresenta l'elenco degli utenti registrati.
 */
public class ListaUtenti {
        /**
         * Lista degli oggetti {@link Utente} caricati dal file JSON.
         *
         * L'annotazione {@code @JsonIgnoreProperties(ignoreUnknown = true)} garantisce
         * che eventuali proprietà non mappate nel JSON non causino errori durante la
         * deserializzazione.
         */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public List<Utente> Utenti;

}
