package com.example.rmider.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rmider.R;
import com.example.rmider.model.Maintain;
import com.example.rmider.utils.AccountUtils;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class EditMaintain extends AppCompatActivity {
    private EditText edtDateEdit,edtDetailEdit,edtKmEdit,edtPriceEdit,edtNextDateEdit;
    private Toolbar toolbarEdit;
    private Maintain maintain;
    private ArrayList<Maintain> m = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_maintain);
        initView();
        setUpToolbar();
        getData();
        setData();
        chageDate();
    }
    private void chageDate() {
        TextWatcher tw = new TextWatcher() {
            private String current  = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal    = Calendar.getInstance();
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
                    edtDateEdit.setText(current);
                    edtDateEdit.setSelection(Math.min(sel, current.length()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        edtDateEdit.addTextChangedListener(tw);
    }

    private void setData() {
        edtDateEdit.setText(maintain.getNgay());
        edtDetailEdit.setText(maintain.getDetail());
        edtKmEdit.setText(maintain.getSoKm());
        edtPriceEdit.setText(maintain.getPrice());
        edtNextDateEdit.setText(maintain.getNgaytt());

    }

    private void getData() {
       maintain = (Maintain) getIntent().getSerializableExtra("key");
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbarEdit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    private void initView() {
        toolbarEdit     = findViewById(R.id.toolbarEdit);
        edtDateEdit     = findViewById(R.id.edtDateEdit);
        edtDetailEdit   = findViewById(R.id.edtDetailEdit);
        edtKmEdit       = findViewById(R.id.edtKmEdit);
        edtPriceEdit    = findViewById(R.id.edtPriceEdit);
        edtNextDateEdit = findViewById(R.id.edtNextDateEdit);
    }

    public void onClickEdit(View view) {
        String day      = edtDateEdit.getText().toString();
        String detail   = edtDetailEdit.getText().toString();
        String km       = edtKmEdit.getText().toString();
        String price    = edtPriceEdit.getText().toString();
        String nextDay  = edtNextDateEdit.getText().toString();
        maintain.setNgay(day);
        maintain.setDetail(detail);
        maintain.setSoKm(km);
        maintain.setPrice(price);
        maintain.setNgaytt(nextDay);

        FirebaseDatabase.getInstance().getReference()
                .child("Maintain")
                .child(AccountUtils.getInstance().getAccount().getPhoneNumber())
                .child(maintain.getKey())
                .setValue(maintain, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError !=null){
                            Toast.makeText(EditMaintain.this, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(EditMaintain.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                });
    }
}
