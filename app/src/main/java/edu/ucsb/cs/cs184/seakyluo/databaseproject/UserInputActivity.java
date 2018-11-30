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
import android.widget.Toast;

import com.jude.swipbackhelper.SwipeBackHelper;
import java.util.ArrayList;

public class UserInputActivity extends AppCompatActivity {

    public static final String TITLE = "Title", FROM = "from", TO = "to";
    public static final String FROM_ACCOUNTS = "from_account", TO_ACCOUNTS = "to_account";
    public static final String FROM_VISIBLE = "fv", TO_VISIBLE = "tv";
    public static final String FROM_TYPE = "ft", TO_TYPE = "tt";

    private TextView title_text;
    private EditText fromAccount, toAccount, amount;
    private ImageButton back;
    private Button confirm;
    private String title;
    private int from = 0, to = 0;
    private boolean fromVisible = true, toVisible = true;
    private String[] from_type, to_type;
    private Intent callerIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        setContentView(R.layout.activity_user_input);
        callerIntent = getIntent();

        title_text = findViewById(R.id.ui_title);
        fromAccount = findViewById(R.id.ui_from);
        toAccount = findViewById(R.id.ui_to);
        amount = findViewById(R.id.ui_amount);
        back = findViewById(R.id.ui_back);
        confirm = findViewById(R.id.ui_confirm);

        title = callerIntent.getStringExtra(TITLE);
        title_text.setText(title);
        fromVisible = callerIntent.getBooleanExtra(FROM_VISIBLE, true);
        toVisible = callerIntent.getBooleanExtra(TO_VISIBLE, true);
        fromAccount.setVisibility(fromVisible ? View.VISIBLE : View.GONE);
        toAccount.setVisibility(toVisible ? View.VISIBLE : View.GONE);
        ArrayList<Account> fromAccounts =  (ArrayList<Account>) callerIntent.getSerializableExtra(FROM_ACCOUNTS);
        ArrayList<Account> toAccounts =  (ArrayList<Account>) callerIntent.getSerializableExtra(TO_ACCOUNTS);
        from_type = (String[]) callerIntent.getSerializableExtra(FROM_TYPE);
        to_type = (String [])  callerIntent.getSerializableExtra(TO_TYPE);
        setEditable(FROM, fromAccounts);
        setEditable(TO, toAccounts);

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
                Confirm();
            }
        });
    }

    private void Confirm(){
        boolean isValid = true;
        if (fromVisible){
            if (fromAccount.getText().toString().isEmpty()){
                fromAccount.setError("Empty!");
                isValid = false;
            }
            Account selected_from = Account.findAccount(from);
            if (from_type != null && !findTypeIn(selected_from, from_type)){
                fromAccount.setError(selected_from.getType() + " NOT Supported!");
                isValid = false;
            }
        }
        if (toVisible){
            if (toAccount.getText().toString().isEmpty()){
                toAccount.setError("Empty!");
                isValid = false;
            }
            Account selected_to = Account.findAccount(to);
            if (to_type != null && !findTypeIn(selected_to, to_type)){
                toAccount.setError(selected_to.getType() + " NOT Supported!");
                isValid = false;
            }
        }
        if (amount.getText().toString().isEmpty()){
            amount.setError("Empty!");
            isValid = false;
        }
        if (isValid)
            MakeTransaction(Double.parseDouble(amount.getText().toString()), from, to);
    }

    private void MakeTransaction(double amount, int from, int to){
        try{
            Transaction.MakeTransaction(DbHelper.user.getId(), DbHelper.time, title, amount, from, to);
        } catch (Account.NotEnoughMoneyException e){
            this.amount.setError(e.getMessage());
            return;
        } catch (Transaction.TransactionException e){
            this.amount.setError(e.getMessage());
            return;
        } catch (Exception e) {
            e.printStackTrace();
            this.amount.setError(e.getMessage());
            return;
        }
        Toast.makeText(getApplicationContext(), title + " Successful!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setEditable(final String type, final ArrayList<Account> accounts){
        EditText e = null;
        switch (type){
            case FROM:
                e = fromAccount;
                break;
            case TO:
                e = toAccount;
                break;
        }
        final EditText editText = e;
        if (accounts == null){
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            return;
        }
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAccountDialog dialog = new SelectAccountDialog();
                dialog.showNow(getSupportFragmentManager(), "SelectAccount");
                dialog.setAccounts(accounts);
                dialog.setOnConfirmListener(new SelectAccountDialog.onConfirmListener() {
                    @Override
                    public void onConfirm(Account account) {
                        switch (type){
                            case FROM:
                                from = account.getId();
                                break;
                            case TO:
                                to = account.getId();
                                break;
                        }
                        editText.setText(account.getId() + "");
                    }
                });
            }
        });
    }

    private boolean findTypeIn(Account account, String[] types){
        for (String type: types)
            if (account.isType(type))
                return true;
        return false;
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
