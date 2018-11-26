package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UseridDialog extends DialogFragment {

    private AppCompatActivity caller;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_userid,container,false);
        setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
//        TODO: change id
        ((TextView) view.findViewById(R.id.userid_userid)).setText("");
        view.findViewById(R.id.userid_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                caller.finish();
            }
        });
        return view;
    }

    public void setCaller(AppCompatActivity caller){
        this.caller = caller;
    }
}
