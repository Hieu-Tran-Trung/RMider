package com.example.rmider.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rmider.R;

public class RegistryActivity extends AppCompatActivity {
    private TextView tvDateDk;
    private Spinner spnDateDk;
    private Button btnCancelDk,btnUpdateDk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        initView();
    }

    private void initView() {
        tvDateDk    =   findViewById(R.id.tvDateDk);
        spnDateDk   =   findViewById(R.id.spnDateDk);
        btnCancelDk =   findViewById(R.id.btnCancelDk);
        btnUpdateDk =   findViewById(R.id.btnUpdateDk);
    }
}
