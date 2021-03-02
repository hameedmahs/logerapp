package com.example.loger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class splashScreentest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screentest);
        Intent i;
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            i=new Intent(splashScreentest.this,HomeActivity.class);

        }else{
            i=new Intent(splashScreentest.this,LiquidActivity.class);

        }startActivity(i);
        finish();
    }
}