package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jude.swipbackhelper.SwipeBackHelper;

import java.util.ArrayList;

public class UserInputActivity extends AppCompatActivity {

    public static final String TITLE = "Title", FROM = "from", TO = "to", AMOUNT = "amount", FROM_ACCOUNTS = "from_account", TO_ACCOUNTS = "to_account";
    public static final String FROM_VISIBLE = "fv", TO_VISIBLE = "tv";

    private TextView title;
    private EditText fromAccount, toAccount, amount;
    private ImageButton back;
    private Button confirm;
    private Account selected_from, selected_to;
    private boolean fromVisible = true, toVisible = true;
    private Intent callerIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        setContentView(R.layout.activity_user_input);
        callerIntent = getIntent();

        title = findViewById(R.id.ui_title);
        fromAccount = findViewById(R.id.ui_from);
        toAccount = findViewById(R.id.ui_to);
        amount = findViewById(R.id.ui_amount);
        back = findViewById(R.id.ui_back);
        confirm = findViewById(R.id.ui_confirm);

        title.setText(callerIntent.getStringExtra(TITLE));
        fromVisible = callerIntent.getBooleanExtra(FROM_VISIBLE, true);
        toVisible = callerIntent.getBooleanExtra(TO_VISIBLE, true);
        fromAccount.setVisibility(fromVisible ? View.VISIBLE : View.INVISIBLE);
        toAccount.setVisibility(toVisible ? View.VISIBLE : View.INVISIBLE);
        ArrayList<Account> fromAccounts =  (ArrayList<Account>) callerIntent.getSerializableExtra(FROM_ACCOUNTS);
        ArrayList<Account> toAccounts =  (ArrayList<Account>) callerIntent.getSerializableExtra(TO_ACCOUNTS);
        setFromEditable(fromAccounts);
        setToEditable(toAccounts);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (fromVisible) intent.putExtra(FROM,  Integer.parseInt(fromAccount.getText().toString()));
                if (toVisible) intent.putExtra(TO, Integer.parseInt(toAccount.getText().toString()));
                intent.putExtra(AMOUNT, Double.parseDouble(amount.getText().toString()));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setFromEditable(final ArrayList<Account> accounts){
        if (accounts == null){
            fromAccount.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            return;
        }
        fromAccount.setInputType(InputType.TYPE_NULL);
        fromAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAccountDialog dialog = new SelectAccountDialog();
                dialog.setAccounts(accounts);
                dialog.showNow(getSupportFragmentManager(), "SelectAccount");
                dialog.addOnConfirmListener(new SelectAccountDialog.onConfirmListener() {
                    @Override
                    public void onConfirm(Account account) {
                        selected_from = account;
                        fromAccount.setText(account.getId() + "");
                    }
                });
            }
        });
    }

    private void setToEditable(final ArrayList<Account> accounts){
        if (accounts == null){
            toAccount.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            return;
        }
        toAccount.setInputType(InputType.TYPE_NULL);
        toAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAccountDialog dialog = new SelectAccountDialog();
                dialog.setAccounts(accounts);
                dialog.showNow(getSupportFragmentManager(), "SelectAccount");
                dialog.addOnConfirmListener(new SelectAccountDialog.onConfirmListener() {
                    @Override
                    public void onConfirm(Account account) {
                        selected_to = account;
                        toAccount.setText(account.getId() + "");
                    }
                });
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
