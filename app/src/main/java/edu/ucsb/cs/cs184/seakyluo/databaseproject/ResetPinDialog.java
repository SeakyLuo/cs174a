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

public class ResetPinDialog extends DialogFragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_reset_pin,container,false);
        final EditText cid = view.findViewById(R.id.reset_pin_id);
        final EditText oldPIN = view.findViewById(R.id.reset_pin_old);
        final EditText newPIN = view.findViewById(R.id.reset_pin_new);
        view.findViewById(R.id.reset_pin_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(cid.getText().toString());
                if (Customer.VerifyPin(id, oldPIN.getText().toString())){
                    Customer.findCustomer(id).SetPin(newPIN.getText().toString());
                    Toast.makeText(getContext(), "Reset Successful!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }else{
                    oldPIN.setError("Incorrect Pin!");
                }
            }
        });
        return view;
    }
}
