package com.example.rmider.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rmider.R;
import com.example.rmider.model.Account;
import com.example.rmider.model.Car;
import com.example.rmider.utils.AccountUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserActivity extends AppCompatActivity {
    private ImageView imgAvt;
    private EditText tvSdt, tvNameUser, tvGender, tvCMND, tvLocal, tvCarInfo,tvDateBirthday;
    private Button updateUser;
    private RadioButton rbNam, rbNu;
    private Account account = AccountUtils.getInstance().getAccount();
    private int year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        initData();
        datePicker(tvDateBirthday);
        setData();
    }
    private void datePicker(final TextView editText) {
        final Calendar calendar = Calendar.getInstance();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                     editText.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
    private void setData() {
        updateUser.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                String sdt      = tvSdt.getText().toString();
                String username = tvNameUser.getText().toString();
                String ngaysinh = tvDateBirthday.getTransitionName().toString();
                String cmnd     = tvCMND.getText().toString();
                String local    = tvLocal.getText().toString();
                String informxe = tvCarInfo.getText().toString();

                Car mycar = account.getMyCar();
                mycar.setTechnicalInformation(informxe);
                account.setPhoneNumber(sdt);
                account.setName(username);
                account.setBirthday(ngaysinh);
                account.setCmnd(cmnd);
                account.setPosition(local);
                account.setMyCar(mycar);
                if (rbNam.isChecked()) { // nếu check là true
                    account.setGender(true); // setGender là true
                } else {
                    account.setGender(false); // ngược lại isCheck kia là false nghĩa là đang nhấn vào button nữ, set Gender là false
                }
                AccountUtils.getInstance().setAccount(account);
                Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        Car myCar = account.getMyCar();
        tvNameUser.setText(account.getName());
        tvSdt.setText(account.getPhoneNumber());
        tvDateBirthday.setText(account.getBirthday());
        tvCMND.setText(account.getCmnd());
        tvLocal.setText(account.getPosition());
        tvCarInfo.setText(myCar.getModelName());
        if (account.isGender()) { // kiểm tra để Hiện lên , true là setcheck cho nam. false thì setCheck cho nữ hiện lên
            rbNam.setChecked(true);
        } else {
            rbNu.setChecked(true);
        }
    }

    private void initView() {
        rbNam = findViewById(R.id.rbNam);
        rbNu = findViewById(R.id.rbNu);
        updateUser = findViewById(R.id.updateUser);
        imgAvt = findViewById(R.id.imgAvt);
        tvSdt = findViewById(R.id.tvSdt);
        tvNameUser = findViewById(R.id.tvNameUser);
        tvDateBirthday = findViewById(R.id.tvDateBirthday);
        tvCMND = findViewById(R.id.tvCMND);
        tvLocal = findViewById(R.id.tvLocal);
        tvCarInfo = findViewById(R.id.tvCarInfo);

    }
}
