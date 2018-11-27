package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.widget.Toast;

import java.sql.Date;

public class Transaction {
    public static final String TABLE_NAME = "Transaction", CID = "cid", FROM = "from", TO = "to", TIME = "time", TYPE = "type", AMOUNT = "amount";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + CID + "INTEGER NOT NULL, " +
                                                                                        TIME + " DATE, " +
                                                                                        TYPE + " TEXT, " +
                                                                                        AMOUNT + " REAL" +
                                                                                        FROM + " INTEGER, " +
                                                                                        TO + " INTEGER, " +
                                                                                        "PRIMARY KEY(" + CID + ", " + TIME + ")" +
                                                                                        "FOREIGN KEY(" + CID +") REFERENCES "+ Customer.TABLE_NAME + ")";
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
        return "INSERT INTO " + TABLE_NAME +" (" + CID + ", " + FROM + ", " + TO + ", " + TIME + ", " + TYPE + ", " + AMOUNT + ") " +
                "VALUES (" + cid + ", " + from + ", " + to + ", " + time + ", " + type + ", " + amount + ")";
    }
    public static String getQuery(){
        return "SELECT t." + CID + ", t." + FROM + ", t." + TO + ", t." + TIME + ", t." + TYPE + ", t." + AMOUNT + " " +
                "FROM " + TABLE_NAME + " t";
    }
    public static void Deposit(int toAccount, double amount){

    }
    public static void TopUp(int fromAccount, double amount){

    }
    public static void Withdraw(int fromAccount, double amount){

    }
    public static void Purchase(int fromAccount, double amount){

    }
    public static void Transfer(int from, int to, double amount){

    }
    public static void Collect(int from, int to, double amount){

    }

    public static void Wire(int from, int to, double amount){

    }

    public static void PayFriend(int from, int to, double amount){

    }

    public static void WriteCheck(int from, double amount){

    }

    public static void AccrueInterest(int to, double amount){

    }
    @Override
    public String toString(){
        return time.toString() + "---" + Customer.findCustomer(cid).getName() + "---" + type + "---$" + amount + ((from != 0) ? "---from " + from : "") + ((to != 0) ? "---to " + to : "") ;
    }
}
