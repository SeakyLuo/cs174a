package edu.ucsb.cs.cs184.seakyluo.databaseproject;

public class Customer{
    public static final String CUSTOMER = "Customer", ID = "id", NAME = "name", ADDRESS = "address", PIN = "pin";
    public static final String CREATE_TABLE = "CREATE TABLE " + CUSTOMER +"(" + ID + " INTEGER NOT NULL, " +
                                                                                    NAME + " CHAR(30), " +
                                                                                    ADDRESS +" CHAR(50), " +
                                                                                    PIN  + " CHAR(4) NOT NULL UNIQUE, " +
                                                                                    " PRIMARY KEY(" + ID +"))";

    private int id;
    private String name, address, pin;

    public Customer(int id, String name, String address, String pin){
        this.id = id;
        this.name = name;
        this.address = address;
        this.pin = pin;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPin() { return pin; }
    public String insertQuery(){
        return "INSERT INTO Accounts (" + ID + ", " + NAME + ", " + ADDRESS + ", " + PIN + ") " +
                "VALUES (" + id + ", " + name + ", " + address + ", " + pin + ")";
    }
    public static String getQuery(){
        return "SELECT c." + ID + ", o." + NAME + ", o." + ADDRESS + ", o." + PIN + " " +
                "FROM " + CUSTOMER + "c";
    }

    public static boolean VerifyPin(String PIN){
        return false;
    }

    public void SetPin(String OldPIN, String NewPin){
        if (OldPIN.equals(pin)){

        }else{

        }
    }
}
