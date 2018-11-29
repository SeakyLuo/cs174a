package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

public class DBInit {
    public static void Init(){
        // Create Tables
//        DatabaseHelper.run(DatabaseHelper.CREATE_TABLE_TIME);
//        DatabaseHelper.run(DatabaseHelper.CREATE_TABLE_COUNTER);
//        DatabaseHelper.run(Customer.CREATE_TABLE);
//        DatabaseHelper.run(Account.CREATE_TABLE);
        DatabaseHelper.run(Owns.CREATE_TABLE);
//        DatabaseHelper.run(Transaction.CREATE_TABLE);
//         Insert Time
//        DatabaseHelper.insertTime(2018,11,28);
//         Insert Counter
//        DatabaseHelper.insertCounter();
        // Insert Customers
//        DatabaseHelper.run(Customer.InsertQuery(361721022, "Alfred Hitchcock", "6667 El Colegio #40", "1234"));
//        DatabaseHelper.run(Customer.InsertQuery(231403227, "Billy Clinton", "5777 Hollister", "1468"));
//        DatabaseHelper.run(Customer.InsertQuery(412231856, "Cindy Laugher", "7000 Hollister", "3764"));
//        DatabaseHelper.run(Customer.InsertQuery(207843218, "David Copperfill", "1357 State St", "8582"));
//        DatabaseHelper.run(Customer.InsertQuery(122219876, "Elizabeth Sailor", "4321 State St", "3856"));
//        DatabaseHelper.run(Customer.InsertQuery(401605312, "Fatal Castro", "3756 La Cumbre Plaza", "8193"));
//        DatabaseHelper.run(Customer.InsertQuery(201674933, "George Brush", "5346 Foothill Av", "9824"));
//        DatabaseHelper.run(Customer.InsertQuery(212431965, "Hurryson Ford", "678 State St", "3532"));
//        DatabaseHelper.run(Customer.InsertQuery(322175130, "Ivan Lendme", "1235 Johnson Dr", "8471"));
//        DatabaseHelper.run(Customer.InsertQuery(344151573, "Joe Pepsi", "3210 State St", "3692"));
//        DatabaseHelper.run(Customer.InsertQuery(209378521, "Kelvin Coster", "Santa Cruz #3579", "4659"));
//        DatabaseHelper.run(Customer.InsertQuery(212116070, "Li Kung", "2 People''s Rd Beijing", "9173"));
//        DatabaseHelper.run(Customer.InsertQuery(188212217, "Magic Jordon", "3852 Court Rd", "7351"));
//        DatabaseHelper.run(Customer.InsertQuery(203491209, "Nam-hoi Chung", "1997 People''s St HK", "5340"));
//        DatabaseHelper.run(Customer.InsertQuery(210389768, "Olive Stoner", "6689 El Colegio #151", "8452"));
//        DatabaseHelper.run(Customer.InsertQuery(400651982, "Pit Wilson", "911 State St", "1821"));
        // Insert Accounts
//        DatabaseHelper.run(new Account(17431, "San Fransisco", "Student-Checking").insertQuery());
//        DatabaseHelper.run(new Account(54321, "Los Angeles", "Student-Checking").insertQuery());
//        DatabaseHelper.run(new Account(12121, "Goleta", "Student-Checking").insertQuery());
//        DatabaseHelper.run(new Account(41725, "Los Angeles", "Interest-Checking").insertQuery());
//        DatabaseHelper.run(new Account(76543 , "Santa Barbara", "Interest-Checking").insertQuery());
//        DatabaseHelper.run(new Account(93156, "Goleta", "Interest-Checking").insertQuery());
//        DatabaseHelper.run(new Account(43942, "Santa Barbara", "Savings").insertQuery());
//        DatabaseHelper.run(new Account(29107, "Los Angeles", "Savings").insertQuery());
//        DatabaseHelper.run(new Account(19023, "San Fransisco", "Savings").insertQuery());
//        DatabaseHelper.run(new Account(32156, "Goleta", "Savings").insertQuery());
//        DatabaseHelper.run(new Account(53027, "Goleta", "Pocket").insertQuery());
//        DatabaseHelper.run(new Account(43947, "Isla Vista", "Pocket").insertQuery());
//        DatabaseHelper.run(new Account(60413, "Santa Cruz", "Pocket").insertQuery());
//        DatabaseHelper.run(new Account(67521, "Santa Barbara", "Pocket").insertQuery());
        // Insert Owns
        DatabaseHelper.run(Owns.InsertQuery(17431, 344151573, 1));
        DatabaseHelper.run(Owns.InsertQuery(17431, 412231856, 0));
        DatabaseHelper.run(Owns.InsertQuery(17431, 322175130, 0));
        DatabaseHelper.run(Owns.InsertQuery(54321, 212431965, 1));
        DatabaseHelper.run(Owns.InsertQuery(54321, 412231856, 0));
        DatabaseHelper.run(Owns.InsertQuery(54321, 122219876, 0));
        DatabaseHelper.run(Owns.InsertQuery(54321, 203491209, 0));
        DatabaseHelper.run(Owns.InsertQuery(12121, 207843218, 1));
        DatabaseHelper.run(Owns.InsertQuery(41725, 201674933, 1));
        DatabaseHelper.run(Owns.InsertQuery(41725, 401605312, 0));
        DatabaseHelper.run(Owns.InsertQuery(41725, 231403227, 0));
        DatabaseHelper.run(Owns.InsertQuery(76543 , 212116070, 1));
        DatabaseHelper.run(Owns.InsertQuery(76543 , 188212217, 0));
        DatabaseHelper.run(Owns.InsertQuery(93156, 209378521, 1));
        DatabaseHelper.run(Owns.InsertQuery(93156, 188212217, 0));
        DatabaseHelper.run(Owns.InsertQuery(93156, 210389768, 0));
        DatabaseHelper.run(Owns.InsertQuery(93156, 122219876, 0));
        DatabaseHelper.run(Owns.InsertQuery(93156, 203491209, 0));
        DatabaseHelper.run(Owns.InsertQuery(43942, 361721022, 1));
        DatabaseHelper.run(Owns.InsertQuery(43942, 400651982, 0));
        DatabaseHelper.run(Owns.InsertQuery(43942, 212431965, 0));
        DatabaseHelper.run(Owns.InsertQuery(43942, 322175130, 0));
        DatabaseHelper.run(Owns.InsertQuery(29107, 209378521, 1));
        DatabaseHelper.run(Owns.InsertQuery(29107, 212116070, 0));
        DatabaseHelper.run(Owns.InsertQuery(29107, 210389768, 0));
        DatabaseHelper.run(Owns.InsertQuery(19023, 412231856, 1));
        DatabaseHelper.run(Owns.InsertQuery(19023, 201674933, 0));
        DatabaseHelper.run(Owns.InsertQuery(19023, 401605312, 0));
        DatabaseHelper.run(Owns.InsertQuery(32156, 188212217, 1));
        DatabaseHelper.run(Owns.InsertQuery(32156, 207843218, 0));
        DatabaseHelper.run(Owns.InsertQuery(32156, 122219876, 0));
        DatabaseHelper.run(Owns.InsertQuery(32156, 344151573, 0));
        DatabaseHelper.run(Owns.InsertQuery(32156, 203491209, 0));
        DatabaseHelper.run(Owns.InsertQuery(32156, 210389768, 0));
        DatabaseHelper.run(Owns.InsertQuery(53027, 207843218, 1));
        DatabaseHelper.run(Owns.InsertQuery(43947, 212116070, 1));
        DatabaseHelper.run(Owns.InsertQuery(43947, 210389768, 0));
        DatabaseHelper.run(Owns.InsertQuery(60413, 361721022, 1));
        DatabaseHelper.run(Owns.InsertQuery(60413, 400651982, 0));
        DatabaseHelper.run(Owns.InsertQuery(60413, 122219876, 0));
        DatabaseHelper.run(Owns.InsertQuery(60413, 231403227, 0));
        DatabaseHelper.run(Owns.InsertQuery(67521, 209378521, 1));
        DatabaseHelper.run(Owns.InsertQuery(67521, 401605312, 0));
        DatabaseHelper.run(Owns.InsertQuery(67521, 212431965, 0));
        // Insert Transactions on Account Creation
//        DatabaseHelper.run(Transaction.InsertQuery(322175130, 2011, 3, 2, Transaction.DEPOSIT, 200, 0, 17431));
//        DatabaseHelper.run(Transaction.InsertQuery(212431965, 2011, 3, 3, Transaction.DEPOSIT, 21000, 0, 54321));
//        DatabaseHelper.run(Transaction.InsertQuery(207843218, 2011, 3, 3, Transaction.DEPOSIT, 1200, 0, 12121));
//        DatabaseHelper.run(Transaction.InsertQuery(201674933, 2011, 3, 3, Transaction.DEPOSIT, 15000, 0, 41725));
//        DatabaseHelper.run(Transaction.InsertQuery(122219876, 2011, 3, 3, Transaction.DEPOSIT, 2000000, 0, 93156));
//        DatabaseHelper.run(Transaction.InsertQuery(207843218, 2011, 3, 4, Transaction.TOP_UP, 50, 12121, 53027));
//        DatabaseHelper.run(Transaction.InsertQuery(212431965, 2011, 3, 4, Transaction.DEPOSIT, 1289, 0, 43942));
//        DatabaseHelper.run(Transaction.InsertQuery(209378521, 2011, 3, 4, Transaction.DEPOSIT, 34000, 0, 29107));
//        DatabaseHelper.run(Transaction.InsertQuery(412231856, 2011, 3, 5, Transaction.DEPOSIT, 2300, 0, 19023));
//        DatabaseHelper.run(Transaction.InsertQuery(400651982, 2011, 3, 5, Transaction.TOP_UP, 20, 43942, 60413));
//        DatabaseHelper.run(Transaction.InsertQuery(344151573, 2011, 3, 5, Transaction.DEPOSIT, 1000, 0, 32156));
//        DatabaseHelper.run(Transaction.InsertQuery(188212217, 2011, 3, 5, Transaction.DEPOSIT, 8456, 0, 76543));
//        DatabaseHelper.run(Transaction.InsertQuery(212116070, 2011, 3, 5, Transaction.TOP_UP, 30, 29107, 43947));
//        DatabaseHelper.run(Transaction.InsertQuery(401605312, 2011, 3, 6, Transaction.TOP_UP, 100, 19023, 67521));
//        // Insert Other Transactions
//        DatabaseHelper.run(Transaction.InsertQuery(344151573, 2011, 3, 2, Transaction.DEPOSIT, 8800, 0, 17431));
//        DatabaseHelper.run(Transaction.InsertQuery(122219876, 2011, 3, 3, Transaction.WITHDRAW, 3000, 54321, 0));
//        DatabaseHelper.run(Transaction.InsertQuery(212116070, 2011, 3, 5, Transaction.WITHDRAW, 2000, 76543, 0));
//        DatabaseHelper.run(Transaction.InsertQuery(207843218, 2011, 3, 5, Transaction.PURCHASE, 5, 53027, 0));
//        DatabaseHelper.run(Transaction.InsertQuery(188212217, 2011, 3, 6, Transaction.WITHDRAW, 1000000, 93156, 0));
//        DatabaseHelper.run(Transaction.InsertQuery(209378521, 2011, 3, 6, Transaction.WRITE_CHECK, 950000, 93156, 0));
//        DatabaseHelper.run(Transaction.InsertQuery(212116070, 2011, 3, 6, Transaction.WITHDRAW, 4000, 29107, 0));
//        DatabaseHelper.run(Transaction.InsertQuery(210389768, 2011, 3, 6, Transaction.COLLECT, 10, 43947, 29107));
//        DatabaseHelper.run(Transaction.InsertQuery(212116070, 2011, 3, 6, Transaction.TOP_UP, 30, 29107, 43947));
//        DatabaseHelper.run(Transaction.InsertQuery(322175130, 2011, 3, 7, Transaction.TRANSFER, 289, 43942, 17431));
//        DatabaseHelper.run(Transaction.InsertQuery(400651982, 2011, 3, 7, Transaction.WITHDRAW, 289, 43942, 0));
//        DatabaseHelper.run(Transaction.InsertQuery(400651982, 2011, 3, 8, Transaction.PAY_FRIEND, 10, 60413, 67521));
//        DatabaseHelper.run(Transaction.InsertQuery(210389768, 2011, 3, 8, Transaction.DEPOSIT, 50000, 0, 93156));
//        DatabaseHelper.run(Transaction.InsertQuery(207843218, 2011, 3, 8, Transaction.WRITE_CHECK, 200, 12121, 0));
//        DatabaseHelper.run(Transaction.InsertQuery(201674933, 2011, 3, 8, Transaction.TRANSFER, 1000, 41725, 19023));
//        DatabaseHelper.run(Transaction.InsertQuery(401605312, 2011, 3, 9, Transaction.WIRE, 4000, 41725, 32156));
//        DatabaseHelper.run(Transaction.InsertQuery(207843218, 2011, 3, 9, Transaction.PAY_FRIEND, 10, 53027, 60413));
//        DatabaseHelper.run(Transaction.InsertQuery(122219876, 2011, 3, 10, Transaction.PURCHASE, 15, 60413, 0));
//        DatabaseHelper.run(Transaction.InsertQuery(203491209, 2011, 3, 12, Transaction.WITHDRAW, 20000, 93156, 0));
//        DatabaseHelper.run(Transaction.InsertQuery(188212217, 2011, 3, 12, Transaction.WRITE_CHECK, 456, 76543, 0));
//        DatabaseHelper.run(Transaction.InsertQuery(401605312, 2011, 3, 12, Transaction.TOP_UP, 50, 19023, 67521));
//        DatabaseHelper.run(Transaction.InsertQuery(212431965, 2011, 3, 14, Transaction.PAY_FRIEND, 20, 19023, 53027));
//        DatabaseHelper.run(Transaction.InsertQuery(210389768, 2011, 3, 14, Transaction.COLLECT, 15, 43947, 29107));
        // Verify Insertion is Successful
        Verification();
    }

    public static void Verification(){
//        for (Account account: (ArrayList<Account>) DatabaseHelper.get(Account.getQuery(), Account.TABLE_NAME)){
//            Log.d("fuck", account.toString());
//        }
//        for (Customer customer: (ArrayList<Customer>) DatabaseHelper.get(Customer.getQuery(), Customer.TABLE_NAME)){
//            Log.d("fuck", customer.toString());
//        }
        for (Owns owns: (ArrayList<Owns>) DatabaseHelper.get(Owns.getQuery(), Owns.TABLE_NAME)){
            Log.d("fuck", owns.toString());
        }
//        for (Transaction transaction: (ArrayList<Transaction>) DatabaseHelper.get(Transaction.getQuery(), Transaction.TABLE_NAME)){
//            Log.d("fuck", transaction.toString());
//        }
    }
}
