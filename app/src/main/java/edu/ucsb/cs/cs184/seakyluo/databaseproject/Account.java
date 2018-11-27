package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Account implements Serializable {
    public static final String TABLE_NAME = "Account", ID = "aid", BANK_NAME = "bank_name", BALANCE = "balance", INTEREST = "interest", TYPE = "type";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"(" + ID + " INTEGER NOT NULL, " +
                                                                                    BANK_NAME + " TEXT, " +
                                                                                    TYPE + " TEXT NOT NULL, " +
                                                                                    BALANCE + " REAL, " +
                                                                                    INTEREST + " REAL, " +
                                                                                    "PRIMARY KEY(" + ID + "))";
    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
    public static final String STUDENT_CHECKING = "Student-Checking", INTEREST_CHECKING = "Interest-Checking", SAVINGS = "Savings", POCKET = "Pocket";
    private static final String[] VT_CHECKING = {Transaction.DEPOSIT, Transaction.WITHDRAW, Transaction.TRANSFER, Transaction.WIRE, Transaction.WRITE_CHECK, Transaction.ACCRUE_INTEREST},
            VT_SAVING = {Transaction.DEPOSIT, Transaction.WITHDRAW, Transaction.TRANSFER, Transaction.WIRE, Transaction.ACCRUE_INTEREST},
            VT_POCKET = {Transaction.TOP_UP, Transaction.PURCHASE, Transaction.COLLECT, Transaction.PAY_FRIEND};
    private int aid;
    private String bank_name, type;
    private Double balance, interest;
    public Account(int aid, String bank_name, String type){
        this.aid = aid;
        this.bank_name = bank_name;
        this.type = type;
        this.balance = 0d;
        switch (type){
            case INTEREST_CHECKING:
                interest = 0.055;
            case STUDENT_CHECKING:
                interest = 0d;
            case SAVINGS:
                interest = 0.075;
            case POCKET:
                interest = 0d;
        }
    }

    public int getId() { return aid; }
    public String getType(){
       if (isPocket()) return POCKET;
       return type;
    }
    public String getBankName() { return bank_name; }
    public double getBalance() { return balance; }
    public double getInterest() { return interest; }
    public void setBalance(double balance) {
        this.balance = balance;
        // updates db
    }
    public void modifyBalance(double delta) {
        this.balance += delta;
        // updates db
    }
    public void setInterest(double interest) {
        this.interest = interest;
        // updates db
    }
    public String insertQuery(){
        return "INSERT INTO " + TABLE_NAME +" (" + ID + ", " + BANK_NAME + ", " + TYPE + ", " + BALANCE + ", " + INTEREST + ") " +
                "VALUES (" + aid + ", '" + bank_name + "', '" + type + "', " + balance + ", " + interest + ")";
    }
    public static String InsertQuery(int aid, String bank_name, String type, double balance, double interest){
        return "INSERT INTO " + TABLE_NAME +" (" + ID + ", " + BANK_NAME + ", " + TYPE + ", " + BALANCE + ", " + INTEREST + ") " +
                "VALUES (" + aid + ", '" + bank_name + "', '" + type + "', " + balance + ", " + interest + ")";
    }
    public static String getQuery(){
        return "SELECT a." + ID + ", a." + BANK_NAME + ", a." + TYPE + ", a." + BALANCE + ", a." + INTEREST + " " +
                "FROM " + TABLE_NAME + " a";
    }
    public static Account findAccount(int aid){
        return ((ArrayList<Account>) DatabaseHelper.get(getQuery() + " WHERE a." + ID + "=" + aid, TABLE_NAME)).get(0);
    }
    public static ArrayList<Account> findUserAccounts(){
        ArrayList<Account> accounts = new ArrayList<>();
        if (DatabaseHelper.user == null) return accounts;
        for(Owns owns: (ArrayList<Owns>) DatabaseHelper.get(Owns.getQuery() + " WHERE o." + Owns.CID + "=" + DatabaseHelper.user.getId(), Owns.TABLE_NAME)){
            accounts.add(findAccount(owns.getAid()));
        }
        return accounts;
    }
    public static ArrayList<Account> findUserNonPocketAccounts(){
        ArrayList<Account> accounts = new ArrayList<>();
        for(Account account: findUserAccounts())
            if (!account.isPocket())
                accounts.add(account);
        return accounts;
    }

    public boolean isClosed() { return balance <= 0.01; }
    public boolean isPocket() {
        try{
            Integer.parseInt(type);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    public int getPocketLinkedAccount(){
        try{
            return Integer.parseInt(type);
        }catch (NumberFormatException e){
            return 0;
        }
    }

    public boolean ValidTransactions(Transaction transaction){
        switch (type){
            case STUDENT_CHECKING:
                return Arrays.asList(VT_CHECKING).contains(transaction.getType());
            case INTEREST_CHECKING:
                return Arrays.asList(VT_CHECKING).contains(transaction.getType());
            case SAVINGS:
                return Arrays.asList(VT_SAVING).contains(transaction.getType());
            case POCKET:
                return Arrays.asList(VT_POCKET).contains(transaction.getType());
            default:
                return false;
        }
    }

    @Override
    public String toString(){
        return getType() + ": " + aid + "\nBank Name: " + bank_name + "\nBalance: " + balance;
    }
}
