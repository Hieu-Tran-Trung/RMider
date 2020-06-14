package com.example.rmider.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rmider.R;
import com.example.rmider.config.Constants;

public class InformInsuranceActivity extends AppCompatActivity {

    private CheckBox cbVatchat,cbBatBuoc,cbDangKiem,cbLoaiKhac;
    private Toolbar toolbarInsure;
    private int typeInsurance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform_insurance);
        initView();
        setUpToolbar();
        setEvent();
    }

    private void setEvent() {
        cbVatchat.setOnCheckedChangeListener(onCheckedChangeListener);
        cbBatBuoc.setOnCheckedChangeListener(onCheckedChangeListener);
        cbDangKiem.setOnCheckedChangeListener(onCheckedChangeListener);
        cbLoaiKhac.setOnCheckedChangeListener(onCheckedChangeListener);

    }

    private void setUpToolbar() {
        setSupportActionBar(toolbarInsure);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                switch (buttonView.getId()){
                    case R.id.cbVatchat:
                        typeInsurance = Constants.TYPE_VATCHAT;
                        cbBatBuoc.setChecked(false);
                        cbDangKiem.setChecked(false);
                        cbLoaiKhac.setChecked(false);
                        break;
                    case R.id.cbBatBuoc:
                        typeInsurance = Constants.TYPE_BATBUOC;
                        cbVatchat.setChecked(false);
                        cbDangKiem.setChecked(false);
                        cbLoaiKhac.setChecked(false);
                        break;
                    case R.id.cbDangKiem:
                        typeInsurance = Constants.TYPE_DANGKIEM;
                        cbBatBuoc.setChecked(false);
                        cbVatchat.setChecked(false);
                        cbLoaiKhac.setChecked(false);
                        break;
                    case R.id.cbLoaiKhac:
                        typeInsurance = Constants.TYPE_LOAIKHAC;
                        cbBatBuoc.setChecked(false);
                        cbDangKiem.setChecked(false);
                        cbVatchat.setChecked(false);
                        break;
                }

            }
        }
    };

    private void initView() {
        cbVatchat       =   findViewById(R.id.cbVatchat);
        cbBatBuoc       =   findViewById(R.id.cbBatBuoc);
        cbDangKiem      =   findViewById(R.id.cbDangKiem);
        cbLoaiKhac      =   findViewById(R.id.cbLoaiKhac);
        toolbarInsure   =   findViewById(R.id.toolbarInsure);

    }

    public void clickNewInform(View view) {
        if(typeInsurance != 0) {
            Intent intent1 = new Intent(this, DetailInsuranceActivity.class);
            intent1.putExtra("type", typeInsurance);
            startActivity(intent1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
