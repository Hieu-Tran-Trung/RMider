package com.example.rmider.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmider.R;
import com.example.rmider.model.Maintain;
import com.example.rmider.utils.AccountUtils;
import com.example.rmider.view.EditMaintain;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.graphics.Typeface.BOLD;

public class MaintainAdapter extends RecyclerView.Adapter<MaintainAdapter.ViewHolder> {

    private ArrayList<Maintain> arrayList;

    public MaintainAdapter(ArrayList<Maintain> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_maintain,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpannableString formatData = new SpannableString("Ngày: " + arrayList.get(position).getNgay());
        formatData.setSpan(new StyleSpan(BOLD),0,5,0);
        holder.tvDate.setText(formatData);

        SpannableString formatDetails = new SpannableString("Chi tiết: " + arrayList.get(position).getDetail());
        formatDetails.setSpan(new StyleSpan(BOLD),0,9,0);
        holder.tvDetail.setText(formatDetails);

        SpannableString formatKm = new SpannableString("Số Km đã đi: " + arrayList.get(position).getSoKm());
        formatKm.setSpan(new StyleSpan(BOLD),0,12,0);
        holder.tvKm.setText(formatKm);

        SpannableString formatPrice = new SpannableString("Chi Phí: "+arrayList.get(position).getPrice());
        formatPrice.setSpan(new StyleSpan(BOLD),0,8,0);
        holder.tvPrice.setText(formatPrice);

        SpannableString formatNextDay = new SpannableString("Ngày bảo dưỡng tiếp theo: " + arrayList.get(position).getNgaytt());
        formatNextDay.setSpan(new StyleSpan(BOLD),0,25,0);
        holder.nextDay.setText(formatNextDay);

        if (arrayList.get(position).getCheck().equals(true)){
            holder.Check.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDate,tvDetail,tvKm,tvPrice,nextDay,Check;
        ImageView imgEdit,imgDel;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvDate    = itemView.findViewById(R.id.tvDate);
            tvDetail  = itemView.findViewById(R.id.tvDetail);
            tvKm      = itemView.findViewById(R.id.tvKm);
            tvPrice   = itemView.findViewById(R.id.tvPrice);
            nextDay   = itemView.findViewById(R.id.nextDay);
            imgEdit   = itemView.findViewById(R.id.imgEdit);
            imgDel    = itemView.findViewById(R.id.imgDel);
            Check     = itemView.findViewById(R.id.Check);

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),EditMaintain.class);
                    intent.putExtra("key",arrayList.get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                }
            });
            imgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Maintain")
                            .child(AccountUtils.getInstance().getAccount().getPhoneNumber())
                            .child(arrayList.get(getAdapterPosition()).getKey())
                            .setValue(null, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    if (databaseError == null){
                                        Toast.makeText(v.getContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();
                                        arrayList.remove(getLayoutPosition());
                                  //      notifyItemRemoved(getLayoutPosition());
                                        notifyDataSetChanged();
                                    }else {
                                        Toast.makeText(v.getContext(), "Xoá thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }
    }
}
