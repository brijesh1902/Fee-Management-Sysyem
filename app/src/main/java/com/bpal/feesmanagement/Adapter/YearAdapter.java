package com.bpal.feesmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpal.feesmanagement.Common.Common;
import com.bpal.feesmanagement.Common.ItemClickListener;
import com.bpal.feesmanagement.DBModel.DBFee;
import com.bpal.feesmanagement.DBModel.DBPaid;
import com.bpal.feesmanagement.DBModel.Option;
import com.bpal.feesmanagement.DBModel.Student;
import com.bpal.feesmanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<Option> mdata;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    FeeAdapter adapter;
    DatabaseReference reference;

    public YearAdapter(Context context, ArrayList<Option> data) {
        mcontext = context;
        mdata = data;
    }

    @NonNull
    @Override
    public YearAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.view_year, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final YearAdapter.ViewHolder holder, int position) {
        final Option options = mdata.get(position);

        holder.text_y.setText(options.getYear());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recyclerView.getContext());

        boolean isExpanded = mdata.get(position).isExpanded();
        holder.recyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);


        holder.recyclerView.setLayoutManager(layoutManager);

        reference = FirebaseDatabase.getInstance().getReference("Fees Management").child("Student")
                .child(Common.currentstudent.getPhone()).child("Fee").child("EMI").child(options.getYear());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<DBPaid> list = new ArrayList<>();
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        DBPaid dbItems = snap.getValue(DBPaid.class);
                        list.add(dbItems);
                    }
                    adapter = new FeeAdapter(mcontext, list);

                    holder.recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    holder.recyclerView.setRecycledViewPool(viewPool);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_y;
        ImageView image, dimage;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_y = itemView.findViewById(R.id.txt_y);
            image = itemView.findViewById(R.id.imageView2);
            recyclerView = itemView.findViewById(R.id.rv_feedetails);
            dimage = itemView.findViewById(R.id.imageView3);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Option formula = mdata.get(getAdapterPosition());
                    formula.setExpanded(!formula.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
