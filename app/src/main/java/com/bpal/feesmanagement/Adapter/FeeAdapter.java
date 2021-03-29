package com.bpal.feesmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpal.feesmanagement.DBModel.DBPaid;
import com.bpal.feesmanagement.R;

import java.util.ArrayList;

public class FeeAdapter extends RecyclerView.Adapter<FeeAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<DBPaid> mdata;

    public FeeAdapter(Context context, ArrayList<DBPaid> data) {
        mcontext = context;
        mdata = data;
    }

    @NonNull
    @Override
    public FeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.view_feedetail, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FeeAdapter.ViewHolder holder, int position) {
        final DBPaid options = mdata.get(position);

        holder.year.setText(options.getCurrentyear());
        holder.total.setText("₹ "+options.getTotalFee()+".00");
        holder.remain.setText("₹ "+options.getRemainingFee()+".00");
        holder.paid.setText("₹ "+options.getPaidFee()+".00");
        holder.date.setText(options.getPaidDate());

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView year, total, remain, date, paid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            year = itemView.findViewById(R.id.textView16);
            total = itemView.findViewById(R.id.textView17);
            remain = itemView.findViewById(R.id.textView18);
            date = itemView.findViewById(R.id.textView23);
            paid = itemView.findViewById(R.id.textView22);

        }
    }
}
