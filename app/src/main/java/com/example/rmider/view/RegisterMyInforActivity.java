package com.example.rmider.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rmider.R;
import com.example.rmider.config.Constants;
import com.example.rmider.model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterMyInforActivity extends AppCompatActivity {

    private EditText edtPhoneNumber,edtPassword,edtName,edtBirthday,edtPosition,edtCMND;
    private RadioButton rbMale,fbFemale;
    private int year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_my_infor);
        initView();
        //datePicker(edtBirthday);
        chageDate();
    }

    private void datePicker(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year    = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterMyInforActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

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
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
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
                    edtBirthday.setText(current);
                    edtBirthday.setSelection(Math.min(sel, current.length()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        edtBirthday.addTextChangedListener(tw);
    }


    private void initView() {
        edtPhoneNumber  = findViewById(R.id.edtPhoneNumber);
        edtPassword     = findViewById(R.id.edtPassword);
        edtName         = findViewById(R.id.edtName);
        edtBirthday     = findViewById(R.id.edtBirthday);
        edtPosition     = findViewById(R.id.edtPosition);
        rbMale          = findViewById(R.id.rbMale);
        fbFemale        = findViewById(R.id.fbFemale);
        edtCMND         = findViewById(R.id.edtCMND);

    }


    public void onClickNext(View view) {
        final String phoneNumber  = edtPhoneNumber.getText().toString();
        final String password     = edtPassword.getText().toString();
        final String name         = edtName.getText().toString();
        final String birthday     = edtBirthday.getText().toString();
        final String position     = edtPosition.getText().toString();
        final String cmnd         = edtCMND.getText().toString();

        if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this, "Bạn chưa điền số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phoneNumber.length() != 10){
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Bạn chưa điền mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6){
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Bạn chưa điền họ tên", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(birthday)){
            Toast.makeText(this, "Bạn chưa điền ngày sinh", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(position)){
            Toast.makeText(this, "Bạn chưa điền địa chỉ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(cmnd)){
            Toast.makeText(this, "Bạn chưa điền địa chỉ", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase.getInstance().getReference()
                .child("Account")
                .child(phoneNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            Toast.makeText(getApplicationContext(), "Số điện thoại đã được đăng ký", Toast.LENGTH_SHORT).show();
                        }else {
                            Account account = new Account();
                            account.setPhoneNumber(phoneNumber);
                            account.setPassword(password);
                            account.setName(name);
                            account.setBirthday(birthday);
                            account.setPosition(position);
                            account.setGender(rbMale.isChecked());
                            account.setCmnd(cmnd);

                            Intent intent = new Intent(getApplicationContext(),RegisterMyAvatarActivity.class);
                            intent.putExtra(Constants.KEY_ACCOUNT,account);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
