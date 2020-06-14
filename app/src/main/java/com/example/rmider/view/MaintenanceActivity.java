package com.example.rmider.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmider.R;
import com.example.rmider.adapter.MaintainAdapter;
import com.example.rmider.model.Maintain;
import com.example.rmider.utils.AccountUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MaintenanceActivity extends AppCompatActivity {

    private RecyclerView   rycMaintain;
    private Toolbar toolbarMaintain;
    private ArrayList<Maintain> arrmaintain = new ArrayList<>();
    private MaintainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        
        initView();
        setupToolbar();
        initRecyclerview();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData() {
        FirebaseDatabase.getInstance().getReference()
                .child("Maintain")
                .child(AccountUtils.getInstance().getAccount().getPhoneNumber())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            arrmaintain.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                Maintain maintain = snapshot.getValue(Maintain.class);
                                arrmaintain.add(maintain);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void initRecyclerview() {
        rycMaintain.setHasFixedSize(true);
        rycMaintain.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MaintainAdapter(arrmaintain);
        rycMaintain.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    
    public void onClickAdd (View view) {
        startActivity(new Intent(getApplicationContext(),AddScheduleActivity.class));
    }

    private void setupToolbar() {
        setSupportActionBar(toolbarMaintain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    private void initView() {
        rycMaintain     = findViewById(R.id.rycMaintain);
        toolbarMaintain = findViewById(R.id.toolbarMaintain);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
