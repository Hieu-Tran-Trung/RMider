package com.example.rmider.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rmider.R;
import com.example.rmider.callback.OnItemClickAssis;
import com.example.rmider.model.Assis;

import java.util.ArrayList;

public class AssistanceAdapter extends RecyclerView.Adapter<AssistanceAdapter.ViewHolder> {
    private ArrayList<Assis> assisArrayList;
    private OnItemClickAssis onItemClickAssis;

    public AssistanceAdapter(ArrayList<Assis> assisArrayList,OnItemClickAssis onItemClickAssis) {
        this.assisArrayList = assisArrayList;
        this.onItemClickAssis = onItemClickAssis;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_assis, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.imgPicAssis)
                .load(assisArrayList.get(position).getPicture())
                .into(holder.imgPicAssis);
        holder.tvNameBrand.setText("Tên hãng :" + assisArrayList.get(position).getName());
        holder.tvPhoneHelp.setText("Số điện thoại :" + assisArrayList.get(position).getSdt());
    }

    @Override
    public int getItemCount() {
        return assisArrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPicAssis;
        TextView tvNameBrand, tvPhoneHelp;
        LinearLayout lnClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicAssis = itemView.findViewById(R.id.imgPicAssis);
            tvNameBrand = itemView.findViewById(R.id.tvNameBrand);
            tvPhoneHelp = itemView.findViewById(R.id.tvPhoneHelp);
            lnClick     = itemView.findViewById(R.id.lnClick);
            lnClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                  ((HouseActivity)v.getContext())(assisArrayList.get(getPosition()).getSdt());

            //        ((AssistanceActivity)v.getContext()).setNumberPhone(assisArrayList.get(getLayoutPosition()).getSdt());
                    onItemClickAssis.onItemClick(assisArrayList.get(getLayoutPosition()).getSdt());
                }
            });
        }
    }
}
