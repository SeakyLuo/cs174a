package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.util.Arrays;

public class Account {
    public static final String ACCOUNT = "Account", ID = "aid", BANK_NAME = "bank_name", BALANCE = "balance", INTEREST = "interest", TYPE = "type";
    public static final String CREATE_TABLE = "CREATE TABLE " + ACCOUNT +"(" + ID + " INTEGER NOT NULL, " +
                                                                                    BANK_NAME + " CHAR(20), " +
                                                                                    BALANCE + " REAL, " +
                                                                                    INTEREST + " REAL, " +
                                                                                    TYPE + " CHAR(20) NOT NULL, " +
                                                                                    "PRIMARY KEY(" + ID + "))";
    public static final String STUDENT_CHECKING = "student_checking", INTEREST_CHECKING = "interest_checking", SAVINGS = "savings", POCKET = "pocket";
    private static final String[] VT_CHECKING = {Transaction.DEPOSIT, Transaction.WITHDRAWAL, Transaction.TRANSFER, Transaction.WIRE, Transaction.WRITE_CHECK, Transaction.ACCRUE_INTEREST},
            VT_SAVING = {Transaction.DEPOSIT, Transaction.WITHDRAWAL, Transaction.TRANSFER, Transaction.WIRE, Transaction.ACCRUE_INTEREST},
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
        return "INSERT INTO Accounts (" + ID + ", " + BANK_NAME + ", " + BALANCE + ", " + INTEREST + ", " + TYPE + ") " +
                "VALUES (" + aid + ", " + bank_name + ", " + balance + ", " + interest + ", " + type + ")";
    }
    public static String getQuery(){
        return "SELECT a." + ID + ", a." + BANK_NAME + ", a." + BALANCE + ", a." + INTEREST + ", a." + TYPE + " " +
                "FROM " + ACCOUNT + "a";
    }

    public boolean isClosed() { return balance <= 0.01; }

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
}
