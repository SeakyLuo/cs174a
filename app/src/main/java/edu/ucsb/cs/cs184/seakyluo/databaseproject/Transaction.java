package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.sql.Date;

public class Transaction {
    public static final String CID = "cid", FROM = "from", TO = "to", TIME = "time", TYPE = "type";
    public static final String CREATE_TABLE = "CREATE TABLE Transaction(" + CID + "INTEGER, " +
                                                                                FROM + " INTEGER, " +
                                                                                TO + " INTEGER, " +
                                                                                TIME + " DATE, " +
                                                                                TYPE + " VAR(20), " +
                                                                                "PRIMARY KEY(" + CID + " , " + TIME + "))";
    public static final String DEPOSIT = "deposit", TOP_UP = "top_up", WITHDRAWAL = "withdrawal", PURCHASE = "purchase", TRANSFER = "transfer",
            COLLECT = "collect", PAY_FRIEND = "pay_friend", WIRE = "wire", WRITE_CHECK = "write_check", ACCRUE_INTEREST = "accrue interest";
    private int cid, from, to;
    private Date time;
    private String type;

    public Transaction(int cid, int from, Date time, String type){
        this.cid = cid;
        this.from = from;
        this.time = time;
        this.type = type;
    }

    public Transaction(int cid, int from, int to, Date time, String type){
        this.cid = cid;
        this.from = from;
        this.to = to;
        this.time = time;
        this.type = type;
    }

    public int getId() { return cid; }
    public int getFrom() { return from; }
    public int getTo() { return to; }
    public Date getTime() { return time; }
    public String getType() { return type; }
    public String insertQuery(){
        return "INSERT INTO Accounts (" + CID + ", " + FROM + ", " + TO + ", " + TIME + ", " + TYPE + ")" +
                "VALUES (" + cid + ", " + from + ", " + to + ", " + time + ", " + type + ")";
    }
}
