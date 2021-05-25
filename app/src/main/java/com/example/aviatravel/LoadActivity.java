package com.example.aviatravel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class LoadActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivityForResult(new Intent(this, LoginActivity.class), 0);
        } else {
            startActivity(new Intent(LoadActivity.this, MainActivity.class));
            LoadActivity.this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            startActivity(new Intent(LoadActivity.this, MainActivity.class));
            LoadActivity.this.finish();
        }else{
            LoadActivity.this.finish();
        }
    }
}
