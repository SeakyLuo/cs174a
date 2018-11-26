package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BankTellerFragment extends Fragment {

    public void SetNewDate(){

    }

    public void ResetPIN(){
        ResetPinDialog dialog = new ResetPinDialog();
        dialog.showNow(getFragmentManager(), "ResetPin");
    }

    public void EnterCheckTransaction(){

    }

    public void GenerateMonthlyStatement(){

    }

    public void ListClosedAccounts(){

    }

    public void CustomerReport(){

    }

    public void DTER(){

    }

    public void AddInterest(){

    }

    public void CreateAccount(){
        startActivity(new Intent(getContext(), SignupActivity.class));
    }

    public void DeleteAccounts(){

    }

    public void DeleteTrasactions(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_teller, container, false);
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
        view.findViewById(R.id.create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
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
