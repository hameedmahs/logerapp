package com.example.loger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageActivity extends AppCompatActivity {

    ImageView mimage;
    androidx.appcompat.widget.Toolbar toolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        toolBar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mimage = findViewById(R.id.imagefull);
        Intent intent = getIntent();

      /*  byte[] bytes=intent.getByteArrayExtra("imagebyte");
       Bitmap bmp= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        mimage.setImageBitmap(bmp);*/


        String image = intent.getStringExtra("imagestring");
        String title = intent.getStringExtra("title");
        getSupportActionBar().setTitle(title);


        Picasso.get().load(image).into(mimage);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
}
}


