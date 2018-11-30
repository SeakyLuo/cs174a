package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
        editText.setVisibility(View.GONE);
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

    public void needsInput(String hint, final OnEditFinishListener listener){
        editText.setVisibility(View.VISIBLE);
        editText.setHint(hint);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event != null &&
                    event.getAction() == KeyEvent.ACTION_DOWN &&
                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        // the user is done typing.
                        listener.EditFinish(v.getText().toString());
                        return true; // consume.
                    }
                }
                return false; // pass on to other listeners.
            }
        });
    }

    public interface OnEditFinishListener{
        void EditFinish(String text);
    }
}
