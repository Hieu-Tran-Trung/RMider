package com.example.rmider.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rmider.R;
import com.example.rmider.config.Constants;
import com.example.rmider.model.Account;
import com.example.rmider.utils.AccountUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

// Activity này sẽ dùng để xác thực OTP từ màn hình login hoặc màn hình register
public class VerifyActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private EditText edtVerifyCode;

    private FirebaseAuth firebaseAuth;
    private Account account;
    private int type;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        initView();
        initData();
    }

    private void initData() {
        account = (Account) getIntent().getSerializableExtra(Constants.KEY_ACCOUNT);
        type = getIntent().getIntExtra(Constants.KEY_TYPE,0);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.setCancelable(false);

        sentOTP();
    }

    private void sentOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+84" + account.getPhoneNumber(),
                60,
                TimeUnit.SECONDS,
                this,
                otpCallback);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks otpCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        // Hàm này sẽ đc chạy khi Sms đc gửi về thiết bị hiện tại
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            edtVerifyCode.setText(phoneAuthCredential.getSmsCode());
        }

        // Hàm này sẽ chạy khi Sms đc gửi
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;
            Toast.makeText(getApplicationContext(), "Đã gửi mã xác thực", Toast.LENGTH_SHORT).show();
        }

        // Hàm này sẽ chạy khi Sms gửi thất bại
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(getApplicationContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                finish();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                new AlertDialog.Builder(VerifyActivity.this)
                        .setTitle("Cảnh Báo")
                        .setMessage("Bạn đã yêu cầu gửi mã OTP vượt quá số lần cho phép. Vui lòng thử lại sau hoặc hãy thử lại với số điện thoại khác !")
                        .setCancelable(false)
                        .setPositiveButton("Trở về", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            }

            Toast.makeText(getApplicationContext(), "Gửi mã xác thực thất bại", Toast.LENGTH_SHORT).show();
        }
    };

    private void signPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        progressDialog.show();
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (type == Constants.TYPE_REGISTER){
                                registerAccount();
                            }else if (type == Constants.TYPE_LOGIN){
                                startUse();
                            }

                        } else {
                            progressDialog.cancel();
                            Toast.makeText(getApplicationContext(), "Mã xác thực không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void registerAccount() {
        FirebaseDatabase.getInstance().getReference()
                .child("Account")
                .child(account.getPhoneNumber())
                .setValue(account,
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError == null){
                                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                                    startUse();
                                }else {
                                    progressDialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }

    private void startUse() {
        // Lưu đối tượng account
        AccountUtils.getInstance().setAccount(account);

        progressDialog.cancel();

        // Mở màn hình chính
        Intent intent = new Intent(getApplicationContext(),HouseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void initView() {
        edtVerifyCode = findViewById(R.id.edtVerifyCode);
    }

    public void onClickVerify(View view) {
        String verifyCode = edtVerifyCode.getText().toString();

        if (TextUtils.isEmpty(verifyCode)){
            Toast.makeText(this, "Bạn chưa điền mã xác thực", Toast.LENGTH_SHORT).show();
            return;
        }

        if (verifyCode.length() != 6){
            Toast.makeText(this, "Mã xác thực không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verifyCode);
        signPhoneAuthCredential(credential);
    }
}
