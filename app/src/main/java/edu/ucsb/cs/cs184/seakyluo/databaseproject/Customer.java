package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    public static final String TABLE_NAME = "Customer", ID = "cid", NAME = "name", ADDRESS = "address", PIN = "pin";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" + ID + " INTEGER NOT NULL, " +
                                                                                    NAME + " CHAR(40), " +
                                                                                    ADDRESS +" CHAR(40), " +
                                                                                    PIN  + " CHAR(4) NOT NULL, " +
                                                                                    "PRIMARY KEY(" + ID +"))";
    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME + " CASCADE Constraints";
    private int cid;
    private String name, address, pin = "1717";

    public Customer(int cid, String name, String address, String pin){
        this.cid = cid;
        this.name = name.trim();
        this.address = address.trim();
        this.pin = pin;
    }
    public int getId() { return cid; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPin() { return pin; }
    public String insertQuery(){
        return "INSERT INTO " + TABLE_NAME +" (" + ID + ", " + NAME + ", " + ADDRESS + ", " + PIN + ") " +
                "VALUES (" + cid + ", '" + name + "', '" + address + "', '" + pin + "')";
    }
    public String deleteQuery(){
        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=" + cid;
    }
    public boolean OwnsAcount(int accountid){
        return DatabaseHelper.get(Owns.getQuery() + " WHERE o." + Owns.CID + "=" + cid + " AND o." + Owns.AID + "=" + accountid, Owns.TABLE_NAME).size() == 0;
    }
    public static String InsertQuery(int id, String name, String address, String pin){
        return "INSERT INTO " + TABLE_NAME +" (" + ID + ", " + NAME + ", " + ADDRESS + ", " + PIN + ") " +
                "VALUES (" + id + ", '" + name + "', '" + address + "', '" + pin + "')";
    }
    public static String getQuery(){
        return "SELECT * FROM " + TABLE_NAME + " c";
    }
    public static Customer findCustomer(int id){
        try{
            return ((ArrayList<Customer>) DatabaseHelper.get(getQuery() + " WHERE c." + ID + "=" + id, TABLE_NAME)).get(0);
        }catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static boolean VerifyPin(int id, String pin){
        if (DatabaseHelper.user != null && DatabaseHelper.user.getId() == id) return DatabaseHelper.user.getPin().equals(pin);
        return DatabaseHelper.get(getQuery() + " WHERE c." + ID + "=" + id + " AND c." + PIN + "=" + pin, TABLE_NAME).size() > 0;
    }

    public void SetPin(String pin){
        this.pin = pin;
    }

    @Override
    public String toString(){
        return name + "---" + cid;
    }
}
