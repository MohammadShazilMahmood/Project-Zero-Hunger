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

import java.util.List;

public class PendingRequestNGOAdapter extends RecyclerView.Adapter<PendingRequestNGOAdapter.MyViewHolder> {
    List<donationRequest> ls;
    Context c;

    public PendingRequestNGOAdapter(List<donationRequest> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public PendingRequestNGOAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c).inflate(R.layout.pending_request_row_hall,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingRequestNGOAdapter.MyViewHolder holder, int position) {
        holder.donorName.setText(ls.get(position).getDonorName());
        holder.donorAddress.setText(ls.get(position).getDonorAddress());
        holder.donorCity.setText(ls.get(position).getDonorCity());
        holder.donationTime.setText(ls.get(position).getTime());
        holder.donationID.setText("Donation ID: " + ls.get(position).getDonationID());
        holder.roww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, detailedPendingRequestNGO.class);
                Integer pos=holder.getAdapterPosition();
//                donationRequest req = ls.get(pos);
                i.putExtra("pendingDonationRequestNGO",ls.get(pos));
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
        TextView donorName, donorAddress, donorCity, donationTime, donationID;
        LinearLayout roww;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roww=itemView.findViewById(R.id.roww);
            donorName=itemView.findViewById(R.id.donorName);
            donorAddress=itemView.findViewById(R.id.donorAddress);
            donorCity=itemView.findViewById(R.id.donorCity);
            donationTime=itemView.findViewById(R.id.donationTime);
            donationID=itemView.findViewById(R.id.donationID);
        }
    }
}
