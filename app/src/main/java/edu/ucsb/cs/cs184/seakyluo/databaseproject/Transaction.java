package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.widget.Toast;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

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
    public double getAmount() { return amount; }
    public String insertQuery(){
        return "INSERT INTO " + TABLE_NAME +" (" + CID  + ", " + TIME + ", " + TYPE + ", " + AMOUNT+ ", " + FROM + ", " + TO + ") " +
                "VALUES (" + cid + ", " + time + ", '" + type + "', " + amount + ", " + from + ", " + to + ")";
    }
    public String deleteQuery(){
        return "DELETE FROM " + TABLE_NAME + " WHERE " + CID + "=" + cid + " AND " + TIME + "=" + time;
    }
    public static String InsertQuery(int cid, Date time, String type, double amount, int from, int to){
        return "INSERT INTO " + TABLE_NAME +" (" + CID + ", " + FROM + ", " + TO + ", " + TIME + ", " + TYPE + ", " + AMOUNT + ") " +
                "VALUES (" + cid + ", " + time + ", '" + type + ", " + amount + ", " + from + ", " + to + ")";
    }
    public static String getQuery(){
        return "SELECT t." + CID + ", t." + TIME + ", t." + TYPE + ", t." + AMOUNT + ", t." + FROM + ", t." + TO + " " +
                "FROM " + TABLE_NAME + " t";
    }
    public static ArrayList<Transaction> MonthlyStatement(int userid){
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction: (ArrayList<Transaction>) DatabaseHelper.get(getQuery(), TABLE_NAME))
            if (userid == transaction.cid && DatabaseHelper.time.getMonth() - 1 == transaction.time.getMonth())
                transactions.add(transaction);
        return transactions;
    }

    public static void Deposit(int toAccount, double amount) throws Account.NotEnoughMoneyException{
        Account.findAccount(toAccount).modifyBalance(amount);
    }
    public static void TopUp(int from, int to, double amount) throws Account.NotEnoughMoneyException{
        Account.findAccount(from).modifyBalance(-amount);
        Account.findAccount(to).modifyBalance(amount);
    }
    public static void Withdraw(int from, double amount) throws Account.NotEnoughMoneyException{
        Account.findAccount(from).modifyBalance(-amount);
    }
    public static void Purchase(int from, double amount) throws Account.NotEnoughMoneyException{
        Account.findAccount(from).modifyBalance(-amount);
    }
    public static void Transfer(int from, int to, double amount) throws Exception {
        if(amount > 2000){
            throw new TransactionException("Cannot Exceed $2000!");
        }
        Account.findAccount(from).modifyBalance(-amount);
        Account.findAccount(to).modifyBalance(amount);

    }
    public static void Collect(int from, int to, double amount) throws Account.NotEnoughMoneyException{
        Account.findAccount(from).modifyBalance(-amount);
        Account.findAccount(to).modifyBalance(amount * 0.97);

    }

    public static void Wire(int from, int to, double amount) throws Account.NotEnoughMoneyException {
        Account.findAccount(from).modifyBalance(-amount);
        Account.findAccount(to).modifyBalance(amount * 0.98);
    }

    public static void PayFriend(int from, int to, double amount) throws Account.NotEnoughMoneyException{
        Account.findAccount(from).modifyBalance(-amount);
        Account.findAccount(to).modifyBalance(amount);
    }

    public static void WriteCheck(int from, double amount)  throws Account.NotEnoughMoneyException{
        Account.findAccount(from).modifyBalance(-amount);
    }

    public static void AccrueInterest(Account account){
        // TODO: see project description
        try {
            account.modifyBalance(account.getBalance() * account.getMonthlyInterest());
        } catch (Account.NotEnoughMoneyException e) {
            e.printStackTrace();
        }
    }

    public static void QuickCash(int from, double amount) throws Account.NotEnoughMoneyException{
        // TODO: see project description
        Account acc1 = Account.findAccount(from);
        acc1.modifyBalance(-amount);
    }
    public static void QuickRefill(int from, double amount) throws Account.NotEnoughMoneyException{
        // TODO: see project description
        Account acc1 = Account.findAccount(from);
        acc1.modifyBalance(amount);
    }
    @Override
    public String toString(){
        return time.toString() + "---" + Customer.findCustomer(cid).getName() + "---" + type + "---$" + amount + ((from != 0) ? "---from " + from : "") + ((to != 0) ? "---to " + to : "") ;
    }

    public static class TransactionException extends Exception{
        public TransactionException(String message){
            super(message);
        }
    }
}