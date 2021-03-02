package com.example.loger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


public class ImageGalleryFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    private FirebaseRecyclerAdapter adapter;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_gallery, container, false);
        recyclerView = view.findViewById(R.id.imagerecy);

        firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = FirebaseDatabase.getInstance().getReference().child("Data");

        FirebaseRecyclerOptions<Member> options =
                new FirebaseRecyclerOptions.Builder<Member>()
                        .setQuery(query, Member.class)
                        .build();


        adapter = new FirebaseRecyclerAdapter<Member, View_Holder>(options) {
            @NonNull
            @Override
            public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recy, parent, false);
                return new View_Holder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull View_Holder holder, int position, @NonNull Member model) {

                holder.mtitleview.setText(model.getTitle());
                Picasso.get().load(model.getImage()).into(holder.mimageview);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(getContext(),GalleryActivity.class);
                        intent.putExtra("eventtitle",model.getTitle());
                        startActivity(intent);

                    }
                });

            }


        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public class View_Holder extends RecyclerView.ViewHolder {


        TextView mtitleview;
        ImageView mimageview;

        public View_Holder(@NonNull View itemView) {
            super(itemView);
            mtitleview = itemView.findViewById(R.id.text_front);
            mimageview = itemView.findViewById(R.id.image_front);


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
}
