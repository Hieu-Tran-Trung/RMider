package com.example.rmider.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rmider.R;
import com.example.rmider.config.Constants;
import com.example.rmider.model.Account;
import com.example.rmider.model.Car;
import com.example.rmider.utils.AccountUtils;

public class DetailCarActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PICTURE = 2;

    private Uri uri;

    private ImageView imgCar;
    private EditText txtName, txtTypeBike, txtColor, txtLicensePlates, txtNumberFrames, txtInformation;
    private ProgressDialog progressDialog;
    private RadioButton rbMotoEdit, rbOtoEdit;

    private Account account = AccountUtils.getInstance().getAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_car);

        initView();
        initUI();
    }

    private void initUI() {
        Car myCar = account.getMyCar();

        txtName.setText(myCar.getModelName());
        txtColor.setText(myCar.getColor());
        txtLicensePlates.setText(myCar.getLicensePlates());
        txtNumberFrames.setText(myCar.getNumberFrames());
        txtInformation.setText(myCar.getTechnicalInformation());

        if (myCar.getType() == Constants.TYPE_MOTO) {
            rbMotoEdit.setChecked(true);
        } else {
            rbOtoEdit.setChecked(true);
        }
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        imgCar = findViewById(R.id.imgCar);
        txtName = findViewById(R.id.txtName);
        rbMotoEdit = findViewById(R.id.rbMotoEdit);
        rbOtoEdit = findViewById(R.id.rbOtoEdit);
        txtColor = findViewById(R.id.txtColor);
        txtLicensePlates = findViewById(R.id.txtLicensePlates);
        txtNumberFrames = findViewById(R.id.txtNumberFrames);
        txtInformation = findViewById(R.id.txtInformation);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.setCancelable(false);
    }

    public void onClickUpdate(View view) {
        Car mycar       = account.getMyCar();
        String mau      = txtName.getText().toString();
        String color    = txtColor.getText().toString();
        String bienso   = txtLicensePlates.getText().toString();
        String sokhung  = txtNumberFrames.getText().toString();
        String info     = txtInformation.getText().toString();

        mycar.setModelName(mau);
        mycar.setColor(color);
        mycar.setLicensePlates(bienso);
        mycar.setNumberFrames(sokhung);
        mycar.setTechnicalInformation(info);
        if (rbMotoEdit.isChecked()) {
            mycar.setType(Constants.TYPE_MOTO);
        } else {
            mycar.setType(Constants.TYPE_OTO);
        }
        account.setMyCar(mycar);
        AccountUtils.getInstance().setAccount(account);
        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
    }
}
