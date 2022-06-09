package com.example.loger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import static com.example.loger.RegisterActivity.KEY_BATCH;
import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;

public class SyllubusActivity extends AppCompatActivity {

    private RecyclerView nfirestorelist;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    androidx.appcompat.widget.Toolbar toolBar;


    SharedPreferences sharedPreferences;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_TEXT = "text";
    public static  final String KEY_CLASS="text";

    List<Syllubus> syllubus;
    private CollectionReference collectionReference;
    resultAdapter resultAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllubus);
        toolBar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Syllabus");


        nfirestorelist =findViewById(R.id.expandsyll_recy);
        firebaseFirestore = FirebaseFirestore.getInstance();

        syllubus = new ArrayList<>();

        sharedPreferences = getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE);

        String sp_batch = sharedPreferences.getString(KEY_BATCH, "BSC CS");

        Intent intent=getIntent();
        String semester=intent.getStringExtra("sem");

        Query query=firebaseFirestore.collection("Syllabus").document(sp_batch).collection(semester);
        FirestoreRecyclerOptions<Syllubus> options=new FirestoreRecyclerOptions.Builder<Syllubus>()
                .setQuery(query,Syllubus.class)
                .build();



        adapter= new FirestoreRecyclerAdapter<Syllubus, SyllabusViewHolder>(options) {
            @NonNull
            @Override
            public SyllabusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.syllexpand, parent, false);
                return new SyllabusViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull SyllabusViewHolder holder, int position, @NonNull Syllubus model) {

                holder.title.setText(model.getSub());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.expandableLayout.getVisibility()==View.GONE){
                            TransitionManager.beginDelayedTransition(holder.cardView,new AutoTransition());
                            holder.expandableLayout.setVisibility(View.VISIBLE);
                        }else {
                            TransitionManager.beginDelayedTransition(holder.cardView,new AutoTransition());

                            holder.expandableLayout.setVisibility(View.GONE);
                        }

                    }
                });
                holder.syllabus.setText(model.getSyllabus());
            }
        };

        nfirestorelist.setHasFixedSize(true);
        nfirestorelist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        nfirestorelist.setAdapter(adapter);




    }
    private class SyllabusViewHolder extends RecyclerView.ViewHolder {


        private TextView title;
        ConstraintLayout expandableLayout;
        CardView cardView;
        TextView syllabus;
        TextView unit;


        public SyllabusViewHolder(@NonNull View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.titleTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            cardView=itemView.findViewById(R.id.cardview1);
            syllabus=itemView.findViewById(R.id.textsyll);
            unit=itemView.findViewById(R.id.textunit);





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
