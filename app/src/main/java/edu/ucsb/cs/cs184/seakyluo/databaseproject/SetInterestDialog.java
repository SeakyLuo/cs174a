package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class SetInterestDialog  extends DialogFragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_set_interest,container,false);
        final EditText id = view.findViewById(R.id.si_id);
        final EditText interest = view.findViewById(R.id.si_interest);
        view.findViewById(R.id.si_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = Account.findAccount(Integer.parseInt(id.getText().toString()));
                if (account == null){
                    id.setError("Account Not Found");
                }else{
                    if (account.isType(Account.INTEREST_CHECKING)){
                        if (interest.getText().toString().isEmpty()){
                            interest.setError("Empty!");
                        }else{
                            account.setInterest(Double.parseDouble(interest.getText().toString()));
                            Toast.makeText(getContext(), "Set Interest Successful!", Toast.LENGTH_LONG).show();
                            dismiss();
                        }
                    }else
                        id.setError("Not Supported!");
                }
            }
        });
        return view;
    }
}