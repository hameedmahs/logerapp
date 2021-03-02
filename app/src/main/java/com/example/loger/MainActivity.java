package com.example.loger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button register,login;
    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=findViewById(R.id.bregister);
        login=findViewById(R.id.blogin);


       animationx();

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(MainActivity.this,LoginActivity.class);
               startActivity(intent);
           }
       });

       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent= new Intent(MainActivity.this,RegisterActivity.class);
               startActivity(intent);
           }
       });

    }
    public void animationx(){
        register.setTranslationX(800);
        login.setTranslationX(800);

        register.setAlpha(v);
        login.setAlpha(v);

        register.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
    }
}