package edu.ucsb.cs.cs184.seakyluo.databaseproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SelectAccountAdapter extends CustomAdapter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_account, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public static class ViewHolder extends CustomAdapter.CustomViewHolder{

        public TextView type, bank, balance;
        private Account data;

        public ViewHolder(@NonNull View view) {
            super(view);
            type = view.findViewById(R.id.va_type);
            bank = view.findViewById(R.id.va_bank_name);
            balance = view.findViewById(R.id.va_balance);
        }

        @Override
        public void setData(Object object) {
            data = (Account) object;
            type.setText(data.getType() + ": " + data.getId());
            bank.setText("Bank Name: " + data.getBankName());
            balance.setText("Balance: " + data.getBalance());
        }
    }
}
