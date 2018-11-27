package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jude.swipbackhelper.SwipeBackHelper;

import java.util.ArrayList;

public class CreateAccountActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton checking, savings, pocket;
    private Button create_account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        setContentView(R.layout.activity_create_account);
        radioGroup = findViewById(R.id.ca_radio_group);
        checking = findViewById(R.id.ca_checking);
        savings = findViewById(R.id.ca_savings);
        pocket = findViewById(R.id.ca_pocket);
        create_account = findViewById(R.id.ca_create_account);

        ArrayList<Owns> owns = DatabaseHelper.get(Owns.getQuery() + " WHERE o.cid = " + DatabaseHelper.user.getId() , Owns.TABLE_NAME);
        pocket.setVisibility(owns.size() > 0 ? View.VISIBLE : View.INVISIBLE);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.ca_checking:
                        PutMoney();
                    case R.id.ca_savings:
                        PutMoney();
                    case R.id.ca_pocket:
                        LinkAccount();
                }
            }
        });
    }

    public void LinkAccount(){
        ArrayList<Account> accounts = new ArrayList<>();
        for(Account account: Account.findUserAccounts()){
            if (!account.isPocket() && !account.isClosed()){
                accounts.add(account);
            }
        }
        // TODO: show accounts
    }

    public void PutMoney(){
        
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }
}
