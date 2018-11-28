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

    public static final String TITLE = "Title", FROM = "from", TO = "to";
    public static final String FROM_ACCOUNTS = "from_account", TO_ACCOUNTS = "to_account";
    public static final String FROM_VISIBLE = "fv", TO_VISIBLE = "tv";
    public static final String FROM_TYPE = "ft", TO_TYPE = "tt";

    private TextView title;
    private EditText fromAccount, toAccount, amount;
    private ImageButton back;
    private Button confirm;
    private int from, to;
    private Account selected_from, selected_to;
    private boolean fromVisible = true, toVisible = true;
    private String[] from_type, to_type;
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
        if (fromVisible && from_type != null && !findTypeIn(selected_from, from_type)){
            fromAccount.setError(selected_from.getType() + " NOT Supported!");
            return;
        }
        if (toVisible && to_type != null && !findTypeIn(selected_to, to_type)){
            toAccount.setError(selected_to.getType() + " NOT Supported!");
            return;
        }
        MakeTransaction();
    }

    private void MakeTransaction(){
        double amount = Double.parseDouble(this.amount.getText().toString());
        try{
            switch (title.getText().toString()){
                case Transaction.DEPOSIT:
                    Transaction.Deposit(to, amount);
                case Transaction.TOP_UP:
                    Transaction.TopUp(from, to, amount);
                case Transaction.WITHDRAW:
                    Transaction.Withdraw(from, amount);
                case Transaction.PURCHASE:
                    Transaction.Purchase(from, amount);
                case Transaction.TRANSFER:
                    Transaction.Transfer(from, to, amount);
                case Transaction.COLLECT:
                    Transaction.Collect(from, to, amount);
                case Transaction.WIRE:
                    Transaction.Wire(from, to, amount);
                case Transaction.PAY_FRIEND:
                    Transaction.PayFriend(from, to, amount);
                case Transaction.QUICK_CASH:
                    Transaction.QuickCash(from, amount);
                case Transaction.QUICK_REFILL:
                    Transaction.QuickRefill(from, amount);
            }
        }catch (Exception e){
            this.amount.setError(e.toString());
        }
        finish();
    }

    private void setEditable(final String type, final ArrayList<Account> accounts){
        EditText e = null;
        switch (type){
            case FROM:
                e = fromAccount;
            case TO:
                e = toAccount;
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
                dialog.setAccounts(accounts);
                dialog.showNow(getSupportFragmentManager(), "SelectAccount");
                dialog.addOnConfirmListener(new SelectAccountDialog.onConfirmListener() {
                    @Override
                    public void onConfirm(Account account) {
                        switch (type){
                            case FROM:
                                selected_from = account;
                                from = account.getId();
                            case TO:
                                selected_to = account;
                                to = account.getId();
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
