package com.example.paul.projectfinal;

/*
 * File: atmEntry.java
 * Last edited: 20-10-2017
 * By: Paul Heijen
 *
 * Model class for an ATM Entry */

public class atmEntry {

    public String id;
    public String straatNaam;
    public String coordinaten;
    public String bank;

    //default contructor (required for firebase)
    public atmEntry() {

    }

    //contructor
    public atmEntry (String aid, String astraatNaam, String acoordinaten, String aBank){
        this.id=aid;
        this.straatNaam = astraatNaam;
        this.coordinaten = acoordinaten;
        this.bank = aBank;
    }


    public void setID(String aid) {
        this.id = aid;
    }

    public String getID() {
        return this.id;
    }

    public void setstraatNaam(String astraatNaam) {
        this.straatNaam = astraatNaam;
    }

    public String getstraatNaam() {
        return this.straatNaam;
    }

    public void setBank(String aBank) {
        this.bank = aBank;
    }

    public String getBank() {
        return this.bank;
    }

    public void setcoordinaten(String acoordinaten) {
        this.coordinaten = acoordinaten;
    }

    public String getcoordinaten() {
        return this.coordinaten;
    }


}


