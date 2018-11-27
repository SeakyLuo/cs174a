package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.sql.Date;

public class Transaction {
    public static final String TRANSACTION = "Transaction", CID = "cid", FROM = "from", TO = "to", TIME = "time", TYPE = "type", AMOUNT = "amount";
    public static final String CREATE_TABLE = "CREATE TABLE " + TRANSACTION + "(" + CID + "INTEGER NOT NULL, " +
                                                                                        TIME + " DATE, " +
                                                                                        TYPE + " TEXT, " +
                                                                                        AMOUNT + " REAL" +
                                                                                        FROM + " INTEGER, " +
                                                                                        TO + " INTEGER, " +
                                                                                        "PRIMARY KEY(" + CID + ", " + TIME + ")" +
                                                                                        "FOREIGN KEY(" + CID +") REFERENCES "+ Customer.CUSTOMER + ")";
    public static final String DEPOSIT = "deposit", TOP_UP = "top-up", WITHDRAW = "withdraw", PURCHASE = "purchase", TRANSFER = "transfer",
            COLLECT = "collect", PAY_FRIEND = "pay-friend", WIRE = "wire", WRITE_CHECK = "write-check", ACCRUE_INTEREST = "accrue-interest";
    private int cid, from = 0, to = 0;
    private Date time;
    private String type;
    private double amount;

    public Transaction(int cid, Date time, String type, double amount, int from, int to){
        this.cid = cid;
        this.time = time;
        this.type = type;
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    public int getId() { return cid; }
    public int getFrom() { return from; }
    public int getTo() { return to; }
    public Date getTime() { return time; }
    public String getType() { return type; }
    public String insertQuery(){
        return "INSERT INTO " + TRANSACTION +" (" + CID + ", " + FROM + ", " + TO + ", " + TIME + ", " + TYPE + ", " + AMOUNT + ") " +
                "VALUES (" + cid + ", " + from + ", " + to + ", " + time + ", " + type + ", " + amount + ")";
    }
    public static String getQuery(){
        return "SELECT t." + CID + ", t." + FROM + ", t." + TO + ", t." + TIME + ", t." + TYPE + ", t." + AMOUNT + " " +
                "FROM " + TRANSACTION + " t";
    }
}
