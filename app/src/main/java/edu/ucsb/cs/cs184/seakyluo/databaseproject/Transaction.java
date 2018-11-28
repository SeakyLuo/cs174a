package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.widget.Toast;

import java.io.Serializable;
import java.sql.Date;

public class Transaction implements Serializable {
    public static final String TABLE_NAME = "Transaction", CID = "cid", FROM = "from", TO = "to", TIME = "time", TYPE = "type", AMOUNT = "amount";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + CID + " INTEGER NOT NULL, " +
                                                                                        TIME + " DATE, " +
                                                                                        TYPE + " TEXT, " +
                                                                                        AMOUNT + " REAL, " +
                                                                                        FROM + " INTEGER, " +
                                                                                        TO + " INTEGER, " +
                                                                                        "PRIMARY KEY(" + CID + ", " + TIME + "), " +
                                                                                        "FOREIGN KEY(" + CID +") REFERENCES "+ Customer.TABLE_NAME + ")";
    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
    public static final String DEPOSIT = "deposit", TOP_UP = "top-up", WITHDRAW = "withdraw", PURCHASE = "purchase", TRANSFER = "transfer",
            COLLECT = "collect", PAY_FRIEND = "pay-friend", WIRE = "wire", WRITE_CHECK = "write-check", ACCRUE_INTEREST = "accrue-interest",
            QUICK_CASH = "quick-cash", QUICK_REFILL = "quick-refill";
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
        return "INSERT INTO " + TABLE_NAME +" (" + CID  + ", " + TIME + ", " + TYPE + ", " + AMOUNT+ ", " + FROM + ", " + TO + ") " +
                "VALUES (" + cid + ", " + time + ", '" + type + "', " + amount + ", " + from + ", " + to + ")";
    }
    public static String InsertQuery(int cid, Date time, String type, double amount, int from, int to){
        return "INSERT INTO " + TABLE_NAME +" (" + CID + ", " + FROM + ", " + TO + ", " + TIME + ", " + TYPE + ", " + AMOUNT + ") " +
                "VALUES (" + cid + ", " + time + ", '" + type + ", " + amount + ", " + from + ", " + to + ")";
    }
    public static String getQuery(){
        return "SELECT t." + CID + ", t." + TIME + ", t." + TYPE + ", t." + AMOUNT + ", t." + FROM + ", t." + TO + " " +
                "FROM " + TABLE_NAME + " t";
    }

    public static void Deposit(int toAccount, double amount){
        Account acc1 = Account.findAccount(toAccount);
        acc1.modifyBalance(amount);
    }
    public static void TopUp(int from, int to, double amount){
        Account acc1 = Account.findAccount(from);
        Account acc2 = Account.findAccount(to);
        //to do
        // check if it is linked????
        if(acc1.getBalance() < amount){
            //error message
            return;
        }
        else if (!acc2.isPocket()) {
            //error message
            return;
        }
        acc1.modifyBalance(-amount);
        acc2.modifyBalance(amount);
    }
    public static void Withdraw(int from, double amount){
        Account acc1 = Account.findAccount(from);
        if(acc1.getBalance() < amount){
            //error message
            return;
        }
        acc1.modifyBalance(-amount);
    }
    public static void Purchase(int from, double amount){
        Account acc1 = Account.findAccount(from);
        if(!acc1.isPocket()) {
            //error message
            return;
        }
        else if (acc1.getBalance() < amount) {
            //error message
            return;
        }
        acc1.modifyBalance(-amount);

    }
    public static void Transfer(int from, int to, double amount){
        // should findaccount return a set of accounts instead of get (0)
        Account acc1 = Account.findAccount(from);
        Account acc2 = Account.findAccount(to);

        //f(acc1.getusers intersect acc2.getusers ==0){ error message}
        if(amount>2000){
            //error message
            return;
        }
        //else if(userid not in acc1.getusers || userid not in acc2.getusers ){error message}
        acc1.modifyBalance(-amount);
        acc2.modifyBalance(amount);

    }
    public static void Collect(int from, int to, double amount){
        Account acc1 = Account.findAccount(from);
        Account acc2 = Account.findAccount(to);
        if(!acc1.isPocket()) {
            //error message
            return;
        }
        else if(acc1.getBalance() < amount){
            //error message
            return;
        }
        acc1.modifyBalance(-amount);
        acc2.modifyBalance(amount * 0.97);

    }

    public static void Wire(int from, int to, double amount){
        Account acc1 = Account.findAccount(from);
        Account acc2 = Account.findAccount(to);
        if(acc1.isPocket()){
            //error message
            return;
        }
        else if(acc2.isPocket()){
            //error
            return;
        }
        //else if(userid not in acc1.getusers){error message}
        acc1.modifyBalance(-amount);
        acc2.modifyBalance(amount * 0.98);
        {
            //error message
        }
    }

    public static void PayFriend(int from, int to, double amount){
        Account acc1 = Account.findAccount(from);
        Account acc2 = Account.findAccount(to);
        if(!acc1.isPocket()) {
            //error message
            return;
        }
        else if(!acc2.isPocket()){
            //error message
            return;
        }
        else if(acc1.getBalance() < amount){
            //error message
            return;
        }
        acc1.modifyBalance(-amount);
        acc2.modifyBalance(amount);

    }

    public static void WriteCheck(int from, double amount){
        Account acc1 = Account.findAccount(from);
        if(acc1.getType()!="CHECKING") {
            //error message
            return;
        }
        acc1.modifyBalance(-amount);
        //where to save it  ??????

    }

    public static void AccrueInterest(int to, double amount){
        Account acc1 = Account.findAccount(to);
        acc1.modifyBalance(amount);

    }

    public static void QuickCash(int from, double amount){
        Account acc1 = Account.findAccount(from);
        acc1.modifyBalance(-amount);
    }
    public static void QuickRefill(int from, double amount){
        Account acc1 = Account.findAccount(from);
        acc1.modifyBalance(amount);
    }
    @Override
    public String toString(){
        return time.toString() + "---" + Customer.findCustomer(cid).getName() + "---" + type + "---$" + amount + ((from != 0) ? "---from " + from : "") + ((to != 0) ? "---to " + to : "") ;
    }
}