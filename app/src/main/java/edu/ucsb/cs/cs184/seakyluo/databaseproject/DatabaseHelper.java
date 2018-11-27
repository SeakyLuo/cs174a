package edu.ucsb.cs.cs184.seakyluo.databaseproject;
import android.util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class DatabaseHelper {
    public static Customer user;
    public static Date time;

    public static final String TIME = "Time";
    public static final String CREATE_TABLE_TIME = "CREATE TABLE " + TIME + "(time DATE)";
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@cloud-34-133.eci.ucsb.edu:1521:XE";

    //  Database credentials
    private static final String USERNAME = "haitianluo";
    private static final String PASSWORD = "4608659";

    private static Connection connection;
    private static boolean initFinished = false;

    public static void Init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                init();
                initFinished = true;
            }
        }).start();
    }
    private static void init(){
        try {
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            Log.d("fuck","Connecting...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Log.d("fuck","Connected!");
//            DBInit.Init();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Object> get(String sql, String type) {
        ArrayList<Object> objects = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                switch (type){
                    case Customer.CUSTOMER:
                        objects.add(new Customer(rs.getInt(Customer.ID),
                                                rs.getString(Customer.NAME),
                                                rs.getString(Customer.ADDRESS),
                                                rs.getString(Customer.PIN)));
                    case Account.ACCOUNT:
                        objects.add(new Account(rs.getInt(Account.ID),
                                                rs.getString(Account.BANK_NAME),
                                                rs.getString(Account.TYPE)));
                    case Transaction.TRANSACTION:
                        objects.add(new Transaction(rs.getInt(Transaction.CID),
                                                    rs.getDate(Transaction.TIME),
                                                    rs.getString(Transaction.TYPE),
                                                    rs.getDouble(Transaction.AMOUNT),
                                                    rs.getInt(Transaction.FROM),
                                                    rs.getInt(Transaction.TO)));
                    case Owns.OWNS:
                        objects.add(new Owns(rs.getInt(Owns.CID),
                                            rs.getInt(Owns.AID),
                                            rs.getBoolean(Owns.ISPRIMARY)));
                    case TIME:
                        time = rs.getDate("time");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return objects;
        }
    }

    public static void run(String sql){
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isInitFinished() { return initFinished; }
    public static void setTime(int year, int month, int day){
        run("UPDATE Time t SET t.time = TO_DATE('" + year + "/" + month + "/" + day + "', 'YYYY/MM/DD')");
        time = new Date(year, month, day);
    }

    public static void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
