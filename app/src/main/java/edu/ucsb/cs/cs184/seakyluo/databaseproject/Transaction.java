package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.widget.Toast;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Transaction implements Serializable {
    public static final String TABLE_NAME = "Transaction", CID = "cid", FROM = "from_account", TO = "to_account", TIME = "time", TYPE = "type", AMOUNT = "amount";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + CID + " INTEGER NOT NULL, " +
                                                                                        TIME + " DATE, " +
                                                                                        TYPE + " CHAR(30), " +
                                                                                        AMOUNT + " REAL, " +
                                                                                        FROM + " INTEGER, " +
                                                                                        TO + " INTEGER)";
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
        this.type = type.trim();
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    public int getId() { return cid; }
    public int getFrom() { return from; }
    public int getTo() { return to; }
    public Date getTime() { return time; }
    public String getType() { return type; }
    public boolean isType(String type) { return this.type.equals(type); }
    public double getAmount() { return amount; }
    public String insertQuery(){
        return "INSERT INTO " + TABLE_NAME +" (" + CID  + ", " + TIME + ", " + TYPE + ", " + AMOUNT+ ", " + FROM + ", " + TO + ") " +
                "VALUES (" + cid + ", " + time + ", '" + type + "', " + amount + ", " + from + ", " + to + ")";
    }
    public String deleteQuery(){
        return "DELETE FROM " + TABLE_NAME + " WHERE " + CID + "=" + cid + " AND " + TIME + "=" + time;
    }
    public static String InsertQuery(int cid, Date time, String type, double amount, int from, int to){
        return "INSERT INTO " + TABLE_NAME +" (" + CID  + ", " + TIME + ", " + TYPE + ", " + AMOUNT + ", " + FROM + ", " + TO + ") " +
                "VALUES (" + cid + ", " + DatabaseHelper.TimeQuery(time) + ", '" + type + "', " + amount + ", " + from + ", " + to + ")";
    }
    public static String InsertQuery(int cid, int year, int month, int day, String type, double amount, int from, int to){
        return InsertQuery(cid, new Date(year, month, day), type, amount, from ,to);
    }
    public static String getQuery(){
        return "SELECT t." + CID + ", t." + TIME + ", t." + TYPE + ", t." + AMOUNT + ", t." + FROM + ", t." + TO + " " +
                "FROM " + TABLE_NAME + " t";
    }
    public static ArrayList<Transaction> MonthlyStatement(int cid){
        // TODO: you wen ti
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction: (ArrayList<Transaction>) DatabaseHelper.get(getQuery(), TABLE_NAME))
            if (cid == transaction.cid && DatabaseHelper.time.getMonth() - 1 == transaction.time.getMonth())
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
        Account.findAccount(from).modifyBalance(-amount);
    }
    public static void QuickRefill(int from, double amount) throws Account.NotEnoughMoneyException{
        // TODO: see project description
        Account.findAccount(from).modifyBalance(amount);
    }
    public static void MakeTransation(String type, int from, int to, double amount) throws Exception {
        switch (type){
            case Transaction.DEPOSIT:
                Transaction.Deposit(to, amount);
            case Transaction.TOP_UP:
                Transaction.TopUp(from, to, amount);
            case Transaction.WITHDRAW:
                Transaction.Withdraw(from, amount);
            case Transaction.PURCHASE:
                Transaction.Purchase(from, amount);
            case Transaction.TRANSFER:
                Transaction.Transfer(from, to, amount);
            case Transaction.COLLECT:
                Transaction.Collect(from, to, amount);
            case Transaction.WIRE:
                Transaction.Wire(from, to, amount);
            case Transaction.PAY_FRIEND:
                Transaction.PayFriend(from, to, amount);
            case Transaction.QUICK_CASH:
                Transaction.QuickCash(from, amount);
            case Transaction.QUICK_REFILL:
                Transaction.QuickRefill(from, amount);
        }
    }
    @Override
    public String toString(){
        return time + "---" + Customer.findCustomer(cid).getName() + "---" + type + "---$" + amount + ((from != 0) ? "---from " + from : "") + ((to != 0) ? "---to " + to : "") ;
    }

    public static class TransactionException extends Exception{
        public TransactionException(String message){
            super(message);
        }
    }
}