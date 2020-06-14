package com.example.rmider.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rmider.R;
import com.example.rmider.adapter.AssistanceAdapter;
import com.example.rmider.callback.OnItemClickAssis;
import com.example.rmider.model.Assis;

import java.util.ArrayList;

public class AssistanceActivity extends AppCompatActivity implements OnItemClickAssis {

    private EditText edtPhoneHelp;
    private RecyclerView rListPhoneHelp;
    private Toolbar toolbar;
    private ArrayList<Assis> assisArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistance);

        initView();
        setupToolbar();
        createDemo();
        setRecycleview();
    }

    public void onClickCall(View view){
        String phoneNo = edtPhoneHelp.getText().toString();

        if(TextUtils.isEmpty(phoneNo)) {
            Toast.makeText(AssistanceActivity.this, "Nhập số điện thoại trợ giúp", Toast.LENGTH_SHORT).show();
            return;
        }

        String dial = "tel:" + phoneNo;
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
    }

    private void setRecycleview() {
        rListPhoneHelp.setHasFixedSize(true);
        rListPhoneHelp.setLayoutManager(new LinearLayoutManager(this));
        AssistanceAdapter assistanceAdapter = new AssistanceAdapter(assisArrayList, this);
        rListPhoneHelp.setAdapter(assistanceAdapter);
    }

    private void createDemo() {
        assisArrayList.add(new Assis("Honda",R.drawable.ic_honda_64dp,"1800 8001"));
        assisArrayList.add(new Assis("Yamaha",R.drawable.ic_yamaha_64dp,"(84.24)38855080"));
        assisArrayList.add(new Assis("Suzuki",R.drawable.ic_suzuki_64dp,"1900 6571"));
        assisArrayList.add(new Assis("Ducati",R.drawable.ic_ducati_64dp,"0946 795 899"));

    }

    private void setupToolbar() {
       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }


    private void initView() {
        toolbar         = findViewById(R.id.toolbar);
        edtPhoneHelp    = findViewById(R.id.edtPhoneHelp);
        rListPhoneHelp  = findViewById(R.id.rListPhoneHelp);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(String phone) {
        edtPhoneHelp.setText(phone);
    }
}
