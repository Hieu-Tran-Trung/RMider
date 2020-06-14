package com.example.rmider.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rmider.R;
import com.example.rmider.adapter.InformInsuranceAdapter;
import com.example.rmider.adapter.InsuaraceAdapter;
import com.example.rmider.model.Account;
import com.example.rmider.model.Car;
import com.example.rmider.model.Insurance;
import com.example.rmider.utils.AccountUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class InsurranceActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_IMAGE = 123;

    private TextView tvName,tvMauXe,tvLoaiXe,tvColor,tvBienso,tvStartDayBh,tvEndDayBh,tvNgayDk,tvNgayNextDk;
    private CircleImageView imgAvatar;
    private RecyclerView recyclerTimes;
    private RecyclerView recyclerInsurance;
    private Account account = AccountUtils.getInstance().getAccount();
    private InsuaraceAdapter insuaraceAdapter;
    private ArrayList<Insurance> arrayInsurance = new ArrayList<>();
    private ProgressDialog progressDialog;
    private InformInsuranceAdapter adapterinsurance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurrance);

        initView();
        setData();
        initData();
        initRecycler();
    }

    private void initRecycler() {
        recyclerTimes.setHasFixedSize(true);
        recyclerTimes.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        insuaraceAdapter = new InsuaraceAdapter(arrayInsurance);
        recyclerTimes.setAdapter(insuaraceAdapter);

        recyclerInsurance.setHasFixedSize(true);
        recyclerInsurance.setLayoutManager(new LinearLayoutManager(this));
        adapterinsurance = new InformInsuranceAdapter(arrayInsurance);
        recyclerInsurance.setAdapter(adapterinsurance);
    }

    private void initData() {
        FirebaseDatabase.getInstance().getReference()
                .child("Insurance")
                .child(AccountUtils.getInstance().getAccount().getPhoneNumber())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (dataSnapshot.exists()){
                            arrayInsurance.add(dataSnapshot.getValue(Insurance.class));
                            adapterinsurance.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    }
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        tvName.setText(account.getName());
        Car myCar = account.getMyCar();
        Glide.with(this)
                .load(myCar.getPicture())
                .error(R.drawable.ic_motor_256dp)
                .centerCrop()
                .into(imgAvatar);

        tvMauXe.setText("Mẫu xe: "+myCar.getModelName());
        tvLoaiXe.setText("Loại xe: "+myCar.getType());
        tvColor.setText("Màu sắc: "+myCar.getColor());
        tvBienso.setText("Biển số: "+myCar.getLicensePlates());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.setCancelable(false);
    }

    private void initView() {
        imgAvatar   =   findViewById(R.id.imgAvatar);
        tvName      =   findViewById(R.id.tvName);
        tvMauXe     =   findViewById(R.id.tvMauXe);
        tvLoaiXe    =   findViewById(R.id.tvLoaiXe);
        tvColor     =   findViewById(R.id.tvColor);
        tvBienso    =   findViewById(R.id.tvBienso);
        tvStartDayBh=   findViewById(R.id.tvStartDayBh);
        tvEndDayBh  =   findViewById(R.id.tvEndDayBh);
        tvNgayDk    =   findViewById(R.id.tvNgayDk);
        tvNgayNextDk  =   findViewById(R.id.tvNgayNextDk);
        recyclerTimes = findViewById(R.id.recyclerTimes);
        recyclerInsurance = findViewById(R.id.recyclerInsurance);
    }

    public void clickNewInsurance(View view) {
        startActivity(new Intent(this,InformInsuranceActivity.class));
    }

    public void onClickAvatar(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null){

            Glide.with(this)
                    .load(data.getData())
                    .into(imgAvatar);

            uploadAvatar(data.getData());
        }
    }

    private void uploadAvatar(Uri uri) {
        progressDialog.show();

        FirebaseStorage.getInstance().getReference().child("Image").child(System.currentTimeMillis() + ".png").putFile(uri)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "Tải lên hình ảnh thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        progressDialog.cancel();
                        account.getMyCar().setPicture(task.getResult().getDownloadUrl().toString());
                        AccountUtils.getInstance().setAccount(account);
                        Log.d("bbjhbhjh",task.getResult().getDownloadUrl().toString());
                    }
                });
    }
}
