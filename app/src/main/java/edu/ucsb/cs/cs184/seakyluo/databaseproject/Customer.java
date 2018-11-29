package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    public static final String TABLE_NAME = "Customer", ID = "cid", NAME = "name", ADDRESS = "address", PIN = "pin", PREACCOUNT = "preset_account", PREAMOUNT = "preset_amount";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" + ID + " INTEGER NOT NULL, " +
                                                                                    NAME + " CHAR(40), " +
                                                                                    ADDRESS +" CHAR(40), " +
                                                                                    PIN  + " CHAR(4) NOT NULL, " +
            PREACCOUNT + " INTEGER, " +
                                                                                    PREAMOUNT + " REAL, " +
                                                                                    "PRIMARY KEY(" + ID +"))";
    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME + " CASCADE Constraints";
    private int cid, preaccount = 0;
    private String name, address, pin = "1717";
    private double preamount = 0;

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
    public double getPreamount() { return preamount; }
    public int getPreaccount() { return preaccount; }
    public void setPreaccount(int preaccount){
        this.preaccount = preaccount;
        DatabaseHelper.run("UPDATE " + TABLE_NAME + " SET " + PREACCOUNT + "=" + preaccount + " WHERE " + ID + "=" + cid);
    }
    public void setPreamount(double preamount){
        this.preamount = preamount;
        DatabaseHelper.run("UPDATE " + TABLE_NAME + " SET " + PREAMOUNT + "=" + preamount + " WHERE " + ID + "=" + cid);
    }
    public String insertQuery(){
        return InsertQuery(cid, name, address, pin, preaccount, preamount);
    }
    public String deleteQuery(){
        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=" + cid;
    }
    public boolean OwnsAcount(int accountid){
        return DatabaseHelper.get(Owns.getQuery() + " WHERE o." + Owns.CID + "=" + cid + " AND o." + Owns.AID + "=" + accountid, Owns.TABLE_NAME).size() == 0;
    }
    public static String InsertQuery(int id, String name, String address, String pin, int preaccount, double preamount){
        return "INSERT INTO " + TABLE_NAME +" (" + ID + ", " + NAME + ", " + ADDRESS + ", " + PIN + ", " + PREACCOUNT + ", " + PREAMOUNT + ") " +
                "VALUES (" + id + ", '" + name + "', '" + address + "', '" + pin + "', " + preaccount + ", " + preamount +")";
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
