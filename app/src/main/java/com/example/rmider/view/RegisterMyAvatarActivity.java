package com.example.rmider.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.rmider.R;
import com.example.rmider.config.Constants;
import com.example.rmider.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterMyAvatarActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_IMAGE = 1;
    private static final int REQUEST_TAKE_PICTURE = 2;
    private CircleImageView imgAvatar;

    private ProgressDialog progressDialog;
    private Account account;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_my_avatar);

        initView();
        intData();
    }

    private void intData() {
        account = (Account) getIntent().getSerializableExtra(Constants.KEY_ACCOUNT);
    }

    private void initView() {
        imgAvatar = findViewById(R.id.imgAvatar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.setCancelable(false);
    }

    public void onClickNext(View view) {
        if (account.getAvatar() != null){
            Intent intent = new Intent(getApplicationContext(),RegisterMyCarActivity.class);
            intent.putExtra(Constants.KEY_ACCOUNT,account);
            startActivity(intent);
        }
    }

    public void onClickPickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            Uri uriImage = requestCode == REQUEST_PICK_IMAGE ? data.getData() : uri;

            Glide.with(this)
                    .load(uriImage)
                    .into(imgAvatar);

            uploadAvatar(uriImage);
        }
    }

    private void uploadAvatar(Uri uri) {
        progressDialog.show();

        FirebaseStorage.getInstance().getReference().child("Image").child(System.currentTimeMillis() + ".png").putFile(uri)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "Tải lên hình ảnh thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        progressDialog.cancel();
                        account.setAvatar(task.getResult().getDownloadUrl().toString());
                    }
                });
    }

    public void onClickCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        uri = Uri.fromFile(file);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_TAKE_PICTURE);
    }
}
