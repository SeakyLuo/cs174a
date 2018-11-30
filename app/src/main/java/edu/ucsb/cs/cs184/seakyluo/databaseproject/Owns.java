package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Owns implements Serializable {
    public static final String TABLE_NAME = "Owns", CID = "cid", AID = "aid", ISPRIMARY = "isPrimary";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" + CID + " INTEGER NOT NULL, " +
                                                                                    AID + " INTEGER NOT NULL, " +
                                                                                    ISPRIMARY +" INTEGER, "+
                                                                                    "PRIMARY KEY(" + CID + ", " + AID +"))";
    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
    private int cid, aid;
    private int isPrimary;

    public Owns(int cid, int aid, int isPrimary){
        this.cid = cid;
        this.aid = aid;
        this.isPrimary = isPrimary;
    }
    public int getCid() { return cid; }
    public int getAid() { return aid; }
    public boolean isPrimary() { return isPrimary == 1; }
    public String insertQuery(){
        return InsertQuery(cid, aid, isPrimary);
    }
    public static ArrayList<Customer> findOwners(int aid){
        return (ArrayList<Customer>) DbHelper.get(getQuery() + " WHERE o." + AID + "=" + aid, Customer.TABLE_NAME);
    }
    public static Customer findPrimaryOwner(int aid){
        try{
            return Customer.findCustomer(((ArrayList<Owns>) DbHelper.get(getQuery() + " WHERE o." + AID + "=" + aid + " AND o." + ISPRIMARY + "=1", Customer.TABLE_NAME)).get(0).getCid());
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }
    public static boolean PrimarilyOwns(int cid, int aid){
        return ((ArrayList<Owns>) DbHelper.get(getQuery() + " WHERE o." + AID + "=" + aid + " AND o." + ISPRIMARY + "=1", Customer.TABLE_NAME)).size() == 0;
    }
    public String deleteQuery(){
        return "DELETE FROM " + TABLE_NAME + " WHERE " + CID + "=" + cid + " AND " + AID + "=" + aid;
    }
    public static String InsertQuery(int cid, int aid, int isPrimary){
        return "INSERT INTO " + TABLE_NAME +" (" + CID + ", " + AID + ", " + ISPRIMARY + ") " +
                "VALUES (" + cid + ", " + aid + ", " + isPrimary + ")";
    }
    public static String getQuery(){
        return "SELECT * FROM " + TABLE_NAME + " o";
    }

    @Override
    public String toString(){
        return cid + " is a" + (isPrimary() ? " primary" : "") + " owner of " + aid;
    }
}
