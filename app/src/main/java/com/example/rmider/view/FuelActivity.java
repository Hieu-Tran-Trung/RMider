package com.example.rmider.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmider.R;
import com.example.rmider.adapter.FuelAdapter;
import com.example.rmider.model.Fuel;

import java.util.ArrayList;

public class FuelActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FuelAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Fuel> arrayFuel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);

        initView();
        setupToolbar();
        initData();
        initRecycler();
    }

    private void initData() {
        arrayFuel.add(new Fuel("Xăng RON 95-IV",R.drawable.ic_ron95_iv,17240,17240,"23989"));
        arrayFuel.add(new Fuel("Xăng RON 95-III",R.drawable.ic_ron95_iii,17140,17140,"17140"));
        arrayFuel.add(new Fuel("E5 RON 92-II",R.drawable.ic_e5_ron92_ii,16370,16370,"16370"));
        arrayFuel.add(new Fuel("DO 0,001S-V",R.drawable.ic_do_001_s_v,13590,13590,"13590"));
        arrayFuel.add(new Fuel("DO 0,05S-II",R.drawable.ic_do_05s,13290,13290,"13290"));
        arrayFuel.add(new Fuel("Dầu hỏa",R.drawable.ic_dau_hoa,12070,12070,"12070"));
    }

    private void initRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FuelAdapter(arrayFuel);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        toolbar         = findViewById(R.id.toolbar);
        recyclerView    = findViewById(R.id.recyclerView);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
