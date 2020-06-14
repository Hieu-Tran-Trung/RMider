package com.example.rmider.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rmider.R;
import com.example.rmider.callback.OnItemClickListener;
import com.example.rmider.config.Constants;
import com.example.rmider.model.Items;
import com.example.rmider.utils.Utils;

import java.util.ArrayList;

public class ItemMainAdapter extends RecyclerView.Adapter<ItemMainAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private ArrayList<Items> arrayItems;

    public ItemMainAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

        arrayItems = new ArrayList<>();
        arrayItems.add(new Items("Thông tin phương tiện",R.drawable.ic_vehicle_information_128dp, Constants.TYPE_VEHICLE_INFORMATION));
        arrayItems.add(new Items("Bảo dưỡng phương tiện",R.drawable.ic_vehicle_maintenance_128dp, Constants.TYPE_VEHICLE_MAINTENANCE));
        arrayItems.add(new Items("Bảo hiểm và Đăng kiểm",R.drawable.ic_insurrance_128dp, Constants.TYPE_INSURRANCE));
        arrayItems.add(new Items("Nhật ký di chuyển",R.drawable.ic_diary_128dp, Constants.TYPE_DIARY));
        arrayItems.add(new Items("Tra cứu luật giao thông",R.drawable.ic_traffic_log_128dp, Constants.TYPE_TRAFFIC_LAWS));
        arrayItems.add(new Items("Thông tin nhiên liệu",R.drawable.ic_price_128dp, Constants.TYPE_FUEL_INFORMATION));
        arrayItems.add(new Items("Địa điểm sữa chữa",R.drawable.ic_location_128dp, Constants.TYPE_REPAIR_LOCATION));
        arrayItems.add(new Items("Trợ giúp sự cố",R.drawable.ic_help_128dp, Constants.TYPE_ASSISTANCE_HELP));
        arrayItems.add(new Items("Thông tin người dùng",R.drawable.ic_user_128dp,Constants.TYPE_USER));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.imgIcon)
                .load(arrayItems.get(position).getImage())
                .into(holder.imgIcon);

        holder.txtTitle.setText(arrayItems.get(position).getTitle());  // chạy 50 lần , mỗi thằng onbind đại diện cho 1 item
    }

    @Override
    public int getItemCount() {
        return arrayItems.size(); //50
    }

    class ViewHolder extends RecyclerView.ViewHolder{  // tương tự thằng onbind , chứa và khởi tạo các view

        ImageView imgIcon;
        TextView txtTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgIcon     = itemView.findViewById(R.id.imgIcon);
            txtTitle    = itemView.findViewById(R.id.txtTitle);

            itemView.setLayoutParams(new ViewGroup.LayoutParams(Utils.getScreenWidth()/3,Utils.getScreenWidth()/3));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItems(arrayItems.get(getAdapterPosition()).getType());
//                    ((HouseActivity)v.getContext()).onclick("hello world");
                }
    });
        }
    }
}
