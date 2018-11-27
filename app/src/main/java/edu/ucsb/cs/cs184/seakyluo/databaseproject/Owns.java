package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.io.Serializable;

public class Owns implements Serializable {
    public static final String TABLE_NAME = "Owns", CID = "cid", AID = "aid", ISPRIMARY = "isPrimary";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" + CID + " INTEGER NOT NULL, " +
                                                                                AID + " INTEGER NOT NULL, " +
                                                                                ISPRIMARY +" BIT NOT NULL, "+
                                                                                "PRIMARY KEY(" + CID + ", " + AID +"), " +
                                                                                "FOREIGN KEY(" + CID + ") REFERENCES Customer) ON DELETE CASCADE, " +
                                                                                "FOREIGN KEY(" + AID + ") REFERENCES Account) ON DELETE CASCADE)";
    private int cid, aid;
    private boolean isPrimary;

    public Owns(int cid, int aid, boolean isPrimary){
        this.cid = cid;
        this.aid = aid;
        this.isPrimary = isPrimary;
    }

    public int getCid() { return cid; }
    public int getAid() { return aid; }
    public boolean isPrimary() { return isPrimary; }
    public String insertQuery(){
        return "INSERT INTO " + TABLE_NAME +" (" + CID + ", " + AID + ", " + ISPRIMARY + ") " +
                "VALUES (" + cid + ", " + aid + ", " + isPrimary + ")";
    }
    public static String InsertQuery(int cid, int aid, boolean isPrimary){
        return "INSERT INTO " + TABLE_NAME +" (" + CID + ", " + AID + ", " + ISPRIMARY + ") " +
                "VALUES (" + cid + ", " + aid + ", " + isPrimary + ")";
    }
    public static String getQuery(){
        return "SELECT o." + CID + ", o." + AID + ", o." + ISPRIMARY + " " +
                "FROM " + TABLE_NAME + " o";
    }
}
