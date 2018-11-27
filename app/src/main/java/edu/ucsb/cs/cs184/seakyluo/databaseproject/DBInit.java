package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import java.sql.Date;

public class DBInit {
    public static void Init(){
        // Create Tables
        DatabaseHelper.run(DatabaseHelper.CREATE_TABLE_TIME);
        DatabaseHelper.run(DatabaseHelper.CREATE_TABLE_COUNTER);
        DatabaseHelper.run(Customer.CREATE_TABLE);
        DatabaseHelper.run(Account.CREATE_TABLE);
        DatabaseHelper.run(Owns.CREATE_TABLE);
        DatabaseHelper.run(Transaction.CREATE_TABLE);
        // Insert Time
        DatabaseHelper.insertTime(2018,11,26);
        // Insert Counter
        DatabaseHelper.insertCounter();
        // Insert Customers
        DatabaseHelper.run(new Customer(361721022, "Alfred Hitchcock", "6667 El Colegio #40", "1234").insertQuery());
        DatabaseHelper.run(new Customer(231403227, "Billy Clinton", "5777 Hollister", "1468").insertQuery());
        DatabaseHelper.run(new Customer(412231856, "Cindy Laugher", "7000 Hollister", "3764").insertQuery());
        DatabaseHelper.run(new Customer(207843218, "David Copperfill", "1357 State St", "8582").insertQuery());
        DatabaseHelper.run(new Customer(122219876, "Elizabeth Sailor", "4321 State St", "3856").insertQuery());
        DatabaseHelper.run(new Customer(401605312, "Fatal Castro", "3756 La Cumbre Plaza", "8193").insertQuery());
        DatabaseHelper.run(new Customer(201674933, "George Brush", "5346 Foothill Av", "9824").insertQuery());
        DatabaseHelper.run(new Customer(212431965, "Hurryson Ford", "678 State St", "3532").insertQuery());
        DatabaseHelper.run(new Customer(322175130, "Ivan Lendme", "1235 Johnson Dr", "8471").insertQuery());
        DatabaseHelper.run(new Customer(344151573, "Joe Pepsi", "3210 State St", "3692").insertQuery());
        DatabaseHelper.run(new Customer(209378521, "Kelvin Coster", "Santa Cruz #3579", "4659").insertQuery());
        DatabaseHelper.run(new Customer(212116070, "Li Kung", "2 People's Rd Beijing", "9173").insertQuery());
        DatabaseHelper.run(new Customer(188212217, "Magic Jordon", "3852 Court Rd", "7351").insertQuery());
        DatabaseHelper.run(new Customer(203491209, "Nam-hoi Chung", "1997 People's St HK", "5340").insertQuery());
        DatabaseHelper.run(new Customer(210389768, "Olive Stoner", "6689 El Colegio #151", "8452").insertQuery());
        DatabaseHelper.run(new Customer(400651982, "Pit Wilson", "911 State St", "1821").insertQuery());
        // Insert Accounts
        DatabaseHelper.run(new Account(17431, "San Fransisco", "Student-Checking").insertQuery());
        DatabaseHelper.run(new Account(54321, "Los Angeles", "Student-Checking").insertQuery());
        DatabaseHelper.run(new Account(12121, "Goleta", "Student-Checking").insertQuery());
        DatabaseHelper.run(new Account(41725, "Los Angeles", "Interest-Checking").insertQuery());
        DatabaseHelper.run(new Account(76543 , "Santa Barbara", "Interest-Checking").insertQuery());
        DatabaseHelper.run(new Account(93156, "Goleta", "Interest-Checking").insertQuery());
        DatabaseHelper.run(new Account(43942, "Santa Barbara", "Savings").insertQuery());
        DatabaseHelper.run(new Account(29107, "Los Angeles", "Savings").insertQuery());
        DatabaseHelper.run(new Account(19023, "San Fransisco", "Savings").insertQuery());
        DatabaseHelper.run(new Account(32156, "Goleta", "Savings").insertQuery());
        DatabaseHelper.run(new Account(53027, "Goleta", "Pocket").insertQuery());
        DatabaseHelper.run(new Account(43947, "Isla Vista", "Pocket").insertQuery());
        DatabaseHelper.run(new Account(60413, "Santa Cruz", "Pocket").insertQuery());
        DatabaseHelper.run(new Account(67521, "Santa Barbara", "Pocket").insertQuery());
        // Insert Owns
        DatabaseHelper.run(new Owns(17431, 344151573, true).insertQuery());
        DatabaseHelper.run(new Owns(17431, 412231856, false).insertQuery());
        DatabaseHelper.run(new Owns(17431, 322175130, false).insertQuery());
        DatabaseHelper.run(new Owns(54321, 212431965, true).insertQuery());
        DatabaseHelper.run(new Owns(54321, 412231856, false).insertQuery());
        DatabaseHelper.run(new Owns(54321, 122219876, false).insertQuery());
        DatabaseHelper.run(new Owns(54321, 203491209, false).insertQuery());
        DatabaseHelper.run(new Owns(12121, 207843218, true).insertQuery());
        DatabaseHelper.run(new Owns(41725, 201674933, true).insertQuery());
        DatabaseHelper.run(new Owns(41725, 401605312, false).insertQuery());
        DatabaseHelper.run(new Owns(41725, 231403227, false).insertQuery());
        DatabaseHelper.run(new Owns(76543 , 212116070, true).insertQuery());
        DatabaseHelper.run(new Owns(76543 , 188212217, false).insertQuery());
        DatabaseHelper.run(new Owns(93156, 209378521, true).insertQuery());
        DatabaseHelper.run(new Owns(93156, 188212217, false).insertQuery());
        DatabaseHelper.run(new Owns(93156, 210389768, false).insertQuery());
        DatabaseHelper.run(new Owns(93156, 122219876, false).insertQuery());
        DatabaseHelper.run(new Owns(93156, 203491209, false).insertQuery());
        DatabaseHelper.run(new Owns(43942, 361721022, true).insertQuery());
        DatabaseHelper.run(new Owns(43942, 400651982, false).insertQuery());
        DatabaseHelper.run(new Owns(43942, 212431965, false).insertQuery());
        DatabaseHelper.run(new Owns(43942, 322175130, false).insertQuery());
        DatabaseHelper.run(new Owns(29107, 209378521, true).insertQuery());
        DatabaseHelper.run(new Owns(29107, 212116070, false).insertQuery());
        DatabaseHelper.run(new Owns(29107, 210389768, false).insertQuery());
        DatabaseHelper.run(new Owns(19023, 412231856, true).insertQuery());
        DatabaseHelper.run(new Owns(19023, 201674933, false).insertQuery());
        DatabaseHelper.run(new Owns(19023, 401605312, false).insertQuery());
        DatabaseHelper.run(new Owns(32156, 188212217, true).insertQuery());
        DatabaseHelper.run(new Owns(32156, 207843218, false).insertQuery());
        DatabaseHelper.run(new Owns(32156, 122219876, false).insertQuery());
        DatabaseHelper.run(new Owns(32156, 344151573, false).insertQuery());
        DatabaseHelper.run(new Owns(32156, 203491209, false).insertQuery());
        DatabaseHelper.run(new Owns(32156, 210389768, false).insertQuery());
        DatabaseHelper.run(new Owns(53027, 207843218, true).insertQuery());
        DatabaseHelper.run(new Owns(43947, 212116070, true).insertQuery());
        DatabaseHelper.run(new Owns(43947, 210389768, false).insertQuery());
        DatabaseHelper.run(new Owns(60413, 361721022, true).insertQuery());
        DatabaseHelper.run(new Owns(60413, 400651982, false).insertQuery());
        DatabaseHelper.run(new Owns(60413, 122219876, false).insertQuery());
        DatabaseHelper.run(new Owns(60413, 231403227, false).insertQuery());
        DatabaseHelper.run(new Owns(67521, 209378521, true).insertQuery());
        DatabaseHelper.run(new Owns(67521, 401605312, false).insertQuery());
        DatabaseHelper.run(new Owns(67521, 212431965, false).insertQuery());
        // Insert Transactions on Account Creation
        DatabaseHelper.run(new Transaction(322175130, new Date(2011, 3, 2), Transaction.DEPOSIT, 200, 0, 17431).insertQuery());
        DatabaseHelper.run(new Transaction(212431965, new Date(2011, 3, 3), Transaction.DEPOSIT, 21000, 0, 54321).insertQuery());
        DatabaseHelper.run(new Transaction(207843218, new Date(2011, 3, 3), Transaction.DEPOSIT, 1200, 0, 12121).insertQuery());
        DatabaseHelper.run(new Transaction(201674933, new Date(2011, 3, 3), Transaction.DEPOSIT, 15000, 0, 41725).insertQuery());
        DatabaseHelper.run(new Transaction(122219876, new Date(2011, 3, 3), Transaction.DEPOSIT, 2000000, 0, 93156).insertQuery());
        DatabaseHelper.run(new Transaction(207843218, new Date(2011, 3, 4), Transaction.TOP_UP, 50, 12121, 53027).insertQuery());
        DatabaseHelper.run(new Transaction(212431965, new Date(2011, 3, 4), Transaction.DEPOSIT, 1289, 0, 43942).insertQuery());
        DatabaseHelper.run(new Transaction(209378521, new Date(2011, 3, 4), Transaction.DEPOSIT, 34000, 0, 29107).insertQuery());
        DatabaseHelper.run(new Transaction(412231856, new Date(2011, 3, 5), Transaction.DEPOSIT, 2300, 0, 19023).insertQuery());
        DatabaseHelper.run(new Transaction(400651982, new Date(2011, 3, 5), Transaction.TOP_UP, 20, 43942, 60413).insertQuery());
        DatabaseHelper.run(new Transaction(344151573, new Date(2011, 3, 5), Transaction.DEPOSIT, 1000, 0, 32156).insertQuery());
        DatabaseHelper.run(new Transaction(188212217, new Date(2011, 3, 5), Transaction.DEPOSIT, 8456, 0, 76543).insertQuery());
        DatabaseHelper.run(new Transaction(212116070, new Date(2011, 3, 5), Transaction.TOP_UP, 30, 29107, 43947).insertQuery());
        DatabaseHelper.run(new Transaction(401605312, new Date(2011, 3, 6), Transaction.TOP_UP, 100, 19023, 67521).insertQuery());
        // Insert Other Transactions
        DatabaseHelper.run(new Transaction(344151573, new Date(2011, 3, 2), Transaction.DEPOSIT, 8800, 0, 17431).insertQuery());
        DatabaseHelper.run(new Transaction(122219876, new Date(2011, 3, 3), Transaction.WITHDRAW, 3000, 54321, 0).insertQuery());
        DatabaseHelper.run(new Transaction(212116070, new Date(2011, 3, 5), Transaction.WITHDRAW, 2000, 76543, 0).insertQuery());
        DatabaseHelper.run(new Transaction(207843218, new Date(2011, 3, 5), Transaction.PURCHASE, 5, 53027, 0).insertQuery());
        DatabaseHelper.run(new Transaction(188212217, new Date(2011, 3, 6), Transaction.WITHDRAW, 1000000, 93156, 0).insertQuery());
        DatabaseHelper.run(new Transaction(209378521, new Date(2011, 3, 6), Transaction.WRITE_CHECK, 950000, 93156, 0).insertQuery());
        DatabaseHelper.run(new Transaction(212116070, new Date(2011, 3, 6), Transaction.WITHDRAW, 4000, 29107, 0).insertQuery());
        DatabaseHelper.run(new Transaction(210389768, new Date(2011, 3, 6), Transaction.COLLECT, 10, 43947, 29107).insertQuery());
        DatabaseHelper.run(new Transaction(212116070, new Date(2011, 3, 6), Transaction.TOP_UP, 30, 29107, 43947).insertQuery());
        DatabaseHelper.run(new Transaction(322175130, new Date(2011, 3, 7), Transaction.TRANSFER, 289, 43942, 17431).insertQuery());
        DatabaseHelper.run(new Transaction(400651982, new Date(2011, 3, 7), Transaction.WITHDRAW, 289, 43942, 0).insertQuery());
        DatabaseHelper.run(new Transaction(400651982, new Date(2011, 3, 8), Transaction.PAY_FRIEND, 10, 60413, 67521).insertQuery());
        DatabaseHelper.run(new Transaction(210389768, new Date(2011, 3, 8), Transaction.DEPOSIT, 50000, 0, 93156).insertQuery());
        DatabaseHelper.run(new Transaction(207843218, new Date(2011, 3, 8), Transaction.WRITE_CHECK, 200, 12121, 0).insertQuery());
        DatabaseHelper.run(new Transaction(201674933, new Date(2011, 3, 8), Transaction.TRANSFER, 1000, 41725, 19023).insertQuery());
        DatabaseHelper.run(new Transaction(401605312, new Date(2011, 3, 9), Transaction.WIRE, 4000, 41725, 32156).insertQuery());
        DatabaseHelper.run(new Transaction(207843218, new Date(2011, 3, 9), Transaction.PAY_FRIEND, 10, 53027, 60413).insertQuery());
        DatabaseHelper.run(new Transaction(122219876, new Date(2011, 3, 10), Transaction.PURCHASE, 15, 60413, 0).insertQuery());
        DatabaseHelper.run(new Transaction(203491209, new Date(2011, 3, 12), Transaction.WITHDRAW, 20000, 93156, 0).insertQuery());
        DatabaseHelper.run(new Transaction(188212217, new Date(2011, 3, 12), Transaction.WRITE_CHECK, 456, 76543, 0).insertQuery());
        DatabaseHelper.run(new Transaction(401605312, new Date(2011, 3, 12), Transaction.TOP_UP, 50, 19023, 67521).insertQuery());
        DatabaseHelper.run(new Transaction(212431965, new Date(2011, 3, 14), Transaction.PAY_FRIEND, 20, 19023, 53027).insertQuery());
        DatabaseHelper.run(new Transaction(210389768, new Date(2011, 3, 14), Transaction.COLLECT, 15, 43947, 29107).insertQuery());
    }
}
