package com.example.rmider.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmider.R;
import com.example.rmider.model.Insurance;

import java.util.ArrayList;

public class InsuaraceAdapter extends RecyclerView.Adapter<InsuaraceAdapter.ViewHolder> {

    private ArrayList<Insurance> arrayList;

    public InsuaraceAdapter(ArrayList<Insurance> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.col_insurance,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTimes.setText("Lần " + position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTimes;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTimes = itemView.findViewById(R.id.tvTimes);
        }
    }
}