package com.example.the_knife.Ristoratore;
import java.util.ArrayList;
import java.util.List;

public class Ristorante {
    public int Id;
    public int IdRistoratore;
    public String Name;
    public String Address;
    public String Location;
    public String Price;
    public String Cuisine;
    public double Longitude;
    public double Latitude;
    public String PhoneNumber;
    public String WebsiteUrl;
    public int GreenStar;
    public String FacilitiesAndServices;
    public String Description;
    public boolean Delivery;
    public boolean BookingOnline;
    public List<Recensione> recensioni;
    public int NumRec;
    public double MediaRec;
    public String Email;


    public Ristorante(int id, int idRistoratore, String nome, String address, String location, String price, String cousine, double lati, double longi, String tel, String url, int stars, String service, String description, boolean delivery, boolean booking, String email ) {
        Id = id;
        IdRistoratore = idRistoratore;
        Name = nome ;
        Address = address;
        Location = location;
        Price = price;
        Cuisine = cousine;
        Longitude = longi;
        Latitude = lati;
        PhoneNumber = tel;
        WebsiteUrl = url;
        GreenStar = stars;
        FacilitiesAndServices = service;
        Description = description;
        Delivery = delivery;
        BookingOnline = booking;
        Email = email;
        recensioni = null;
        NumRec = 0;
        MediaRec = 0;
    }

    public Ristorante(){

    }

}
