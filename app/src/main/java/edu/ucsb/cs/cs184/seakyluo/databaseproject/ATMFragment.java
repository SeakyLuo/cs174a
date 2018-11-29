package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.app.Instrumentation;
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

import static android.app.Activity.RESULT_OK;

public class ATMFragment extends Fragment {
    public static final int LOGIN = 0;
    private Button login;

    public void Deposit(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.DEPOSIT);

        intent.putExtra(UserInputActivity.FROM_VISIBLE, false);
        intent.putExtra(UserInputActivity.TO_ACCOUNTS, Account.findAccountsWithoutType(DatabaseHelper.user.getId(), Account.POCKET, false));
        startActivity(intent);
    }

    public void TopUp(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.TOP_UP);

        intent.putExtra(UserInputActivity.FROM_VISIBLE, false);
        intent.putExtra(UserInputActivity.TO_ACCOUNTS, Account.findAccountsWithoutType(DatabaseHelper.user.getId(), Account.POCKET, false));
        startActivity(intent);
    }

    public void Withdraw(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.WITHDRAW);

        intent.putExtra(UserInputActivity.TO_VISIBLE, false);
        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, Account.findAccountsWithoutType(DatabaseHelper.user.getId(), Account.POCKET, false));
        startActivity(intent);
    }

    public void Purchase(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.PURCHASE);
        ;
        intent.putExtra(UserInputActivity.TO_VISIBLE, false);
        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, Account.findAccountsWithoutType(DatabaseHelper.user.getId(), Account.SAVINGS, false));
        startActivity(intent);
    }

    public void Transfer(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.TRANSFER);

        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, Account.findAccountsWithoutType(DatabaseHelper.user.getId(), Account.POCKET, false));
        intent.putExtra(UserInputActivity.TO_ACCOUNTS, Account.findAccountsWithoutType(DatabaseHelper.user.getId(), Account.POCKET, false));
        startActivity(intent);
    }

    public void Collect(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.COLLECT);

        intent.putExtra(UserInputActivity.TO_VISIBLE, false);
        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, Account.findAccountsWithType(DatabaseHelper.user.getId(), Account.POCKET, false));
        startActivity(intent);
    }

    public void Wire(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.DEPOSIT);

        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, Account.findAccountsWithoutType(DatabaseHelper.user.getId(), Account.POCKET, false));
        intent.putExtra(UserInputActivity.TO_TYPE, new String[] {Account.CHECKING, Account.SAVINGS});
        startActivity(intent);
    }

    public void PayFriend(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), UserInputActivity.class);
        intent.putExtra(UserInputActivity.TITLE, Transaction.PAY_FRIEND);

        intent.putExtra(UserInputActivity.FROM_ACCOUNTS, Account.findAccountsWithType(DatabaseHelper.user.getId(), Account.POCKET, false));
        intent.putExtra(UserInputActivity.TO_TYPE, new String[] {Account.POCKET});
        startActivity(intent);
    }

    public void QuickCash(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        QuickAmountDialog dialog = new QuickAmountDialog();
        dialog.showNow(getFragmentManager(), "QuickCash");
        dialog.setAccounts(Account.findAccounts(DatabaseHelper.user.getId()));
    }

    public void QuickRefill(){
        if (DatabaseHelper.user == null){
            Toast.makeText(getContext(), "Please log in first!", Toast.LENGTH_SHORT).show();
            return;
        }
        QuickAmountDialog dialog = new QuickAmountDialog();
        dialog.showNow(getFragmentManager(), "QuickRefill");
        dialog.setAccounts(Account.findAccounts(DatabaseHelper.user.getId()));
    }

    public void Login(){
        if (login.getText().toString().equals(getString(R.string.login))){
            startActivityForResult(new Intent(getContext(), LoginActivity.class), LOGIN);
        } else{
            DatabaseHelper.user = null;
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
        view.findViewById(R.id.quick_refill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickRefill();
            }
        });
        return view;
    }
}
