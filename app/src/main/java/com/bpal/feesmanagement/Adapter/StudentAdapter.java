package com.bpal.feesmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpal.feesmanagement.Common.Common;
import com.bpal.feesmanagement.Common.ItemClickListener;
import com.bpal.feesmanagement.DBModel.Student;
import com.bpal.feesmanagement.R;
import com.bpal.feesmanagement.Staff.StudentDetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<Student> mdata;

    public StudentAdapter(Context context, ArrayList<Student> data) {
        mcontext = context;
        mdata = data;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.student_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentAdapter.ViewHolder holder, int position) {
        final Student options = mdata.get(position);

        Glide.with(mcontext).load(R.drawable.student).apply(RequestOptions.circleCropTransform()).into(holder.image);
        holder.textuid.setText(options.getUid());
        holder.textname.setText(options.getFirstname()+" "+options.getLastname());
        holder.textcourse.setText(options.getYear()+" ("+options.getStream()+"/"+options.getCourse()+")");

        if (options.getRemaining().equals("00")){
            holder.textfpaid.setText("Fee Paid");
        } else {
            holder.textfpaid.setText("₹ "+options.getRemaining()+"/"+options.getTotal());
        }


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                    Intent intent = new Intent(mcontext, StudentDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Common.currentstudent = options;
                    mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView textuid, textname, textcourse, textfpaid, textfrem;
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            textuid = itemView.findViewById(R.id.textuid);
            textname = itemView.findViewById(R.id.textname);
            textcourse = itemView.findViewById(R.id.textcourse);
            textfpaid = itemView.findViewById(R.id.textfpaid);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClickListener(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }


    }

}