/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author aitor
 */

public class Person {
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty izena;
    private final SimpleStringProperty abizena;
    private final SimpleStringProperty korreoa;
    
    public Person(int id, String fName, String lName, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.izena = new SimpleStringProperty(fName);
        this.abizena = new SimpleStringProperty(lName);
        this.korreoa = new SimpleStringProperty(email);
    }

    public Person(SimpleIntegerProperty id, SimpleStringProperty izena, SimpleStringProperty abizena, SimpleStringProperty korreoa) {
        this.id = id;
        this.izena = izena;
        this.abizena = abizena;
        this.korreoa = korreoa;
    }
    
    public int getId(){
        return id.get();
    } 
    public void setId(int pid){
        id.set(pid);
    }
    public String getIzena() {
        return izena.get();
    }
    public void setIzena(String fName) {
        izena.set(fName);
    }
    public String getAbizena() {
        return abizena.get();
    }
    public void setAbizena(String fName) {
        abizena.set(fName);
    }
    public String getKorreoa() {
        return korreoa.get();
    }
    public void setKorreoa(String fName) {
        korreoa.set(fName);
    }

}