package com.example.rmider.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rmider.R;
import com.example.rmider.model.Insurance;
import com.example.rmider.utils.DateUtils;

import java.util.ArrayList;

import static com.example.rmider.config.Constants.TYPE_BATBUOC;
import static com.example.rmider.config.Constants.TYPE_DANGKIEM;
import static com.example.rmider.config.Constants.TYPE_LOAIKHAC;
import static com.example.rmider.config.Constants.TYPE_VATCHAT;

public class InformInsuranceAdapter extends RecyclerView.Adapter<InformInsuranceAdapter.ViewHolder> {
    private ArrayList<Insurance> arrayList;
    public InformInsuranceAdapter(ArrayList<Insurance> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_insurance,parent,false);
       return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TimeHH.setText("Ngày hết hạn: " + DateUtils.getDate(arrayList.get(position).getAlarm()));
        holder.BaoTruoc.setText("Nhắc nhở trước: " + arrayList.get(position).getNexttime());

        switch (arrayList.get(position).getType()){
            case TYPE_VATCHAT:
                Glide.with(holder.imgloaiBH)
                        .load(R.drawable.ic_sec_1)
                        .into(holder.imgloaiBH);
                holder.tenBH.setText("Bảo Hiểm vật chất");
                break;
            case TYPE_BATBUOC:
                Glide.with(holder.imgloaiBH)
                        .load(R.drawable.ic_sec2_64dp)
                        .into(holder.imgloaiBH);
                holder.tenBH.setText("Bảo hiểm bắt buộc");
                break;
            case TYPE_DANGKIEM:
                Glide.with(holder.imgloaiBH)
                        .load(R.drawable.ic_shield_64dp)
                        .into(holder.imgloaiBH);
                holder.tenBH.setText("Đăng Kiểm");
                break;
            case TYPE_LOAIKHAC:
                Glide.with(holder.imgloaiBH)
                        .load(R.drawable.ic_sec3_64dp)
                        .into(holder.imgloaiBH);
                holder.tenBH.setText("Bảo hiểm loại khác");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgloaiBH;
        TextView tenBH,TimeHH,BaoTruoc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgloaiBH   =   itemView.findViewById(R.id.imgloaiBH);
            tenBH       =   itemView.findViewById(R.id.tenBH);
            TimeHH      =   itemView.findViewById(R.id.TimeHH);
            BaoTruoc    =   itemView.findViewById(R.id.BaoTruoc);
        }
    }
}
