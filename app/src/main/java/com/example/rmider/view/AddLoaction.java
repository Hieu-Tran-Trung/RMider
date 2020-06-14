package com.example.rmider.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rmider.R;
import com.example.rmider.model.Diary;
import com.example.rmider.utils.AccountUtils;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddLoaction extends AppCompatActivity {

    private EditText  edtStart, edtEnd;
    private TextView edtDate;
    private int year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loaction);

        initView();
        datePicker(edtDate);
    }

    private void datePicker(final TextView textView) {
        final Calendar calendar = Calendar.getInstance();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddLoaction.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textView.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
    private void initView() {
        edtDate    = findViewById(R.id.edtDate);
        edtStart   = findViewById(R.id.edtStart);
        edtEnd     = findViewById(R.id.edtEnd);
    }
    public void onClickAddLocal(View view) {
        String date = edtDate.getText().toString();
        String Start = edtStart.getText().toString();
        String End = edtEnd.getText().toString();

        if (TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Bạn chưa điền thời gian", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Start)) {
            Toast.makeText(this, "Bạn chưa nhập địa điểm đi", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(End)) {
            Toast.makeText(this, "Bạn chưa nhập địa điểm đến", Toast.LENGTH_SHORT).show();
            return;
        }

        String key = System.currentTimeMillis() + "";

        Diary diary = new Diary();
        diary.setDate(date);
        diary.setStart(Start);
        diary.setEnd(End);
        diary.setKey(key);

        FirebaseDatabase.getInstance().getReference()
                .child("Diary")
                .child(AccountUtils.getInstance().getAccount().getPhoneNumber())
                .child(key)
                .setValue(diary, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(AddLoaction.this, "Update  Thành Công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddLoaction.this, "Update Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                });
    }
}
