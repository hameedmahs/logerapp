package com.example.loger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class GalleryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    androidx.appcompat.widget.Toolbar toolBar;
    private FirebaseRecyclerAdapter adapter;
    DatabaseReference reference;
    GridView gridView;

    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        recyclerView=findViewById(R.id.grid);
        Intent intent=getIntent();
        path=intent.getStringExtra("eventtitle");


        toolBar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(path);



        firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = FirebaseDatabase.getInstance().getReference().child("Gallery").child(path);

        FirebaseRecyclerOptions<galleryView> options =
                new FirebaseRecyclerOptions.Builder<galleryView>()
                        .setQuery(query, galleryView.class)
                        .build();


        adapter = new FirebaseRecyclerAdapter<galleryView, View_Holder>(options) {
            @NonNull
            @Override
            public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem, parent, false);
                return new View_Holder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull View_Holder holder, int position, @NonNull galleryView model) {

                Picasso.get().load(model.getImage()).into(holder.mimageview);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1=new Intent(GalleryActivity.this,ImageActivity.class);
                      /*  Drawable mdrawable=holder.mimageview.getDrawable();
                        Bitmap mbitmap=((BitmapDrawable)mdrawable).getBitmap();
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                        mbitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                        byte []bytes=stream.toByteArray();
                        intent1.putExtra("imagebyte",bytes);*/
                        intent1.putExtra("imagestring",model.getImage());
                        intent1.putExtra("title",path);
                        startActivity(intent1);
                    }
                });


            }


        };






        RecyclerView.LayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    public class View_Holder extends RecyclerView.ViewHolder {




        ImageView mimageview;

        public View_Holder(@NonNull View itemView) {
            super(itemView);

            mimageview = itemView.findViewById(R.id.galleryitem);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
                }
            });



        }


    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}