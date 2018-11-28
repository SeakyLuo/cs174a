package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowListDialog extends DialogFragment {

    private LinearLayout linearLayout;
    private EditText editText;
    private ViewGroup.MarginLayoutParams params;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_show_list,container,false);
        linearLayout = view.findViewById(R.id.sl_layout);
        editText = view.findViewById(R.id.sl_edit_text);
        params = new ViewGroup.MarginLayoutParams(linearLayout.getLayoutParams());
        params.setMargins(0, 16, 0, 16);
        return view;
    }

    public void setData(ArrayList data){
        for (Object obj: data){
            TextView textView = new TextView(getContext());
            textView.setText(obj.toString());
            linearLayout.addView(textView, params);
        }
    }

    public void needsInput(){

    }

    public interface OnEditFinishListener{

    }
}
