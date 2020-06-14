package com.example.rmider.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rmider.R;
import com.example.rmider.model.Diary;
import com.example.rmider.utils.AccountUtils;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditLocationActivity extends AppCompatActivity {
    private EditText edtTimeEdit, edtStartEdit, edtEndEdit;
    private Diary diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);
        initView();
        getData();
        setData();
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

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : Math.min(mon, 12);
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : Math.min(year, 2100);
                        cal.set(Calendar.YEAR, year);

                        day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = Math.max(sel, 0);
                    current = clean;
                    edtTimeEdit.setText(current);
                    edtTimeEdit.setSelection(Math.min(sel, current.length()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        edtTimeEdit.addTextChangedListener(tw);
    }

    private void setData() {
        edtEndEdit.setText(diary.getEnd());
        edtTimeEdit.setText(diary.getDate());
        edtStartEdit.setText(diary.getStart());
    }

    private void getData() {
        diary = (Diary) getIntent().getSerializableExtra("editlocation");
    }

    private void initView() {
        edtTimeEdit     = findViewById(R.id.edtTimeEdit);
        edtStartEdit    = findViewById(R.id.edtStartEdit);
        edtEndEdit      = findViewById(R.id.edtEndEdit);
    }

    public void onClickEditLocal(View view) {
        String time     = edtTimeEdit.getText().toString();
        String start    = edtStartEdit.getText().toString();
        String end      = edtEndEdit.getText().toString();

        diary.setDate(time);
        diary.setStart(start);
        diary.setEnd(end);

        FirebaseDatabase.getInstance().getReference()
                .child("Diary")
                .child(AccountUtils.getInstance().getAccount().getPhoneNumber())
                .child(diary.getKey())
                .setValue(diary, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(EditLocationActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditLocationActivity.this, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                });
    }
}
