package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jude.swipbackhelper.SwipeBackHelper;

import java.sql.Date;
import java.util.ArrayList;

public class CreateAccountActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton ichecking, schecking, savings, pocket;
    private EditText input_amount, bank_name, select_account;
    private Button create_account;
    private Account selected_account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        setContentView(R.layout.activity_create_account);
        radioGroup = findViewById(R.id.ca_radio_group);
        ichecking = findViewById(R.id.ca_ichecking);
        schecking = findViewById(R.id.ca_schecking);
        savings = findViewById(R.id.ca_savings);
        pocket = findViewById(R.id.ca_pocket);
        bank_name = findViewById(R.id.ca_bank_name);
        input_amount = findViewById(R.id.ca_input_amount);
        create_account = findViewById(R.id.ca_create_account);
        select_account = findViewById(R.id.ca_select_account);

        ArrayList<Owns> owns = DatabaseHelper.get(Owns.getQuery() + " WHERE o.cid = " + DatabaseHelper.user.getId() , Owns.TABLE_NAME);
        pocket.setVisibility(owns.size() > 0 ? View.VISIBLE : View.INVISIBLE);
        select_account.setVisibility(View.INVISIBLE);
        select_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAccountDialog dialog = new SelectAccountDialog();
                dialog.setAccounts(Account.findAccounts(DatabaseHelper.user.getId()));
                dialog.showNow(getSupportFragmentManager(), "SelectAccount");
                dialog.addOnConfirmListener(new SelectAccountDialog.onConfirmListener() {
                    @Override
                    public void onConfirm(Account account) {
                        selected_account = account;
                        select_account.setText(account.getId() + "");
                    }
                });
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.ca_pocket){
                    select_account.setVisibility(View.VISIBLE);
                    bank_name.setVisibility(View.INVISIBLE);
                    input_amount.setHint("TopUp");
                }else{
                    select_account.setVisibility(View.INVISIBLE);
                    bank_name.setVisibility(View.VISIBLE);
                    input_amount.setHint("Deposit");
                }
            }
        });
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double balance = 0;
                boolean isPocket = radioGroup.getCheckedRadioButtonId() == R.id.ca_pocket;
                if (isPocket){
                    if (Double.parseDouble(input_amount.getText().toString()) > selected_account.getBalance()){
                        input_amount.setError("Too Much!");
                        return;
                    }
                }else{
                    if (bank_name.getText().toString().isEmpty()){
                        bank_name.setError("Empty Bank Name!");
                        return;
                    }
                    if (input_amount.getText().toString().isEmpty()){
                        input_amount.setError("Empty Amount!");
                        return;
                    }
                }
                balance = Double.parseDouble(input_amount.getText().toString());
                int acount = DatabaseHelper.getAcount();
                DatabaseHelper.updateCounter(DatabaseHelper.ACOUNT);
                String type = "";
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.ca_ichecking:
                        type = Account.INTEREST_CHECKING;
                    case R.id.ca_schecking:
                        type = Account.STUDENT_CHECKING;
                    case R.id.ca_savings:
                        type = Account.SAVINGS;
                    case R.id.ca_pocket:
                        type = selected_account.getId() + "";
                }
                DatabaseHelper.run(new Account(acount, bank_name.getText().toString(), type).insertQuery());
                DatabaseHelper.run(Owns.InsertQuery(DatabaseHelper.user.getId(), acount, true));
                if (isPocket)
                    DatabaseHelper.run(Transaction.InsertQuery(DatabaseHelper.user.getId(), DatabaseHelper.time, Transaction.TOP_UP, balance, selected_account.getId(), acount));
                else
                    DatabaseHelper.run(Transaction.InsertQuery(DatabaseHelper.user.getId(), DatabaseHelper.time, Transaction.DEPOSIT, balance, 0, acount));
                finish();
            }
        });
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
