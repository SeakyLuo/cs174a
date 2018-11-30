package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Account implements Serializable {
    public static final String TABLE_NAME = "Account", ID = "aid", BANK_NAME = "bank_name", BALANCE = "balance", INTEREST = "interest", TYPE = "type", QUICK_AMOUNT = "quick_amount";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" + ID + " INTEGER NOT NULL, " +
                                                                                    BANK_NAME + " CHAR(40), " +
                                                                                    TYPE + " CHAR(30) NOT NULL, " +
                                                                                    BALANCE + " REAL, " +
                                                                                    INTEREST + " REAL, " +
                                                                                    "PRIMARY KEY(" + ID + "))";
    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME + " CASCADE Constraints";
    public static final String CHECKING = "Checking", STUDENT_CHECKING = "Student-Checking", INTEREST_CHECKING = "Interest-Checking", SAVINGS = "Savings", POCKET = "Pocket";

    private int aid;
    private String bank_name, type;
    private Double balance, interest;
    public Account(int aid, String bank_name, String type){
        this.aid = aid;
        this.bank_name = bank_name.trim();
        this.type = type.trim();
        this.balance = 0d;
        switch (type){
            case INTEREST_CHECKING:
                this.interest = 0.055;
                break;
            case STUDENT_CHECKING:
                this.interest = 0d;
                break;
            case SAVINGS:
                this.interest = 0.075;
                break;
            case POCKET:
                this.interest = 0d;
                break;
        }
    }
    public Account(int aid, String bank_name, String type, double balance, double interest){
        this.aid = aid;
        this.bank_name = bank_name.trim();
        this.type = type.trim();
        this.balance = balance;
        this.interest = interest;
    }

    public int getId() { return aid; }
    public String getType(){
       if (isPocket()) return POCKET;
       return type;
    }
    public boolean isType(String type){
        return getType().contains(type);
    }
    public String getBankName() { return bank_name; }
    public double getBalance() { return balance; }
    public double getInterest() { return interest; }
    public double getMonthlyInterest() { return interest / 12; }
    public void setBalance(double balance) {
        this.balance = balance;
        DatabaseHelper.run("UPDATE " + TABLE_NAME + " a SET a." + BALANCE + "=" + balance + " WHERE a." + ID + "=" + aid);
    }
    public void modifyBalance(double delta) throws NotEnoughMoneyException {
        if (this.balance + delta < 0){
            throw new NotEnoughMoneyException();
        }
        this.balance += delta;
        DatabaseHelper.run("UPDATE " + TABLE_NAME + " a SET a." + BALANCE + "=" + balance  + " WHERE a." + ID + "=" + aid);
    }
    public void setInterest(double interest) {
        this.interest = interest;
        DatabaseHelper.run("UPDATE " + TABLE_NAME + " a SET a." + INTEREST + "=" + interest + " WHERE a." + ID + "=" + aid);
    }
    public ArrayList<Owns> findOwners(){
        ArrayList<Owns> owners = new ArrayList<>();
        DatabaseHelper.get(Owns.getQuery() + " WHERE o.aid=" + aid, Owns.TABLE_NAME);
        return owners;
    }
    public static ArrayList<Transaction> findTransactions(int aid){
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction: (ArrayList<Transaction>) DatabaseHelper.get(Transaction.getQuery(), Transaction.TABLE_NAME))
            if (transaction.getFrom() == aid || transaction.getTo() == aid)
                transactions.add(transaction);
        return transactions;
    }
    public String insertQuery(){
        return InsertQuery(aid, bank_name, type, balance, interest);
    }
    public String deleteQuery(){
        return "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "=" + aid;
    }
    public static String InsertQuery(int aid, String bank_name, String type, double balance, double interest){
        return "INSERT INTO " + TABLE_NAME +" (" + ID + ", " + BANK_NAME + ", " + TYPE + ", " + BALANCE + ", " + INTEREST + ") " +
                "VALUES (" + aid + ", '" + bank_name + "', '" + type + "', " + balance + ", " + interest + ")";
    }
    public static String getQuery(){
        return "SELECT * FROM " + TABLE_NAME + " a";
    }

    public boolean isClosed() {
        return balance <= 0.01 && findTransactions(aid).size() > 0;
    }
    public boolean isPocket() {
        try{
            Integer.parseInt(type);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    public static int getPocketLinkedAccount(int aid){
        Account account = Account.findAccount(aid);
        if (account.isType(POCKET)) return Integer.parseInt(account.getType());
        else return 0;
    }

    public static Account findAccount(int aid){
        return ((ArrayList<Account>) DatabaseHelper.get(getQuery() + " WHERE a." + ID + "=" + aid, TABLE_NAME)).get(0);
    }
    public static ArrayList<Account> findAccounts(int userid){
        ArrayList<Account> accounts = new ArrayList<>();
        for (Owns owns: (ArrayList<Owns>) DatabaseHelper.get(Owns.getQuery() + " WHERE o." + Owns.CID + "=" + userid, Owns.TABLE_NAME))
            accounts.add(findAccount(owns.getAid()));
        return accounts;
    }
    public static ArrayList<Account> findAccountsWithType(int userid, String type, boolean including_closed){
        ArrayList<Account> accounts = new ArrayList<>();
        for (Account account: findAccounts(userid))
            if (account.isType(type) && (including_closed || !account.isClosed()))
                accounts.add(account);
        return accounts;
    }
    public static ArrayList<Account> findAccountsWithoutType(int userid, String type, boolean including_closed){
        ArrayList<Account> accounts = new ArrayList<>();
        for (Account account: findAccounts(userid))
            if (!account.isType(type) && (including_closed || !account.isClosed()))
                accounts.add(account);
        return accounts;
    }
    public static ArrayList<Account> findClosedAccounts(){
        ArrayList<Account> accounts = new ArrayList<>();
        for (Account account: (ArrayList<Account>) DatabaseHelper.get(Account.getQuery(), TABLE_NAME))
            if(account.isClosed())
                accounts.add(account);
        return accounts;
    }

    @Override
    public String toString(){
        return getType() + ": " + aid + "\nBank Name: " + bank_name + "\nBalance: " + balance + "\n" + (isClosed() ? "Closed" : "Open") ;
    }

    public static class NotEnoughMoneyException extends Exception{
        public NotEnoughMoneyException(){
            super("Not Enough Money!");
        }
    }
}
