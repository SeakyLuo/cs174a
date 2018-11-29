package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Owns implements Serializable {
    public static final String TABLE_NAME = "Owns", CID = "cid", AID = "aid", ISPRIMARY = "isPrimary";
//    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" + CID + " INTEGER NOT NULL, " +
//                                                                                    AID + " INTEGER NOT NULL, " +
//                                                                                    ISPRIMARY +" INTEGER, "+
//                                                                                    "PRIMARY KEY(" + CID + ", " + AID +"), " +
//                                                                                    "FOREIGN KEY(" + CID + ") REFERENCES " + Customer.TABLE_NAME + " ON DELETE CASCADE, " +
//                                                                                    "FOREIGN KEY(" + AID + ") REFERENCES " + Account.TABLE_NAME + " ON DELETE CASCADE)";
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
        return "INSERT INTO " + TABLE_NAME +" (" + CID + ", " + AID + ", " + ISPRIMARY + ") " +
                "VALUES (" + cid + ", " + aid + ", " + isPrimary + ")";
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
