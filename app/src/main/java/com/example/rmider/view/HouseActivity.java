package com.example.rmider.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmider.R;
import com.example.rmider.adapter.ItemMainAdapter;
import com.example.rmider.callback.OnItemClickListener;
import com.example.rmider.utils.AccountUtils;

import static com.example.rmider.config.Constants.TYPE_ASSISTANCE_HELP;
import static com.example.rmider.config.Constants.TYPE_DIARY;
import static com.example.rmider.config.Constants.TYPE_FUEL_INFORMATION;
import static com.example.rmider.config.Constants.TYPE_INSURRANCE;
import static com.example.rmider.config.Constants.TYPE_REPAIR_LOCATION;
import static com.example.rmider.config.Constants.TYPE_TRAFFIC_LAWS;
import static com.example.rmider.config.Constants.TYPE_USER;
import static com.example.rmider.config.Constants.TYPE_VEHICLE_INFORMATION;
import static com.example.rmider.config.Constants.TYPE_VEHICLE_MAINTENANCE;

public class HouseActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView recyclerItems;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        initView();
        initUI();
    }

    public void onclick(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initUI() {
        setSupportActionBar(toolbar);
        recyclerItems.setHasFixedSize(true);
        recyclerItems.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));
        ItemMainAdapter adapter = new ItemMainAdapter(this);
        recyclerItems.setAdapter(adapter);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        recyclerItems = findViewById(R.id.recyclerItems);
    }

    @Override
    public void onItems(int type) {
        switch (type) {
            case TYPE_VEHICLE_INFORMATION:
                startActivity(new Intent(this, DetailCarActivity.class));
                break;
            case TYPE_VEHICLE_MAINTENANCE:
                startActivity(new Intent(this, MaintenanceActivity.class));
                break;
            case TYPE_INSURRANCE:
                startActivity(new Intent(this, InsurranceActivity.class));
                break;
            case TYPE_DIARY:
                startActivity(new Intent(this, DiaryActivity.class));
                break;
            case TYPE_TRAFFIC_LAWS:
                startActivity(new Intent(this, LawActivity.class));
                break;
            case TYPE_FUEL_INFORMATION:
                startActivity(new Intent(this, FuelActivity.class));
                break;
            case TYPE_REPAIR_LOCATION:
                startActivity(new Intent(this, RepairLocationActivity.class));
                break;
            case TYPE_ASSISTANCE_HELP:
                startActivity(new Intent(this, AssistanceActivity.class));
                break;
            case TYPE_USER:
                startActivity(new Intent(this, UserActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClickLogout(MenuItem item) {
        AccountUtils.getInstance().logout();
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
