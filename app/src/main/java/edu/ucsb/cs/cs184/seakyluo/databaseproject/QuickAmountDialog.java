package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class QuickAmountDialog extends DialogFragment {

    public static final String QUICK_REFILL = "Quick Refill", QUICK_CASH = "Quick Cash";

    private EditText editText;
    private RadioGroup radioGroup;
    private Button set, quick;
    private String title;
    private Account account;
    private ArrayList<Account> accounts = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_amount, container, false);
        editText = view.findViewById(R.id.qa_input);
        radioGroup = view.findViewById(R.id.qa_radioGroup);
        set = view.findViewById(R.id.qa_set);
        quick = view.findViewById(R.id.qa_quick);
        if(DatabaseHelper.user.getPreAmount() != 0) editText.setText(DatabaseHelper.user.getPreAmount() + "");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                account = getAccount();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                if (getAccount() == null){
                    Toast.makeText(getContext(), "No account selected!", Toast.LENGTH_SHORT).show();
                    valid = false;
                }
                if (editText.getText().toString().isEmpty()){
                    editText.setError("No amount!");
                    valid = false;
                }
                if (!valid) return;
                DatabaseHelper.user.setPreAccount(getAccount().getId());
                DatabaseHelper.user.setPreAmount(Double.parseDouble(editText.getText().toString()));
                Toast.makeText(getContext(), "Set Successful!", Toast.LENGTH_SHORT).show();
            }
        });
        quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("fuck", account.toString());
                    if (title.equals(QUICK_CASH))
                        Transaction.MakeTransaction(DatabaseHelper.user.getId(), DatabaseHelper.time, Transaction.QUICK_CASH, Double.parseDouble(editText.getText().toString()), account.getId(), 0);
                    else if (title.equals(QUICK_REFILL))
                        Transaction.MakeTransaction(DatabaseHelper.user.getId(), DatabaseHelper.time, Transaction.QUICK_REFILL, Double.parseDouble(editText.getText().toString()), 0, account.getId());
                    else
                        Log.d("fuck", "QuickAmountDialog has an incorrect title: " + title);
                    Toast.makeText(getContext(), quick.getText() + " Successful!", Toast.LENGTH_SHORT).show();
                    dismiss();
                } catch (Account.NotEnoughMoneyException e) {
                    editText.setError(e.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private Account getAccount(){
        try{
            return accounts.get(radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId())));
        }catch (Exception e){
            return null;
        }
    }

    public void setAccounts(ArrayList<Account> accounts, String title){
        this.accounts = accounts;
        this.title = title;
        this.quick.setText(title);
        for (Account account: accounts){
            RadioButton button = new RadioButton(getContext());
            button.setPadding(16,8,16,8);
            button.setText(account.toString());
            if (account.getId() == DatabaseHelper.user.getPreAccount()){
                this.account = account;
                Log.d("fuck", account.toString());
                button.setChecked(true);
            }
            radioGroup.addView(button);
        }
    }
}
