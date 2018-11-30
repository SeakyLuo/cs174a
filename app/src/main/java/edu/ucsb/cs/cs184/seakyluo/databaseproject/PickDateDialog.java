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
import android.widget.Toast;

import java.sql.Date;

public class PickDateDialog extends DialogFragment {

    private EditText year, month, day;
    private Button confirm;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_date_picker, container, false);
        year = view.findViewById(R.id.dp_year);
        month = view.findViewById(R.id.dp_month);
        day = view.findViewById(R.id.dp_day);
        confirm = view.findViewById(R.id.dp_confirm);
        year.setText(DbHelper.getYear() + "");
        month.setText(DbHelper.getMonth() + "");
        day.setText(DbHelper.getDay() + "");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                if (year.getText().toString().isEmpty()) {
                    year.setError("Empty");
                    valid = false;
                }
                if (month.getText().toString().isEmpty()) {
                    month.setError("Empty");
                    valid = false;
                }
                if (day.getText().toString().isEmpty()) {
                    day.setError("Empty");
                    valid = false;
                }
                if (valid) {
                    int y = Integer.parseInt(year.getText().toString()),
                        m = Integer.parseInt(month.getText().toString()),
                        d = Integer.parseInt(day.getText().toString());
                    if (m > 12 || m < 1) {
                        month.setError("Invalid Month");
                    } else {
                        Integer[] day_array = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                        int md = day_array[m - 1] + ((m == 2 && y % 4 == 0 && y % 400 != 0) ? 1 : 0);
                        if (d > md) {
                            day.setError("Invalid day");
                        } else {
                            if (new Date(y, m, d).getTime() < DbHelper.time.getTime())
                                Toast.makeText(getContext(), "You cannot set an earlier date.", Toast.LENGTH_SHORT).show();
                            else {
                                DbHelper.setTime(y, m, d);
                                Toast.makeText(getContext(), "Set Date Successful", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        }
                    }
                }
            }
        });
        return view;
    }
}
