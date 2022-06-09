package com.example.loger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import static com.example.loger.RegisterActivity.KEY_REGNO;
import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;

public class InternalsActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;

    public String REGNO;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internals);

        SharedPreferences preferences=getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE);

        REGNO=preferences.getString(KEY_REGNO,null);

        recyclerView = findViewById(R.id.intrecy);
        Intent intent=getIntent();
        String semester=intent.getStringExtra("SEM_NAME");

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Internals").document(REGNO).collection(semester);
        FirestoreRecyclerOptions<internalModel> options = new FirestoreRecyclerOptions.Builder<internalModel>()
                .setQuery(query, internalModel.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<internalModel, InternalViewHolder>(options) {
            @NonNull
            @Override
            public InternalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.ayinternl, parent, false);
                return new InternalViewHolder(view1);

            }

            @Override
            protected void onBindViewHolder(@NonNull InternalViewHolder holder, int position, @NonNull internalModel model) {

              /*  holder.subject.setText(model.getSub());
                holder.mark.setText(model.getMark());

                holder.row.addView(holder.subject);
                holder.row.addView(holder.mark);
                holder.tableLayout.addView(holder.row);*/
                holder.subject.setText(model.getSub());
                holder.internalmark1.setText(model.getInternal());

               /* if (model.getStatus().equals("Passed")) {
                    holder.mstatus.setTextColor(0xFF37bf4d);
                } else {
                    holder.mstatus.setTextColor(0xFFe71837);
                }


                holder.mstatus.setText(model.getStatus());
*/

            }


        };
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private class InternalViewHolder extends RecyclerView.ViewHolder {

        TextView subject, internalmark1;



        public InternalViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.sub12);
            internalmark1 = itemView.findViewById(R.id.mark12);


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
