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

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ATMFragment extends Fragment {
    public static final int LOGIN = 0;
    private Button login, deposit, top_up, withdraw, purchase, transfer, collect, wire, pay_friend, quick_cash, quick_refill;

    public void Deposit(){
        if (DbHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Account> accounts = Account.findAccountsWithoutType(DbHelper.user.getId(), Account.POCKET, false);
        if (accounts.size() == 0){
            Toast.makeText(getContext(), "You don't have accounts available!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.DEPOSIT);
        intent.putExtra(UserInputActivity.TO_ACCOUNTS, accounts);
        intent.putExtra(UserInputActivity.FROM_VISIBLE, false);
        startActivity(intent);
    }

    public void TopUp(){
        if (DbHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Account> accounts = Account.findAccountsWithType(DbHelper.user.getId(), Account.POCKET, false);
        if (accounts.size() == 0){
            Toast.makeText(getContext(), "You don't have a Pocket Account!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.TOP_UP);
        intent.putExtra(UserInputActivity.TO_ACCOUNTS, accounts);
        intent.putExtra(UserInputActivity.FROM_VISIBLE, false);
        startActivity(intent);
    }

    public void Withdraw(){
        if (DbHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Account> accounts = Account.findAccountsWithoutType(DbHelper.user.getId(), Account.POCKET, false);
        if (accounts.size() == 0){
            Toast.makeText(getContext(), "You don't have accounts available!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.WITHDRAW);
        intent.putExtra(UserInputActivity.TO_VISIBLE, false);
        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, accounts);
        startActivity(intent);
    }

    public void Purchase(){
        if (DbHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Account> accounts = Account.findAccountsWithoutType(DbHelper.user.getId(), Account.SAVINGS, false);
        if (accounts.size() == 0){
            Toast.makeText(getContext(), "You don't have accounts available!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.PURCHASE);
        intent.putExtra(UserInputActivity.TO_VISIBLE, false);
        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, accounts);
        startActivity(intent);
    }

    public void Transfer(){
        if (DbHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Account> accounts = Account.findAccountsWithoutType(DbHelper.user.getId(), Account.POCKET, false);
        if (accounts.size() == 0){
            Toast.makeText(getContext(), "You don't have accounts available!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.TRANSFER);
        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, accounts);
        intent.putExtra(UserInputActivity.TO_ACCOUNTS, accounts);
        startActivity(intent);
    }

    public void Collect(){
        if (DbHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Account> accounts = Account.findAccountsWithType(DbHelper.user.getId(), Account.POCKET, false);
        if (accounts.size() == 0){
            Toast.makeText(getContext(), "You don't have a Pocket account!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.COLLECT);
        intent.putExtra(UserInputActivity.TO_VISIBLE, false);
        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, accounts);
        startActivity(intent);
    }

    public void Wire(){
        if (DbHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Account> accounts = Account.findAccountsWithoutType(DbHelper.user.getId(), Account.POCKET, false);
        if (accounts.size() == 0){
            Toast.makeText(getContext(), "You don't have accounts available!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.WIRE);
        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, accounts);
        intent.putExtra(UserInputActivity.TO_TYPE, new String[] {Account.CHECKING, Account.SAVINGS});
        startActivity(intent);
    }

    public void PayFriend(){
        if (DbHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Account> accounts = Account.findAccountsWithType(DbHelper.user.getId(), Account.POCKET, false);
        if (accounts.size() == 0){
            Toast.makeText(getContext(), "You don't have a Pocket account!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.PAY_FRIEND);
        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, accounts);
        intent.putExtra(UserInputActivity.TO_TYPE, new String[] {Account.POCKET});
        startActivity(intent);
    }

    public void QuickCash(){
        if (DbHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Account> accounts = Account.findAccountsWithoutType(DbHelper.user.getId(), Account.POCKET, false);
        if (accounts.size() == 0){
            Toast.makeText(getContext(), "You don't have accounts available!", Toast.LENGTH_SHORT).show();
            return;
        }
        QuickAmountDialog dialog = new QuickAmountDialog();
        dialog.showNow(getFragmentManager(), QuickAmountDialog.QUICK_CASH);
        dialog.setAccounts(accounts, QuickAmountDialog.QUICK_CASH);
    }

    public void QuickRefill(){
        if (DbHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Account> accounts = Account.findAccountsWithType(DbHelper.user.getId(), Account.POCKET, false);
        if (accounts.size() == 0){
            Toast.makeText(getContext(), "You don't have a Pocket account!", Toast.LENGTH_SHORT).show();
            return;
        }
        QuickAmountDialog dialog = new QuickAmountDialog();
        dialog.showNow(getFragmentManager(), QuickAmountDialog.QUICK_REFILL);
        dialog.setAccounts(accounts, QuickAmountDialog.QUICK_REFILL);
    }

    public void Login(){
        if (login.getText().toString().equals(getString(R.string.login))){
            startActivityForResult(new Intent(getContext(), LoginActivity.class), LOGIN);
        } else{
            DbHelper.user = null;
            login.setText(getString(R.string.login));
            Toast.makeText(getContext(), "Logout Successful!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode){
            case LOGIN:
                login.setText(R.string.logout);
                break;
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
        deposit = view.findViewById(R.id.deposit);
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deposit();
            }
        });
        top_up = view.findViewById(R.id.top_up);
        top_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopUp();
            }
        });
        withdraw = view.findViewById(R.id.withdraw);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Withdraw();
            }
        });
        purchase = view.findViewById(R.id.purchase);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Purchase();
            }
        });
        transfer = view.findViewById(R.id.transfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transfer();
            }
        });
        collect = view.findViewById(R.id.collect);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collect();
            }
        });
        wire = view.findViewById(R.id.wire);
        wire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wire();
            }
        });
        pay_friend = view.findViewById(R.id.pay_friend);
        pay_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayFriend();
            }
        });
        quick_cash = view.findViewById(R.id.quick_cash);
        quick_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickCash();
            }
        });
        quick_refill = view.findViewById(R.id.quick_refill);
        quick_refill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickRefill();
            }
        });
        return view;
    }
}
