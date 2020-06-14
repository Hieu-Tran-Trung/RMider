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
import com.example.rmider.adapter.DiaryAdapter;
import com.example.rmider.model.Diary;
import com.example.rmider.utils.AccountUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {

    private Toolbar ToolbarDiary;
    private RecyclerView recyclerDiary;
    private DiaryAdapter diaryAdapter;
    private ArrayList<Diary> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initView();
        setUpToolBar();
        initRecycler();
        getData();

    }
    private void getData() {
        FirebaseDatabase.getInstance().getReference()
                .child("Diary")
                .child(AccountUtils.getInstance().getAccount().getPhoneNumber())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            arrayList.clear();
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                Diary diary = snapshot.getValue(Diary.class);
                                arrayList.add(diary);
                            }
                            diaryAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
    private void initRecycler() {
        recyclerDiary.setHasFixedSize(true);
        recyclerDiary.setLayoutManager(new LinearLayoutManager(this));
        diaryAdapter = new DiaryAdapter(arrayList);
        recyclerDiary.setAdapter(diaryAdapter);
    }
    private void initView() {
        ToolbarDiary  = findViewById(R.id.ToolbarDiary);
        recyclerDiary = findViewById(R.id.recyclerDiary);
    }
    private void setUpToolBar() {
        setSupportActionBar(ToolbarDiary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    public void addLocation(View view) {
        startActivity(new Intent(this,AddLoaction.class));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
