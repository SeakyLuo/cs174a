package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.util.Log;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Transaction implements Serializable {
    public static final String TABLE_NAME = "Transaction", CID = "cid", FROM = "from_account", TO = "to_account", TIME = "time", TYPE = "type", AMOUNT = "amount";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + CID + " INTEGER NOT NULL, " +
                                                                                        TIME + " DATE, " +
                                                                                        TYPE + " CHAR(30), " +
                                                                                        AMOUNT + " REAL, " +
                                                                                        FROM + " INTEGER, " +
                                                                                        TO + " INTEGER, " +
                                                                                        "PRIMARY KEY(" + CID + ", " + TIME + ", " + TYPE + "), " +
                                                                                        "FOREIGN KEY(" + CID + ") REFERENCES " + Customer.TABLE_NAME + ")";
//    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + CID + " INTEGER NOT NULL, " +
//                                                                                        TIME + " DATE, " +
//                                                                                        TYPE + " CHAR(30), " +
//                                                                                        AMOUNT + " REAL, " +
//                                                                                        FROM + " INTEGER, " +
//                                                                                        TO + " INTEGER, " +
//                                                                                        "PRIMARY KEY(" + CID + ", " + TIME + ", " + TYPE + "), " +
//                                                                                        "FOREIGN KEY(" + CID + ") REFERENCES " + Customer.TABLE_NAME + ", " +
//                                                                                        "FOREIGN KEY(" + FROM + ") REFERENCES " + Account.TABLE_NAME + ", " +
//                                                                                        "FOREIGN KEY(" + TO + ") REFERENCES " + Account.TABLE_NAME + ")";
    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
    public static final String DEPOSIT = "Deposit", TOP_UP = "Top-up", WITHDRAW = "Withdraw", PURCHASE = "Purchase", TRANSFER = "Transfer",
            COLLECT = "Collect", PAY_FRIEND = "Pay-Friend", WIRE = "Wire", WRITE_CHECK = "Write-Check", ACCRUE_INTEREST = "Accrue-Interest",
            QUICK_CASH = "Quick-Cash", QUICK_REFILL = "Quick-Refill";
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
        return InsertQuery(cid, time, type, amount, from, to);
    }
    public String deleteQuery(){
        return "DELETE FROM " + TABLE_NAME + " WHERE " + CID + "=" + cid + " AND " + TIME + "=" + time;
    }
    public static String InsertQuery(int cid, Date time, String type, double amount, int from, int to){
        return InsertQuery(cid, DbHelper.getYear(time), DbHelper.getMonth(time), DbHelper.getDay(time), type, amount, from, to);
    }
    public static String InsertQuery(int cid, int year, int month, int day, String type, double amount, int from, int to){
        return "INSERT INTO " + TABLE_NAME +" (" + CID  + ", " + TIME + ", " + TYPE + ", " + AMOUNT + ", " + FROM + ", " + TO + ") " +
                "VALUES (" + cid + ", " + DbHelper.TimeQuery(year, month, day) + ", '" + type + "', " + amount + ", " + from + ", " + to + ")";
    }
    public static String getQuery(){
        return "SELECT * FROM " + TABLE_NAME + " t";
    }
    public static ArrayList<Transaction> MonthlyStatement(int cid){
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Owns owns: (ArrayList<Owns>) DbHelper.get(Owns.getQuery() + " WHERE o." + CID + "=" + cid + " AND " + Owns.ISPRIMARY + "=1", Owns.TABLE_NAME))
            for (Transaction transaction: Account.findTransactions(owns.getAid()))
                if (DbHelper.getMonth(transaction.getTime()) == DbHelper.getMonth() - 1)
                    transactions.add(transaction);
        return transactions;
    }
    public static void Deposit(int toAccount, double amount){
        try {
            Account.findAccount(toAccount).modifyBalance(amount);
        } catch (Account.NotEnoughMoneyException e) {
            e.printStackTrace();
        }
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
        double sum = 0;
        double average = 0;
        double last_day_balance = account.getBalance();
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction: Account.findTransactions(account.getId()))
            if (DbHelper.getMonth() - DbHelper.getMonth(transaction.getTime()) == 1)
                transactions.add(transaction);
        if (transactions.isEmpty()){
            try {
                account.modifyBalance(account.getBalance() * account.getMonthlyInterest());
            } catch (Account.NotEnoughMoneyException e) {
                e.printStackTrace();
            }
            return;
        }
        int year = DbHelper.getYear(transactions.get(0).time);
        int month = DbHelper.getMonth(transactions.get(0).time);
        Integer[] day_array = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = day_array[month - 1];
        days += (month == 2 && year % 4 == 0 && year % 400 != 0) ? 1 : 0;
        int today = days;
        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return (int) (o2.getTime().getTime() - o1.getTime().getTime());
            }
        });
        for (Transaction transaction: transactions){
            sum += last_day_balance * (today - DbHelper.getDay(transaction.getTime()) + 1);
            today = DbHelper.getDay(transaction.getTime());
            last_day_balance = last_day_balance - transaction.getAmount();
        }
        sum += (today - 1) * last_day_balance;
        average = sum/days;
        try {
            account.modifyBalance(average * account.getMonthlyInterest());
        } catch (Account.NotEnoughMoneyException e) {
            e.printStackTrace();
        }
    }
    public static void QuickCash(int from, double amount) throws Account.NotEnoughMoneyException{
        Account.findAccount(from).modifyBalance(-amount);
    }
    public static void QuickRefill(int to, double amount) throws Account.NotEnoughMoneyException{
        Account.findAccount(to).modifyBalance(amount);
    }
    public static void MakeTransaction(int cid, Date time, String type, double amount, int from, int to) throws Exception {
        MakeTransaction(cid, DbHelper.getYear(time), DbHelper.getMonth(time), DbHelper.getDay(time), type, amount, from, to);
    }
    public static void MakeTransaction(int cid, int year, int month, int day, String type, double amount, int from, int to) throws Exception {
        switch (type){
            case Transaction.DEPOSIT:
                Transaction.Deposit(to, amount);
                break;
            case Transaction.TOP_UP:
                if (from == 0) Transaction.TopUp(Account.getPocketLinkedAccount(to), to, amount);
                else Transaction.TopUp(from, to, amount);
                break;
            case Transaction.WITHDRAW:
                Transaction.Withdraw(from, amount);
                break;
            case Transaction.PURCHASE:
                Transaction.Purchase(from, amount);
                break;
            case Transaction.TRANSFER:
                Transaction.Transfer(from, to, amount);
                break;
            case Transaction.COLLECT:
                if (to == 0) Transaction.TopUp(from, Account.getPocketLinkedAccount(from), amount);
                else Transaction.Collect(from, to, amount);
                break;
            case Transaction.WIRE:
                Transaction.Wire(from, to, amount);
                break;
            case Transaction.PAY_FRIEND:
                Transaction.PayFriend(from, to, amount);
                break;
            case Transaction.QUICK_CASH:
                Transaction.QuickCash(from, amount);
                break;
            case Transaction.QUICK_REFILL:
                Transaction.QuickRefill(to, amount);
                break;
        }
        DbHelper.run(InsertQuery(cid, year, month, day, type, amount, from, to));
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