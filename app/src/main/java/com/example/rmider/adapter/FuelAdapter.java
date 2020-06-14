package com.example.rmider.adapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rmider.R;
import com.example.rmider.model.Fuel;

import java.util.ArrayList;

public class FuelAdapter extends RecyclerView.Adapter<FuelAdapter.ViewHolder> {

    private ArrayList<Fuel> arrayFuel;

    public FuelAdapter(ArrayList<Fuel> arrayFuel) {
        this.arrayFuel = arrayFuel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fuel,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.imgPicture)
                .load(arrayFuel.get(position).getPicture())
                .into(holder.imgPicture);

        holder.txtName.setText(arrayFuel.get(position).getName());
        holder.txtPrice.setText("Đơn giá: " + arrayFuel.get(position).getPrice() + " ");
        holder.tvTotalLit.setText("Tổng Lít: " + (Double.parseDouble(arrayFuel.get(position).getEditTextValue())/ arrayFuel.get(position).getUnitprice()) + "lít");
        holder.edtMoney.setText(arrayFuel.get(position).getEditTextValue());
    }

    @Override
    public int getItemCount() {
        return arrayFuel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPicture;
        TextView txtName,txtPrice,tvTotalLit;
        EditText edtMoney;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            imgPicture = itemView.findViewById(R.id.imgPicture);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            edtMoney = itemView.findViewById(R.id.edtMoney);
            tvTotalLit = itemView.findViewById(R.id.tvTotalLit);

          edtMoney.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence s, int start, int count, int after) {

              }

              @SuppressLint("SetTextI18n")
              @Override
              public void onTextChanged(CharSequence s, int start, int before, int count) {
             //     arrayFuel.get(getAdapterPosition()).setEditTextValue(edtMoney.getText().toString());

                  CharSequence sp = "0"  +s;
                  Log.d("LOG_COUNT", "onTextChanged: " + sp  + " - " + start + " - " + before);
                  float number = Float.parseFloat(sp.toString());
                  Log.d("LOG_C", "onTextChanged: " + number  + " - " + start + " - " + before);

                  if(sp.length() < 0){
                      tvTotalLit.setText("0" +"lit");
                  }
                  else {
                      double many = (double) number/(double) arrayFuel.get(getAdapterPosition()).getUnitprice();
                      tvTotalLit.setText("Tổng lít : " + (double)Math.round((many)*100.0)/100.0 + " lit");
                      Log.d("LOG_G", "onTextChanged: " + Math.round(many*100.0/100.0)  + " - " + start + " - " + before);
                  }
              }

              @Override
              public void afterTextChanged(Editable s) {

              }
          });

//
//            imgRemove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (arrayFuel.get(getAdapterPosition()).getCount() > 0){
//                        arrayFuel.get(getAdapterPosition()).setCount(arrayFuel.get(getAdapterPosition()).getCount() - 1);
//                        notifyItemChanged(getAdapterPosition());
//                    }
//                }
//            });
//
//            imgAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (arrayFuel.get(getAdapterPosition()).getCount() < 99){
//                        arrayFuel.get(getAdapterPosition()).setCount(arrayFuel.get(getAdapterPosition()).getCount() + 1);
//                        notifyItemChanged(getAdapterPosition());
//                    }
//                }
//            });
        }
    }
}
