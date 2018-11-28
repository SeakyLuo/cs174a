package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.app.DatePickerDialog;
import android.content.Context;
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

    }

    public void GenerateMonthlyStatement(){
        // TODO: get monthly transactions
        ArrayList<Transaction> data = new ArrayList<>();
        ShowListDialog dialog = new ShowListDialog();
        dialog.setData(data);
        dialog.show(getFragmentManager(), "GenerateMonthlyStatement");
    }

    public void ListClosedAccounts(){
        // TODO: get closed accounts
        ArrayList<Account> data = new ArrayList<>();
        ShowListDialog dialog = new ShowListDialog();
        dialog.setData(data);
        dialog.show(getFragmentManager(), "ListClosedAccounts");
    }

    public void CustomerReport(){
        ArrayList<Account> data = Account.findAccounts(0);
        ShowListDialog dialog = new ShowListDialog();
        dialog.setData(data);
        dialog.show(getFragmentManager(), "CustomerReport");
    }

    public void DTER(){
        // TODO: get accounts
        ArrayList<Account> data = new ArrayList<>();
        ShowListDialog dialog = new ShowListDialog();
        dialog.setData(data);
        dialog.show(getFragmentManager(), "DTER");
    }

    public void AddInterest(){

    }
    public void DeleteAccounts(){

    }

    public void DeleteTrasactions(){

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
                DeleteTrasactions();
            }
        });
        return view;
    }
}
