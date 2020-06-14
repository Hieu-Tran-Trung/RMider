package com.example.rmider.adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmider.R;
import com.example.rmider.model.Diary;
import com.example.rmider.utils.AccountUtils;
import com.example.rmider.view.EditLocationActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.graphics.Typeface.BOLD;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {

    private ArrayList<Diary> arrLocal;

    public DiaryAdapter(ArrayList<Diary> arrLocal) {
        this.arrLocal = arrLocal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_diary,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpannableString fomat = new SpannableString("Thời gian: "+arrLocal.get(position).getDate());
        fomat.setSpan(new StyleSpan(BOLD),0,11,0);
        holder.tvTime.setText(fomat);

        SpannableString fomat1 = new SpannableString("Địa điểm đi: " +arrLocal.get(position).getStart());
        fomat1.setSpan(new StyleSpan(BOLD),0,13,0);
        holder.tvStart.setText(fomat1);

        SpannableString fomat2 = new SpannableString("Thời gian đến: "+arrLocal.get(position).getEnd());
        fomat2.setSpan(new StyleSpan(BOLD),0,15,0);
        holder.tvEnd.setText(fomat2);

    }

    @Override
    public int getItemCount() {
        return arrLocal.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTime,tvStart,tvEnd;
        ImageView imgEditLocal,imgDelLocal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime       = itemView.findViewById(R.id.tvTime);
            tvStart      = itemView.findViewById(R.id.tvStart);
            tvEnd        = itemView.findViewById(R.id.tvEnd);
            imgEditLocal = itemView.findViewById(R.id.imgEditLocal);
            imgDelLocal  = itemView.findViewById(R.id.imgDelLocal);

            imgEditLocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EditLocationActivity.class);
                    intent.putExtra("editlocation",arrLocal.get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                    notifyDataSetChanged();
                }
            });

            imgDelLocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Diary")
                            .child(AccountUtils.getInstance().getAccount().getPhoneNumber())
                            .child(arrLocal.get(getAdapterPosition()).getKey())                // arr lấy ra key
                            .setValue(null, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(databaseError == null){
                                        Toast.makeText(v.getContext(), "Xoá thành công!", Toast.LENGTH_SHORT).show();
                                        arrLocal.remove(getAdapterPosition());
                                        notifyItemRemoved(getAdapterPosition());
                                    }
                                    else {
                                        Toast.makeText(v.getContext(), "Xoá thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }
    }
}
