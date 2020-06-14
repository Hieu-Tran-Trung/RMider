package com.example.rmider.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rmider.R;
import com.example.rmider.config.Constants;
import com.example.rmider.model.Account;
import com.example.rmider.model.Car;

public class RegisterMyCarActivity extends AppCompatActivity {

    private EditText edtName,edtColor,edtLicensePlates,edtNumberFrames,edtTechnicalInformation;
    private RadioButton rbMoto,rbOto;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_my_car);

        initView();
        initData();
    }

    private void initData() {
        account = (Account) getIntent().getSerializableExtra(Constants.KEY_ACCOUNT);
    }

    private void initView() {
        edtName = findViewById(R.id.edtName);
        edtColor = findViewById(R.id.edtColor);
        edtLicensePlates = findViewById(R.id.edtLicensePlates);
        edtNumberFrames = findViewById(R.id.edtNumberFrames);
        edtTechnicalInformation = findViewById(R.id.edtTechnicalInformation);
        rbMoto = findViewById(R.id.rbMoto);
        rbOto = findViewById(R.id.rbOto);
    }

    public void onClickRegister(View view) {
        final String nameModel      = edtName.getText().toString();
        final String color          = edtColor.getText().toString();
        final String licensePlates  = edtLicensePlates.getText().toString();
        final String numberFrames   = edtNumberFrames.getText().toString();
        final String technicalInfor = edtTechnicalInformation.getText().toString();

        if (TextUtils.isEmpty(nameModel)){
            Toast.makeText(this, "Bạn chưa điền mẫu xe", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(color)){
            Toast.makeText(this, "Bạn chưa điền màu sắc", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(licensePlates)){
            Toast.makeText(this, "Bạn chưa điền biển kiểm soát", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(numberFrames)){
            Toast.makeText(this, "Bạn chưa điền khung số", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(technicalInfor)){
            Toast.makeText(this, "Bạn chưa điền thông tin kỹ thuật", Toast.LENGTH_SHORT).show();
            return;
        }

        Car car = new Car();
        car.setModelName(nameModel);
        car.setColor(color);
        car.setLicensePlates(licensePlates);
        car.setNumberFrames(numberFrames);
        car.setTechnicalInformation(technicalInfor);

        if (rbMoto.isChecked()){
            car.setType(Constants.TYPE_MOTO);
        }else {
            car.setType(Constants.TYPE_OTO);
        }

        account.setMyCar(car);

        Intent intent = new Intent(getApplicationContext(),VerifyActivity.class);
        intent.putExtra(Constants.KEY_ACCOUNT,account);
        intent.putExtra(Constants.KEY_TYPE,Constants.TYPE_REGISTER);
        startActivity(intent);
    }

    public void onClickCarImage(View view) {
    }
}
