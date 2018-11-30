package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;

public class BankTellerFragment extends Fragment {

    public void SetInterest(){
        SetInterestDialog dialog = new SetInterestDialog();
        dialog.showNow(getFragmentManager(), "SetInterest");
    }

    public void SetNewDate(){
//        PickDateDialog dialog = new PickDateDialog();
//        dialog.showNow(getFragmentManager(), "DatePicker");
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                if (new Date(year, month, dayOfMonth).getTime() < DbHelper.time.getTime())
                    Toast.makeText(getContext(), "Cannot Set Date Earlier", Toast.LENGTH_SHORT).show();
                else if (DbHelper.getMonth() - month > 1)
                    Toast.makeText(getContext(), "Cannot Set More than 1 Month", Toast.LENGTH_SHORT).show();
                else {
                    addInterest();
                    DbHelper.setTime(year, month, dayOfMonth);
                    Toast.makeText(getContext(), "Set Date Successful", Toast.LENGTH_SHORT).show();
                }
            }
        }, DbHelper.getYear(), DbHelper.getMonth() - 1, DbHelper.getDay());
        datePickerDialog.show();
    }

    public void ResetPIN(){
        ResetPinDialog dialog = new ResetPinDialog();
        dialog.showNow(getFragmentManager(), "ResetPin");
    }

    public void EnterCheckTransaction(){
        if (DbHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Account> accounts = Account.findAccountsWithType(DbHelper.user.getId(), Account.CHECKING, false);
        if (accounts.size() == 0){
            Toast.makeText(getContext(), "You don't have a Checking account!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.WRITE_CHECK);
        intent.putExtra(UserInputActivity.TO_VISIBLE, false);
        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, accounts);
        startActivity(intent);
    }

    public void GenerateMonthlyStatement(){
        final ShowListDialog dialog = new ShowListDialog();
        dialog.showNow(getFragmentManager(), "GenerateMonthlyStatement");
        dialog.setTitle("GenerateMonthlyStatement");
        dialog.needsInput("Customer ID", new ShowListDialog.OnEditFinishListener() {
            @Override
            public void EditFinish(String text) {
                ArrayList<String> data = new ArrayList<>();
                int cid = Integer.parseInt(text);
                for (Transaction transaction: Transaction.MonthlyStatement(cid))
                    data.add(transaction.toString());
                for (Account account: Account.findPrimaryAccounts(cid))
                    if (account.getBalance() > 100000)
                        data.add(account.getId() + ": the limit of insurance has reached");
                dialog.setData(data);
            }
        });
    }

    public void ListClosedAccounts(){
        ArrayList<Account> data = new ArrayList<>();
        for (Account account: Account.findClosedAccounts()){
            if (account.isClosed()){
                ArrayList<Transaction> transactions = Account.findTransactions(account.getId());
                boolean valid = false;
                for (Transaction transaction: transactions){
                    if (DbHelper.getMonth() - DbHelper.getMonth(transaction.getTime()) == 1){
                        valid = true;
                    }else if (DbHelper.getMonth() == DbHelper.getMonth(transaction.getTime())){
                        valid = false;
                        break;
                    }
                }
                if (valid) data.add(account);
            }
        }
        ShowListDialog dialog = new ShowListDialog();
        dialog.showNow(getFragmentManager(), "ListClosedAccounts");
        dialog.setData(data);
    }

    public void CustomerReport(){
        final ShowListDialog dialog = new ShowListDialog();
        dialog.showNow(getFragmentManager(), "CustomerReport");
        dialog.needsInput("Customer ID", new ShowListDialog.OnEditFinishListener() {
            @Override
            public void EditFinish(String text) {
                ArrayList<Account> data = Account.findAccounts(Integer.parseInt(text));
                dialog.setData(data);
            }
        });
    }

    public void DTER(){
        ArrayList<Customer> data = new ArrayList<>();
        ShowListDialog dialog = new ShowListDialog();
        for(Customer customer: (ArrayList<Customer>) DbHelper.get(Customer.getQuery(), Customer.TABLE_NAME)){
            double sum = 0;
            for (Transaction transaction: Customer.findTransactions(customer.getId())){
                if (DbHelper.getMonth(transaction.getTime()) == DbHelper.getMonth() && customer.ownsAcount(transaction.getTo()) &&
                    (transaction.isType(Transaction.DEPOSIT) || transaction.isType(Transaction.TRANSFER) || transaction.isType(Transaction.WIRE)))
                    sum += transaction.getAmount();
            }
            if (sum > 10000)
                data.add(customer);
        }
        dialog.showNow(getFragmentManager(), "DTER");
        dialog.setData(data);
    }

    private void addInterest(){
        for (Account account: (ArrayList<Account>) DbHelper.get(Account.getQuery(), Account.TABLE_NAME))
            Transaction.AccrueInterest(account);
    }

    public void AddInterest(){
        Toast.makeText(getContext(), "This should be called automatically.", Toast.LENGTH_SHORT).show();
    }

    public void DeleteAccounts(){
        ArrayList<Account> accounts = Account.findClosedAccounts();
        for (Account account: accounts)
            DbHelper.run(account.deleteQuery());
        int count = 0;

        for (Customer customer: (ArrayList<Customer>) DbHelper.get(Customer.getQuery(), Customer.TABLE_NAME))
            if (Account.findAccounts(customer.getId()).size() == 0){
                DbHelper.run("DELETE * FROM" + Owns.TABLE_NAME + " WHERE " + Owns.CID + "=" + customer.getId());
                count++;
            }
        Toast.makeText(getContext(), accounts.size() + "Accounts and " + count + " Customers deleted", Toast.LENGTH_SHORT).show();
    }

    public void DeleteTransactions(){
        // Delete All
        for (Transaction transaction: (ArrayList<Transaction>) DbHelper.get(Transaction.getQuery(), Transaction.TABLE_NAME))
            DbHelper.run(transaction.deleteQuery());
        Toast.makeText(getContext(), "Delete Successful!", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_teller, container, false);
        view.findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SignupActivity.class));
            }
        });
        view.findViewById(R.id.create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DbHelper.user == null){
                    Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(getContext(), CreateAccountActivity.class));
            }
        });
        view.findViewById(R.id.set_new_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetNewDate();
            }
        });
        view.findViewById(R.id.set_interest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetInterest();
            }
        });
        view.findViewById(R.id.reset_pin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPIN();
            }
        });
        view.findViewById(R.id.enter_check_transaction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterCheckTransaction();
            }
        });
        view.findViewById(R.id.generate_monthly_statement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateMonthlyStatement();
            }
        });
        view.findViewById(R.id.list_closed_accounts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListClosedAccounts();
            }
        });
        view.findViewById(R.id.customer_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerReport();
            }
        });
        view.findViewById(R.id.dter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DTER();
            }
        });
        view.findViewById(R.id.add_interest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInterest();
            }
        });
        view.findViewById(R.id.delete_accounts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAccounts();
            }
        });
        view.findViewById(R.id.delete_transaction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteTransactions();
            }
        });
        return view;
    }
}
