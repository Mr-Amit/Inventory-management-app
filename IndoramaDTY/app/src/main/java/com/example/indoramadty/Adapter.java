package com.example.indoramadty;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<method> payment_list;

    public Adapter(Context context, List<method> payment_list) {
        this.context = context;
        this.payment_list = payment_list;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        // Adapter.ViewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull Adapter.ViewHolder holder, int position) {
        if(payment_list != null && payment_list.size() > 0){
            method model = payment_list.get(position);
            holder.deliveryNo.setText(model.getDeliveryNo());
            holder.lineItemNo.setText(model.getLineItemNo());
            holder.cartonNo.setText(model.getCartonNO());
            holder.palleteNo.setText(model.getPalleteNo());
            holder.netWt.setText(model.getNetWt());
            holder.grossWt.setText(model.getGrossWt());
            holder.proReqNetWt.setText(model.getProReqNetWt());
            // holder.scanFlag.setText(model.getScanFlag());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return payment_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView deliveryNo, lineItemNo, cartonNo, palleteNo, netWt, grossWt, proReqNetWt,scanFlag;
        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            deliveryNo= itemView.findViewById(R.id.deliveryNo);
            lineItemNo= itemView.findViewById(R.id.lineItemNo);
            cartonNo = itemView.findViewById(R.id.cartonNo);
            palleteNo = itemView.findViewById(R.id.palletNo);
            netWt = itemView.findViewById(R.id.netWt);
            grossWt = itemView.findViewById(R.id.grossWt);
            proReqNetWt = itemView.findViewById(R.id.proReqNetWt);
            // scanFlag = itemView.findViewById(R.id.scanFlag);
        }
    }
}
