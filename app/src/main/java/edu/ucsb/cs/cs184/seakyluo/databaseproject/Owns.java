package edu.ucsb.cs.cs184.seakyluo.databaseproject;

public class Owns {
    public static final String CID = "cid", AID = "aid", ISPRIMARY = "isPrimary";
    public static final String CREATE_TABLE = "CREATE TABLE Owns(" + CID + " INTEGER NOT NULL, " +
                                                                    AID + " INTEGER, " +
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
        return "INSERT INTO Owns (" + CID + ", " + AID + ", " + ISPRIMARY + ")" +
                "VALUES (" + cid + ", " + aid + ", " + isPrimary + ")";
    }
}
