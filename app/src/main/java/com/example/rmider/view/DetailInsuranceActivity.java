package com.example.rmider.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.rmider.R;
import com.example.rmider.config.Constants;
import com.example.rmider.model.Insurance;
import com.example.rmider.utils.AccountUtils;
import com.example.rmider.utils.DateUtils;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class DetailInsuranceActivity extends AppCompatActivity {

    private TextView tvLoaiBh;
    private EditText tvDatePicker;
    private Spinner spnDatePicker;
    private ImageView imgLoaiBh;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_damage_coverage);

        initView();
        getType();
        chageDate();
    }
    private void chageDate() {
        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : Math.min(mon, 12);
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900: Math.min(year, 2100);
                        cal.set(Calendar.YEAR, year);

                        day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }
                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = Math.max(sel, 0);
                    current = clean;
                    tvDatePicker.setText(current);
                    tvDatePicker.setSelection(Math.min(sel, current.length()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        tvDatePicker.addTextChangedListener(tw);
    }
    @SuppressLint("SetTextI18n")
    private void getType() {
        type = getIntent().getIntExtra("type",0);
        switch (type){
            case Constants.TYPE_VATCHAT:
                Glide.with(this)
                        .load(R.drawable.ic_sec_1)
                        .into(imgLoaiBh);
                tvLoaiBh.setText("Bảo hiểm vật chất");
                break;
            case Constants.TYPE_BATBUOC:
                Glide.with(this)
                        .load(R.drawable.ic_sec2_64dp)
                        .into(imgLoaiBh);
                tvLoaiBh.setText("Bảo hiểm bắt buộc");
                break;
            case Constants.TYPE_DANGKIEM:
                Glide.with(this)
                        .load(R.drawable.ic_shield_64dp)
                        .into(imgLoaiBh);
                tvLoaiBh.setText("Đăng Kiểm");
                break;
            case Constants.TYPE_LOAIKHAC:
                Glide.with(this)
                        .load(R.drawable.ic_sec3_64dp)
                        .into(imgLoaiBh);
                tvLoaiBh.setText("Bảo hiểm Loại khác");
                break;
        }
    }

    private void initView() {
        tvDatePicker    =   findViewById(R.id.tvDatePicker);
        spnDatePicker   =   findViewById(R.id.spnDatePicker);
        tvLoaiBh        =   findViewById(R.id.tvLoaiBh);
        imgLoaiBh       =   findViewById(R.id.imgLoaiBh);
    }

    public void onClickCancel(View view) {
        finish();
    }

    public void onClickUpdate(View view) {
        String date = tvDatePicker.getText().toString();

        if (!TextUtils.isEmpty(date)){
            long time = System.currentTimeMillis();
            long timeAlarm = DateUtils.getTimeMilli(date);
            if (timeAlarm < 0){
                Toast.makeText(this, "Định dạng ngày sai", Toast.LENGTH_SHORT).show();
                return;
            }
            Insurance insurance = new Insurance();
            insurance.setTimeCreate(time);
            insurance.setType(type);
            insurance.setAlarm(timeAlarm);
            insurance.setNexttime(spnDatePicker.getSelectedItem().toString());

            FirebaseDatabase.getInstance().getReference()
                    .child("Insurance")
                    .child(AccountUtils.getInstance().getAccount().getPhoneNumber())
                    .child(time + "")
                    .setValue(insurance, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError==null){
                                Toast.makeText(DetailInsuranceActivity.this, "Update thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DetailInsuranceActivity.this, "Update thất bại", Toast.LENGTH_SHORT).show();
                            }

                            finish();
                        }
                    });
        }
    }
}
