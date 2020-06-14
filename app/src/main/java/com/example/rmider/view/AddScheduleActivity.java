package com.example.rmider.view;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rmider.R;
import com.example.rmider.model.Maintain;
import com.example.rmider.utils.AccountUtils;
import com.example.rmider.utils.AlarmUtils;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class AddScheduleActivity extends AppCompatActivity {

    private EditText edtDate,edtDetailMaintaine,edtKm,edtPrice,edtNextDate;
    private RadioButton rbNotify;
    private ArrayList<Maintain> arrayList = new ArrayList<>();
    final Maintain maintain = new Maintain();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        initView();
        Event();
    }

    private void Event() {
        rbNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(AddScheduleActivity.this, "Bạn đã bật thông báo!", Toast.LENGTH_SHORT).show();
                    createChanel();
                    Intent intent = new Intent(AddScheduleActivity.this, AlarmUtils.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AddScheduleActivity.this,0,intent,0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    long timewhenclick = System.currentTimeMillis();
                    long tenmillisecond = 1000*10;
                    String start = edtDate.getText().toString();
                    String end = edtNextDate.getText().toString();

                    String[] arStr = start.split("/");
                    String[] arEnd = end.split("/");
//
//                    LocalDate startdate = LocalDate.of(Integer.parseInt(arStr[2]), Integer.parseInt(arStr[1]), Integer.parseInt(arStr[0]));
//                    LocalDate endDate = LocalDate.of(Integer.parseInt(arEnd[2]), Integer.parseInt(arEnd[1]), Integer.parseInt(arEnd[0]));
//                    Period different = Period.between(startdate, endDate);
////                    long year = different.getYears()*365;
////                    long month = different.getMonths()*30;
////                    long day = different.getDays();
////                    long y = TimeUnit.DAYS.toMillis(year);
////                    long m = TimeUnit.DAYS.toMillis(month);
////                    long d = TimeUnit.DAYS.toMillis(day);
////                    long total = y+m+d;

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Integer.parseInt(arEnd[2]), Integer.parseInt(arEnd[1]), Integer.parseInt(arEnd[0]));
                    long time = calendar.getTimeInMillis();
                    alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);

                }
            }
        });
    }
private void createChanel(){
    if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
        CharSequence name = "helllo";
        String des = "hellllllllo";
        int important = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("hello",name,important);
        channel.setDescription(des);
        NotificationManager notificationManager =  getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }
}
    private void initView() {
        edtDate            = findViewById(R.id.edtDate);
        edtDetailMaintaine = findViewById(R.id.edtDetailMaintaine);
        edtKm              = findViewById(R.id.edtKm);
        edtPrice           = findViewById(R.id.edtPrice);
        edtNextDate        = findViewById(R.id.edtNextDate);
        rbNotify           = findViewById(R.id.rbNotify);
    }

    public void onClickAdd(View view) {
        final String date   = edtDate.getText().toString();
        final String detail = edtDetailMaintaine.getText().toString();
        final String km     = edtKm.getText().toString();
        final String price  = edtPrice.getText().toString();
        final String nextday  = edtNextDate.getText().toString();
        final Boolean rb = rbNotify.isChecked();
        if(TextUtils.isEmpty(date)){
            Toast.makeText(AddScheduleActivity.this, "Bạn chưa nhập ngày bảo dưỡng", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(detail)){
            Toast.makeText(AddScheduleActivity.this, "Bạn chưa nhập chi tiết bảo dưỡng", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(km)){
            Toast.makeText(AddScheduleActivity.this, "Bạn chưa nhập số Km đã đi", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(price)){
            Toast.makeText(AddScheduleActivity.this, "Bạn chưa nhập chi phí bảo dưỡng", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(nextday)){
            Toast.makeText(AddScheduleActivity.this, "Bạn chưa nhập ngày bảo dưỡng tiếp theo", Toast.LENGTH_SHORT).show();
            return;
        }

        final String key = "Key:" + System.currentTimeMillis();

        maintain.setNgay(date);
        maintain.setDetail(detail);
        maintain.setSoKm(km);
        maintain.setPrice(price);
        maintain.setNgaytt(nextday);
        maintain.setKey(key);
        maintain.setCheck(rb);
        FirebaseDatabase.getInstance().getReference()
                .child("Maintain")
                .child(AccountUtils.getInstance().getAccount().getPhoneNumber())
                .child(key)
                .setValue(maintain, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null){
                            Toast.makeText(AddScheduleActivity.this, "Thêm lịch bảo dưỡng thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(AddScheduleActivity.this, "Thêm lịch bảo dưỡng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
