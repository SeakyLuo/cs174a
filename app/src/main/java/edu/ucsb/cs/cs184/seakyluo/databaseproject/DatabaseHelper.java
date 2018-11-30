package edu.ucsb.cs.cs184.seakyluo.databaseproject;
import android.util.Log;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {
    public static Customer user;
    public static Date time;
    private static int acount, ccount;

    public static final String TIME = "Time", COUNTER = "Counter", ACOUNT = "AccountCounter", CCOUNT = "CustomerCounter";
    public static final String CREATE_TABLE_TIME = "CREATE TABLE " + TIME + "(time DATE PRIMARY KEY)";
    public static final String CREATE_TABLE_COUNTER = "CREATE TABLE " + COUNTER + "(" + ACOUNT + " INTEGER" + ", " + CCOUNT + " INTEGER" + ")";
    private static final String GET_TIME = "SELECT t.time FROM " + TIME + " t";
    private static final String GET_ACOUNT = "SELECT c." + ACOUNT + " FROM " + COUNTER + " c";
    private static final String GET_CCOUNT = "SELECT c." + CCOUNT + " FROM " + COUNTER + " c";
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@cloud-34-133.eci.ucsb.edu:1521:XE";

    //  Database credentials
    private static final String USERNAME = "haitianluo";
    private static final String PASSWORD = "4608659";

    public static void init(){
        DBInit.Init();
        get(GET_TIME, TIME);
        get(GET_ACOUNT, ACOUNT);
        get(GET_CCOUNT, CCOUNT);
        DBInit.Verification();
    }

    private static ArrayList Get(Connection connection, String sql, String table_name){
        ArrayList<Object> objects = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                switch (table_name){
                    case Customer.TABLE_NAME:
                        objects.add(new Customer(rs.getInt(Customer.ID),
                                rs.getString(Customer.NAME),
                                rs.getString(Customer.ADDRESS),
                                rs.getString(Customer.PIN)));
                        break;
                    case Account.TABLE_NAME:
                        objects.add(new Account(rs.getInt(Account.ID),
                                rs.getString(Account.BANK_NAME),
                                rs.getString(Account.TYPE)));
                        break;
                    case Transaction.TABLE_NAME:
                        objects.add(new Transaction(rs.getInt(Transaction.CID),
                                rs.getDate(Transaction.TIME),
                                rs.getString(Transaction.TYPE),
                                rs.getDouble(Transaction.AMOUNT),
                                rs.getInt(Transaction.FROM),
                                rs.getInt(Transaction.TO)));
                        break;
                    case Owns.TABLE_NAME:
                        objects.add(new Owns(rs.getInt(Owns.CID),
                                rs.getInt(Owns.AID),
                                rs.getInt(Owns.ISPRIMARY)));
                        break;
                    case TIME:
                        time = rs.getDate("time");
                        break;
                    case ACOUNT:
                        acount = rs.getInt(ACOUNT);
                        break;
                    case CCOUNT:
                        ccount = rs.getInt(CCOUNT);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return objects;
        }
    }

    public static ArrayList get(final String sql, final String table_name) {
        final ArrayList[] a = new ArrayList[1];
        connect(new OnConnectionBuiltListener() {
            @Override
            public void OnBuilt(Connection connection) {
                a[0] = Get(connection, sql, table_name);
            }
        });
        return a[0];
    }

    private static void Run(Connection connection, String sql){
        Log.d("fuck", sql);
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void connect(final OnConnectionBuiltListener listener){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName(JDBC_DRIVER);

                    //STEP 3: Open a connection
                    Log.d("fuck","Connecting...");
                    Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    Log.d("fuck","Connected!");
                    listener.OnBuilt(connection);
                    connection.close();
                    Log.d("fuck", "Connection Closed");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void run(final String sql){
        connect(new OnConnectionBuiltListener() {
            @Override
            public void OnBuilt(Connection connection) {
                Run(connection, sql);
            }
        });
    }

    public static void run(final ArrayList<String> queries){
        connect(new OnConnectionBuiltListener() {
            @Override
            public void OnBuilt(Connection connection) {
                for (String sql: queries)
                    Run(connection, sql);
            }
        });
    }

    public static void setTime(int year, int month, int day){
        run("UPDATE " + TIME + " t SET t.time=" + TimeQuery(year, month, day));
        time = new Date(year, month, day);
    }
    public static void insertTime(int year, int month, int day){
        run("INSERT INTO " + TIME + "(time) VALUES (" + TimeQuery(year, month, day) + ")");
        time = new Date(year, month, day);
    }
    public static String TimeQuery(Date date){
        return TimeQuery(date.getYear(), date.getMonth(), date.getDay());
    }
    public static String TimeQuery(int year, int month, int day){
        return "TO_DATE('" + year + ((month < 10) ? "0": "") + month + ((day < 10) ? "0": "") + day + "', 'YYYYMMDD')";
    }
    public static void updateCounter(String variable){
        if (variable.equals(ACOUNT) || variable.equals(CCOUNT))
            run("UPDATE " + COUNTER + " c SET c." + variable + "=c." + variable + "+1");
    }
    public static int getAcount() { return acount; }
    public static int getCcount() { return ccount; }
    public static void insertCounter(){
        run("INSERT INTO " + COUNTER + "(" + ACOUNT + ", " + CCOUNT + ") VALUES (10000, 10000)");
    }

    public interface OnConnectionBuiltListener {
        public void OnBuilt(Connection connection);
    }
}
