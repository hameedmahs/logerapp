package com.example.loger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import static com.example.loger.LoginActivity.KEY_CUR_SEM;
import static com.example.loger.RegisterActivity.KEY_REGNO;
import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;

public class ResultActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    SharedPreferences sharedPreferences;
    RecyclerView tabrec;
    public String REGNO;
    public String SEM;


    ArrayList<ResultModel> Result_list = new ArrayList<>();


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        SharedPreferences preferences=getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE);
        REGNO=preferences.getString(KEY_REGNO,null);


        Intent intentm=getIntent();
        SEM=intentm.getStringExtra("SEM_NAME");



        tabrec = findViewById(R.id.tabrecy);


        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Result").document(REGNO).collection(SEM);
        FirestoreRecyclerOptions<ResultModel> options = new FirestoreRecyclerOptions.Builder<ResultModel>()
                .setQuery(query, ResultModel.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<ResultModel, ResultViewHolder>(options) {
            @NonNull
            @Override
            public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.testres, parent, false);
                return new ResultViewHolder(view1);

            }

            @Override
            protected void onBindViewHolder(@NonNull ResultViewHolder holder, int position, @NonNull ResultModel model) {

              /*  holder.subject.setText(model.getSub());
                holder.mark.setText(model.getMark());

                holder.row.addView(holder.subject);
                holder.row.addView(holder.mark);
                holder.tableLayout.addView(holder.row);*/
                holder.subject.setText(model.getSub());
                holder.mark1.setText(String.valueOf(model.getMark()));

                if (model.getStatus().equals("Passed")) {
                    holder.mstatus.setTextColor(0xFF37bf4d);
                } else {
                    holder.mstatus.setTextColor(0xFFe71837);
                }


                holder.mstatus.setText(model.getStatus());


            }


        };
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        tabrec.setLayoutManager(layoutManager);
        tabrec.setItemAnimator(new DefaultItemAnimator());
        tabrec.setAdapter(adapter);

    }

    private class ResultViewHolder extends RecyclerView.ViewHolder {

        TextView subject, mark1, mstatus;
        TableRow row;
        TableLayout tableLayout;


        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.sub1);
            mark1 = itemView.findViewById(R.id.mar1);
            mstatus = itemView.findViewById(R.id.pass21);


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