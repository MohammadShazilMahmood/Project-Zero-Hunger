package com.ass2.final_project_i190727_i190542_i180580;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class CompleteRequestAdapter extends RecyclerView.Adapter<CompleteRequestAdapter.MyViewHolder> {
    List<completedRequest> ls;
    Context c;

    public CompleteRequestAdapter(List<completedRequest> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public CompleteRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c).inflate(R.layout.completed_request_row,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CompleteRequestAdapter.MyViewHolder holder, int position) {
        holder.donorName.setText("Donor Name: "+ ls.get(position).getAccepted_request().getRequest().getDonorName());
        holder.NGOName.setText("Collected By: "+ls.get(position).getAccepted_request().getNGO_Name());
        holder.collectTime.setText("Collection Date and Time: "+ls.get(position).getCompletion_time());
        holder.donationID.setText("Donation ID: " + ls.get(position).getAccepted_request().getRequest().getDonationID());
        holder.roww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, detailedCompletedRequest.class);
                Integer pos=holder.getAdapterPosition();
//                donationRequest req = ls.get(pos);

                i.putExtra("completedDonationRequest",ls.get(pos));
                c.startActivity(i);
                ((Activity)c).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView donorName, NGOName, collectTime, donationID;
        LinearLayout roww;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roww=itemView.findViewById(R.id.roww);
            donorName=itemView.findViewById(R.id.donorName);
            NGOName=itemView.findViewById(R.id.NGOName);
            collectTime=itemView.findViewById(R.id.collectTime);
            donationID=itemView.findViewById(R.id.donationID);
        }
    }
}
