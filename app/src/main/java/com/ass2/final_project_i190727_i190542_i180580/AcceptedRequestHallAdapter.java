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

public class AcceptedRequestHallAdapter extends RecyclerView.Adapter<AcceptedRequestHallAdapter.MyViewHolder> {
    List<acceptedRequest> ls;
    Context c;

    public AcceptedRequestHallAdapter(List<acceptedRequest> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c).inflate(R.layout.accepted_request_row_hall,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.NGOName.setText(ls.get(position).getNGO_Name());
        holder.NGOAddress.setText(ls.get(position).getNGO_Address());
        holder.NGOCity.setText(ls.get(position).getNGO_City());
        holder.acceptTime.setText(ls.get(position).getAcceptedTime());
//        holder.donationTime.setVisibility(View.GONE);
        holder.donationID.setText("Donation ID: " + ls.get(position).getRequest().getDonationID());
        holder.roww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, detailedAcceptedRequestHall.class);
                Integer pos=holder.getAdapterPosition();
//                donationRequest req = ls.get(pos);
                i.putExtra("acceptedDonationRequestHall",ls.get(pos));
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
        TextView NGOName, NGOAddress, NGOCity, acceptTime, donationID;
        LinearLayout roww;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roww=itemView.findViewById(R.id.roww);
            NGOName=itemView.findViewById(R.id.NGOName);
            NGOAddress=itemView.findViewById(R.id.NGOAddress);
            NGOCity=itemView.findViewById(R.id.NGOCity);
            acceptTime=itemView.findViewById(R.id.acceptTime);
            donationID=itemView.findViewById(R.id.donationID);
        }
    }
}
