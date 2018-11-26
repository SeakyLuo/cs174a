package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ATMFragment extends Fragment {
    private Button login;

    public void Login(){
        if (login.getText().toString().equals(getString(R.string.login))){
            startActivity(new Intent(getContext(), LoginActivity.class));
            login.setText(getString(R.string.logout));
        } else{
            DatabaseHelper.user = null;
            login.setText(getString(R.string.login));
            Toast.makeText(getContext(), "Logout Successful!", Toast.LENGTH_SHORT).show();
        }
    }

    public void Deposit(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void TopUp(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void Withdraw(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void Purchase(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void Transfer(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void Collect(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void Wire(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void PayFriend(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void QuickCash(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_atm, container, false);
        login = view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        view.findViewById(R.id.deposit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deposit();
            }
        });
        view.findViewById(R.id.top_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopUp();
            }
        });
        view.findViewById(R.id.withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Withdraw();
            }
        });
        view.findViewById(R.id.purchase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Purchase();
            }
        });
        view.findViewById(R.id.transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transfer();
            }
        });
        view.findViewById(R.id.collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collect();
            }
        });
        view.findViewById(R.id.wire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wire();
            }
        });
        view.findViewById(R.id.pay_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayFriend();
            }
        });
        view.findViewById(R.id.quick_cash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickCash();
            }
        });
        return view;
    }
}
