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

import java.util.ArrayList;

public class BankTellerFragment extends Fragment {

    public void SetNewDate(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DatePickerDialog dialog = new DatePickerDialog(getContext());
            dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    DatabaseHelper.setTime(year, month, dayOfMonth);
                }
            });
            dialog.show();
        }
    }

    public void ResetPIN(){
        ResetPinDialog dialog = new ResetPinDialog();
        dialog.showNow(getFragmentManager(), "ResetPin");
    }

    public void EnterCheckTransaction(){
        // TODO
    }

    public void GenerateMonthlyStatement(){
        final ShowListDialog dialog = new ShowListDialog();
        dialog.needsInput("Customer ID", new ShowListDialog.OnEditFinishListener() {
            @Override
            public void EditFinish(String text) {
                ArrayList<Transaction> data = Transaction.MonthlyStatement(Integer.parseInt(text));
                dialog.setData(data);
            }
        });
        dialog.show(getFragmentManager(), "GenerateMonthlyStatement");
    }

    public void ListClosedAccounts(){
        ArrayList<Account> data = Account.findClosedAccounts();
        ShowListDialog dialog = new ShowListDialog();
        dialog.setData(data);
        dialog.show(getFragmentManager(), "ListClosedAccounts");
    }

    public void CustomerReport(){
        final ShowListDialog dialog = new ShowListDialog();
        dialog.needsInput("Customer ID", new ShowListDialog.OnEditFinishListener() {
            @Override
            public void EditFinish(String text) {
                ArrayList<Account> data = Account.findAccounts(Integer.parseInt(text));
                dialog.setData(data);
            }
        });
        dialog.show(getFragmentManager(), "CustomerReport");
    }

    public void DTER(){
        // TODO: get customers
        ArrayList<Customer> data = new ArrayList<>();
        ShowListDialog dialog = new ShowListDialog();
        dialog.setData(data);
        dialog.show(getFragmentManager(), "DTER");
    }

    public void AddInterest(){
        for (Account account: (ArrayList<Account>) DatabaseHelper.get(Account.getQuery(), Account.TABLE_NAME))
            Transaction.AccrueInterest(account);
    }

    public void DeleteAccounts(){
        for (Account account: Account.findClosedAccounts())
            DatabaseHelper.run(account.deleteQuery());
        for (Customer customer: (ArrayList<Customer>) DatabaseHelper.get(Customer.getQuery(), Customer.TABLE_NAME))
            if (Account.findAccounts(customer.getId()).size() == 0)
                DatabaseHelper.run(customer.deleteQuery());
    }

    public void DeleteTransactions(){
        for (Transaction transaction: (ArrayList<Transaction>) DatabaseHelper.get(Transaction.getQuery(), Transaction.TABLE_NAME))
            if (DatabaseHelper.time.getMonth() - transaction.getTime().getMonth() > 1)
                DatabaseHelper.run(transaction.deleteQuery());
        // TODO: description seems to say delete all?
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
                if (DatabaseHelper.user == null){
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
