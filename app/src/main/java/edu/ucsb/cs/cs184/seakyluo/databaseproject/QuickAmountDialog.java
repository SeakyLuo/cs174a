package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

    public static final String QUICK_REILL = "Quick Refill", QUICK_CASH = "Quick Cash";

    private EditText editText;
    private RadioGroup radioGroup;
    private Button set, quick;
    private ArrayList<Account> accounts = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_account, container, false);
        editText = view.findViewById(R.id.qa_input);
        radioGroup = view.findViewById(R.id.qa_radioGroup);
        set = view.findViewById(R.id.qa_set);
        quick = view.findViewById(R.id.qa_quick);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (getAccount().isType(Account.POCKET))
                    quick.setText(QUICK_REILL);
                else
                    quick.setText(QUICK_CASH);
            }
        });
        if(DatabaseHelper.user.getPreamount() != 0) editText.setText(DatabaseHelper.user.getPreamount() + "");
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAccount() == null){
                    Toast.makeText(getContext(), "No account selected!", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseHelper.user.setPreaccount(getAccount().getId());
                }
                DatabaseHelper.user.setPreamount(Double.parseDouble(editText.getText().toString()));
            }
        });
        quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (quick.getText().equals(QUICK_CASH))
                        Transaction.QuickCash(getAccount().getId(), Double.parseDouble(editText.getText().toString()));
                    else if (quick.getText().equals(QUICK_REILL))
                        Transaction.QuickRefill(getAccount().getId(), Double.parseDouble(editText.getText().toString()));
                } catch (Account.NotEnoughMoneyException e) {
                    editText.setError(e.toString());
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

    public void setAccounts(ArrayList<Account> accounts){
        this.accounts = accounts;
        for (Account account: accounts){
            RadioButton button = new RadioButton(getContext());
            button.setPadding(16,8,16,8);
            button.setText(account.toString());
            if (account.getId() == DatabaseHelper.user.getPreaccount())
                button.setChecked(true);
            radioGroup.addView(button);
        }
    }
}
