package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.util.ArrayList;

public class Customer{
    public static final String TABLE_NAME = "Customer", ID = "id", NAME = "name", ADDRESS = "address", PIN = "pin";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" + ID + " INTEGER NOT NULL, " +
                                                                                    NAME + " TEXT, " +
                                                                                    ADDRESS +" TEXT, " +
                                                                                    PIN  + " CHAR(4) NOT NULL, " +
                                                                                    " PRIMARY KEY(" + ID +"))";

    private int id;
    private String name, address, pin = "1717";

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
        return "INSERT INTO " + TABLE_NAME +" (" + ID + ", " + NAME + ", " + ADDRESS + ", " + PIN + ") " +
                "VALUES (" + id + ", " + name + ", " + address + ", " + pin + ")";
    }
    public static String getQuery(){
        return "SELECT c." + ID + ", c." + NAME + ", c." + ADDRESS + ", c." + PIN + " " +
                "FROM " + TABLE_NAME + " c";
    }
    public static Customer findCustomer(int id){
        return ((ArrayList<Customer>) DatabaseHelper.get(getQuery() + " WHERE c." + ID + "=" + id, TABLE_NAME)).get(0);
    }

    public static boolean VerifyPin(String PIN){
        return DatabaseHelper.user.pin.equals(PIN);
    }

    public void SetPin(String pin){
        this.pin = pin;
    }
}
