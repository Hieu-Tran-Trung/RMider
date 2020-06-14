package com.example.rmider.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rmider.R;
import com.example.rmider.config.Constants;
import com.example.rmider.model.Account;
import com.example.rmider.utils.AccountUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edtPhoneNumber,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initLogin();
        initView();
        initData();
    }

    private void initLogin() {
        if (AccountUtils.getInstance().getAccount() != null){
            Intent intent = new Intent(getApplicationContext(),HouseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void initData() {
        Account account = AccountUtils.getInstance().getAccount();

        if (account == null){
            return;
        }

        edtPhoneNumber.setText(account.getPhoneNumber());
    }

    private void initView() {
        edtPhoneNumber  = findViewById(R.id.edtPhoneNumber);
        edtPassword     = findViewById(R.id.edtPassword);
    }

    public void onClickLogin(View view) {
        String phoneNumber = edtPhoneNumber.getText().toString();
        final String password = edtPassword.getText().toString();

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
            Toast.makeText(this, "Mật khẩu phải có tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase.getInstance().getReference()
                .child("Account")
                .child(phoneNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            Account account = dataSnapshot.getValue(Account.class);

                            // Kiểm tra mật khẩu có trùng hay không
                            if (account.getPassword().equals(password)){
                                startVerify(account);
                            }else {
                                Toast.makeText(LoginActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                            edtPassword.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(LoginActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startVerify(Account account) {
        Intent intent = new Intent(this, VerifyActivity.class);
        intent.putExtra(Constants.KEY_ACCOUNT, account);
        intent.putExtra(Constants.KEY_TYPE,Constants.TYPE_LOGIN);
        startActivity(intent);
    }

    public void onClickRegister(View view) {
        startActivity(new Intent(this, RegisterMyInforActivity.class));
    }
}
