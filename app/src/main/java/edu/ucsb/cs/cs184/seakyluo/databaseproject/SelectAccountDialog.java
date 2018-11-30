package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectAccountDialog extends DialogFragment {

    private RadioGroup radioGroup;
    private Button confirm;
    private ArrayList<onConfirmListener> listeners = new ArrayList<>();
    private ArrayList<Account> accounts = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_account, container, false);
        confirm = view.findViewById(R.id.sa_confirm);
        radioGroup = view.findViewById(R.id.sa_radio_group);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (onConfirmListener listener: listeners){
                    listener.onConfirm(accounts.get(radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()))));
                }
                dismiss();
            }
        });
        return view;
    }

    public void setAccounts(ArrayList<Account> accounts){
        this.accounts = accounts;
        for (Account account: accounts){
            RadioButton button = new RadioButton(getContext());
            button.setPadding(16,8,16,8);
            button.setText(account.toString());
            radioGroup.addView(button);
        }
    }

    public void addOnConfirmListener(onConfirmListener listener){
        listeners.add(listener);
    }

    public interface onConfirmListener{
        public void onConfirm(Account account);
    }
}
