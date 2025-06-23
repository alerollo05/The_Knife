package com.example.the_knife.Ristoratore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Ristorante {
    @JsonProperty("Id")
    public int id;

    @JsonProperty("IdRistoratore")
    public int idRistoratore;

    @JsonProperty("Name")
    public String name;

    @JsonProperty("Address")
    public String address;

    @JsonProperty("Location")
    public String location;

    @JsonProperty("Price")
    public String price;

    @JsonProperty("Cuisine")
    public String cuisine;

    @JsonProperty("Longitude")
    public double longitude;

    @JsonProperty("Latitude")
    public double latitude;

    @JsonProperty("PhoneNumber")
    public String phoneNumber;

    @JsonProperty("WebsiteUrl")
    public String websiteUrl;

    @JsonProperty("GreenStar")
    public int greenStar;

    @JsonProperty("FacilitiesAndServices")
    public String facilitiesAndServices;

    @JsonProperty("Description")
    public String description;

    @JsonProperty("Delivery")
    public boolean delivery;

    @JsonProperty("BookingOnline")
    public boolean bookingOnline;

    @JsonProperty("recensioni")
    public List<Recensione> recensioni;

    @JsonProperty("NumRec")
    public int numRec;

    @JsonProperty("MediaRec")
    public double mediaRec;

    @JsonProperty("Email")
    public String email;


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

    public Ristorante(){}

    //getter e setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdRistoratore() { return idRistoratore; }
    public void setIdRistoratore(int idRistoratore) { this.idRistoratore = idRistoratore; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getCuisine() { return cuisine; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getWebsiteUrl() { return websiteUrl; }
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }

    public int getGreenStar() { return greenStar; }
    public void setGreenStar(int greenStar) { this.greenStar = greenStar; }

    public String getFacilitiesAndServices() { return facilitiesAndServices; }
    public void setFacilitiesAndServices(String facilitiesAndServices) { this.facilitiesAndServices = facilitiesAndServices; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isDelivery() { return delivery; }
    public void setDelivery(boolean delivery) { this.delivery = delivery; }

    public boolean isBookingOnline() { return bookingOnline; }
    public void setBookingOnline(boolean bookingOnline) { this.bookingOnline = bookingOnline; }

    public List<Recensione> getRecensioni() { return recensioni; }
    public void setRecensioni(List<Recensione> recensioni) { this.recensioni = recensioni; }

    public int getNumRec() { return numRec; }
    public void setNumRec(int numRec) { this.numRec = numRec; }

    public double getMediaRec() { return mediaRec; }
    public void setMediaRec(double mediaRec) { this.mediaRec = mediaRec; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

}
