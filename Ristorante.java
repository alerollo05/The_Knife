package com.example.the_knife.Ristoratore;

import java.util.List;

public class Ristorante {
    public int Id;
    public int IdRistoratore;
    public String Name;
    public String Address;
    public String Location;
    public String Price;
    public String Email;
    public String Cuisine;
    public double Longitude;
    public double Latitude;
    public String PhoneNumber;
    public String Url;
    public String WebsiteUrl;
    public String Award;
    public int GreenStar;
    public String FacilitiesAndServices;
    public String Description;
    public boolean Delivery;
    public boolean BookingOnline;
    public List<Recensione> recensioni;
    public int NumRec;
    public double MediaRec;

    //getter e setter
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdRistoratore() {
        return IdRistoratore;
    }

    public void setIdRistoratore(int idRistoratore) {
        IdRistoratore = idRistoratore;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }

    public String getCuisine() {
        return Cuisine;
    }

    public void setCuisine(String cuisine) {
        Cuisine = cuisine;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getWebsiteUrl() {
        return WebsiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        WebsiteUrl = websiteUrl;
    }

    public String getAward() {
        return Award;
    }

    public void setAward(String award) {
        Award = award;
    }

    public int getGreenStar() {
        return GreenStar;
    }

    public void setGreenStar(int greenStar) {
        GreenStar = greenStar;
    }

    public String getFacilitiesAndServices() {
        return FacilitiesAndServices;
    }

    public void setFacilitiesAndServices(String facilitiesAndServices) {
        FacilitiesAndServices = facilitiesAndServices;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isDelivery() {
        return Delivery;
    }

    public void setDelivery(boolean delivery) {
        Delivery = delivery;
    }

    public boolean isBookingOnline() {
        return BookingOnline;
    }

    public void setBookingOnline(boolean bookingOnline) {
        BookingOnline = bookingOnline;
    }

    public List<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    public int getNumRec() {
        return NumRec;
    }

    public void setNumRec(int numRec) {
        NumRec = numRec;
    }

    public double getMediaRec() {
        return MediaRec;
    }

    public void setMediaRec(double mediaRec) {
        MediaRec = mediaRec;
    }
}
