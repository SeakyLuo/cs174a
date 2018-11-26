package edu.ucsb.cs.cs184.seakyluo.databaseproject;
import android.util.Log;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {
    public static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String DB_URL = "jdbc:oracle:thin:@cloud-34-133.eci.ucsb.edu:1521:XE";

    //  Database credentials
    public static final String USERNAME = "haitianluo";
    public static final String PASSWORD = "4608659";

    public static void run() {
        Connection connection = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            Log.d("fuck","Connecting to a selected database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Log.d("fuck","Connected database successfully...");

            //STEP 4: Execute a query
            Log.d("fuck","Creating statement...");
            stmt = connection.createStatement();

            String sql = "SELECT cid, cname, city, discount FROM cs174.Customers";
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set

            rs.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    connection.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public interface QueryExecutor{
        void exec();
    }
}
