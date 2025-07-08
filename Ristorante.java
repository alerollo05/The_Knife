package com.example.the_knife.Ristoratore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
/**
 * La classe {@code Ristorante} rappresenta un'entità ristorante con tutte le sue proprietà
 * anagrafiche e funzionali, inclusi contatti, posizione, tipo di cucina, servizi disponibili,
 * recensioni e metadati statistici.
 *
 * È utilizzata per la gestione dei dati nei controller e viene serializzata/deserializzata
 * da/verso file JSON tramite Jackson.
 */
public class Ristorante {
    /** Identificativo univoco del ristorante. */
    @JsonProperty("Id")
    public int id;

    /** Identificativo del ristoratore proprietario. */
    @JsonProperty("IdRistoratore")
    public int idRistoratore;

    /** Nome del ristorante. */
    @JsonProperty("Name")
    public String name;

    /** Indirizzo completo del ristorante. */
    @JsonProperty("Address")
    public String address;

    /** Città o località del ristorante. */
    @JsonProperty("Location")
    public String location;

    /** Fascia di prezzo rappresentata da simboli (es. €, $$). */
    @JsonProperty("Price")
    public String price;

    /** Tipo di cucina offerta (es. Italiana, Giapponese). */
    @JsonProperty("Cuisine")
    public String cuisine;

    /** Coordinata longitudine per geolocalizzazione. */
    @JsonProperty("Longitude")
    public double longitude;

    /** Coordinata latitudine per geolocalizzazione. */
    @JsonProperty("Latitude")
    public double latitude;

    /** Numero di telefono del ristorante. */
    @JsonProperty("PhoneNumber")
    public String phoneNumber;

    /** URL del sito web del ristorante. */
    @JsonProperty("WebsiteUrl")
    public String websiteUrl;

    /** Numero di stelle verdi (qualità o sostenibilità). */
    @JsonProperty("GreenStar")
    public int greenStar;

    /** Elenco dei servizi e delle strutture disponibili. */
    @JsonProperty("FacilitiesAndServices")
    public String facilitiesAndServices;

    /** Descrizione testuale del ristorante. */
    @JsonProperty("Description")
    public String description;

    /** Indica se il ristorante offre consegna a domicilio. */
    @JsonProperty("Delivery")
    public boolean delivery;

    /** Indica se il ristorante permette prenotazioni online. */
    @JsonProperty("BookingOnline")
    public boolean bookingOnline;

    /** Lista delle recensioni associate al ristorante. */
    @JsonProperty("recensioni")
    public List<Recensione> recensioni;

    /** Numero totale di recensioni. */
    @JsonProperty("NumRec")
    public int numRec;

    /** Media delle valutazioni delle recensioni. */
    @JsonProperty("MediaRec")
    public double mediaRec;

    /** Email di contatto del ristorante. */
    @JsonProperty("Email")
    public String email;

    /**
     * Costruttore completo per inizializzare un oggetto Ristorante con tutti i campi principali.
     *
     * @param Id identificativo del ristorante
     * @param IdRistoratore identificativo del proprietario
     * @param Nome nome del ristorante
     * @param Address indirizzo fisico
     * @param Location località/città
     * @param Price fascia di prezzo
     * @param Cousine tipo di cucina
     * @param lati latitudine
     * @param longi longitudine
     * @param tel numero di telefono
     * @param url URL del sito
     * @param stars numero di stelle verdi
     * @param service servizi offerti
     * @param Description descrizione del ristorante
     * @param Delivery flag per consegna a domicilio
     * @param Booking flag per prenotazione online
     * @param Email email del ristorante
     */
    public Ristorante(int Id, int IdRistoratore, String Nome, String Address, String Location, String Price,
                      String Cousine, double lati, double longi, String tel, String url, int stars, String service,
                      String Description, boolean Delivery, boolean Booking, String Email ) {
        id = Id;
        idRistoratore = IdRistoratore;
        name = Nome ;
        address = Address;
        location = Location;
        price = Price;
        cuisine = Cousine;
        longitude = longi;
        latitude = lati;
        phoneNumber = tel;
        websiteUrl = url;
        greenStar = stars;
        facilitiesAndServices = service;
        description = Description;
        delivery = Delivery;
        bookingOnline = Booking;
        email = Email;
        recensioni = null;
        numRec = 0;
        mediaRec = 0;
    }
    /** Costruttore vuoto per la deserializzazione automatica. */
    public Ristorante(){}

    /** @return ID del ristorante */
    public int getId() { return id; }

    /** @param id nuovo ID del ristorante */
    public void setId(int id) { this.id = id; }

    /** @return ID del ristoratore proprietario */
    public int getIdRistoratore() { return idRistoratore; }

    /** @param idRistoratore nuovo ID del ristoratore */
    public void setIdRistoratore(int idRistoratore) { this.idRistoratore = idRistoratore; }

    /** @return nome del ristorante */
    public String getName() { return name; }

    /** @param name nuovo nome del ristorante */
    public void setName(String name) { this.name = name; }

    /** @return indirizzo fisico */
    public String getAddress() { return address; }

    /** @param address nuovo indirizzo fisico */
    public void setAddress(String address) { this.address = address; }

    /** @return città o località */
    public String getLocation() { return location; }

    /** @param location nuova città o località */
    public void setLocation(String location) { this.location = location; }

    /** @return fascia di prezzo */
    public String getPrice() { return price; }

    /** @param price nuova fascia di prezzo */
    public void setPrice(String price) { this.price = price; }

    /** @return tipo di cucina */
    public String getCuisine() { return cuisine; }

    /** @param cuisine nuovo tipo di cucina */
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }

    /** @return longitudine geografica */
    public double getLongitude() { return longitude; }

    /** @param longitude nuova longitudine */
    public void setLongitude(double longitude) { this.longitude = longitude; }

    /** @return latitudine geografica */
    public double getLatitude() { return latitude; }

    /** @param latitude nuova latitudine */
    public void setLatitude(double latitude) { this.latitude = latitude; }

    /** @return numero di telefono */
    public String getPhoneNumber() { return phoneNumber; }

    /** @param phoneNumber nuovo numero di telefono */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /** @return URL del sito web */
    public String getWebsiteUrl() { return websiteUrl; }

    /** @param websiteUrl nuovo URL del sito web */
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }

    /** @return numero di stelle verdi */
    public int getGreenStar() { return greenStar; }

    /** @param greenStar nuovo numero di stelle verdi */
    public void setGreenStar(int greenStar) { this.greenStar = greenStar; }

    /** @return servizi e strutture disponibili */
    public String getFacilitiesAndServices() { return facilitiesAndServices; }

    /** @param facilitiesAndServices nuova lista di servizi */
    public void setFacilitiesAndServices(String facilitiesAndServices) { this.facilitiesAndServices = facilitiesAndServices; }

    /** @return descrizione del ristorante */
    public String getDescription() { return description; }

    /** @param description nuova descrizione */
    public void setDescription(String description) { this.description = description; }

    /** @return true se è disponibile la consegna a domicilio */
    public boolean isDelivery() { return delivery; }

    /** @param delivery imposta la disponibilità della consegna */
    public void setDelivery(boolean delivery) { this.delivery = delivery; }

    /** @return true se è disponibile la prenotazione online */
    public boolean isBookingOnline() { return bookingOnline; }

    /** @param bookingOnline imposta la disponibilità della prenotazione online */
    public void setBookingOnline(boolean bookingOnline) { this.bookingOnline = bookingOnline; }

    /** @return lista di recensioni */
    public List<Recensione> getRecensioni() { return recensioni; }

    /** @param recensioni nuova lista di recensioni */
    public void setRecensioni(List<Recensione> recensioni) { this.recensioni = recensioni; }

    /** @return numero di recensioni */
    public int getNumRec() { return numRec; }

    /** @param numRec nuovo numero di recensioni */
    public void setNumRec(int numRec) { this.numRec = numRec; }

    /** @return media delle valutazioni */
    public double getMediaRec() { return mediaRec; }

    /** @param mediaRec nuova media delle valutazioni */
    public void setMediaRec(double mediaRec) { this.mediaRec = mediaRec; }

    /** @return email del ristorante */
    public String getEmail() { return email; }

    /** @param email nuova email del ristorante */
    public void setEmail(String email) { this.email = email; }

}
