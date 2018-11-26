package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.io.Serializable;

public class Customer implements Serializable {
    public static final String ID = "id", NAME = "name", ADDRESS = "address", PIN = "pin";
    public static final String CREATE_TABLE = "CREATE TABLE Customer(" + ID + " INTEGER NOT NULL, " +
                                                                        NAME + " CHAR(30), " +
                                                                        ADDRESS +" CHAR(50), " +
                                                                        PIN  + " CHAR(4) NOT NULL UNIQUE, " +
                                                                        " PRIMARY KEY(" + ID +"))";

    private int id;
    private String name, address, pin;

    public Customer(int id, String name, String address, String pin){
        this.id = id;
        this.name = name;
        this.address = address;
        this.pin = pin;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPin() { return pin; }
    public String insertQuery(){
        return "INSERT INTO Accounts (" + ID + ", " + NAME + ", " + ADDRESS + ", " + PIN + ")" +
                "VALUES (" + id + ", " + name + ", " + address + ", " + pin + ")";
    }

    public static boolean VerifyPin(String PIN){
        return false;
    }

    public void SetPin(String OldPIN, String NewPin){
        if (OldPIN == pin){

        }else{

        }
    }
}
