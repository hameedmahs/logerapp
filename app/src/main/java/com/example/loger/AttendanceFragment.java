package com.example.loger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.DecimalFormat;

import static android.content.Context.MODE_PRIVATE;
import static com.example.loger.RegisterActivity.KEY_REGNO;
import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;


public class AttendanceFragment extends Fragment {
    private RecyclerView nfirestorelist;
    private FirebaseFirestore firebaseFirestore;
    
    private FirestoreRecyclerAdapter adapter;

    SharedPreferences sharedPreferences;
    String REGNO;



    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_TEXT = "text";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        nfirestorelist = view.findViewById(R.id.firelist);
        firebaseFirestore = FirebaseFirestore.getInstance();
        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String sp_regno = sharedPreferences.getString(KEY_TEXT, null);

        SharedPreferences preferences=getContext().getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE);

        REGNO=preferences.getString(KEY_REGNO,null);

       Query query = firebaseFirestore.collection("attend").document(REGNO).collection("pre");


        FirestoreRecyclerOptions<AttendModel> options = new FirestoreRecyclerOptions.Builder<AttendModel>()
                .setQuery(query, AttendModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<AttendModel, AttendViewHolder>(options) {
            @NonNull
            @Override
            public AttendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendstr, parent, false);
                return new AttendViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull AttendViewHolder holder, int position, @NonNull AttendModel model) {


                float total = model.getTotal();
                float present = model.getPresent();
                DecimalFormat df = new DecimalFormat("###.#");
                float prec = ((present / total) * 100);
                String perce = df.format(prec) + "%";


                holder.circularProgressBar.setProgressWithAnimation(prec, 1000l); // =1s

// Set Progress Max
                holder.circularProgressBar.setProgressMax(100);


                holder.circularProgressBar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.BOTTOM_TO_END);

                holder.circularProgressBar.setProgressBarWidth(13); // in DP
                holder.circularProgressBar.setBackgroundProgressBarWidth(13); // in DP

// Other
                holder.circularProgressBar.setRoundBorder(true);
                holder.circularProgressBar.setStartAngle(360);
                holder.circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);

                if(prec>90) {
                    holder.circularProgressBar.setProgressBarColorStart(0xFF339900);
                    holder.circularProgressBar.setProgressBarColorEnd(0xFF99cc33);
                }else if(prec>35){
                    holder.circularProgressBar.setProgressBarColorStart(R.color.navy);
                    holder.circularProgressBar.setProgressBarColorEnd(R.color.blue);
                }else {
                    holder.circularProgressBar.setProgressBarColorStart(0xFFff9966);
                    holder.circularProgressBar.setProgressBarColorEnd(0xFFcc3300);
                }


                holder.list_title.setText(model.getTitle());
                holder.list_perc.setText(perce);

            }
        };

        nfirestorelist.setHasFixedSize(true);
        nfirestorelist.setLayoutManager(new LinearLayoutManager(getContext()));
        nfirestorelist.setAdapter(adapter);
        return view;
    }

    private class AttendViewHolder extends RecyclerView.ViewHolder {

        private TextView list_title;
        private TextView list_perc;
        private CircularProgressBar circularProgressBar;

        public AttendViewHolder(@NonNull View itemView) {
            super(itemView);
            list_title = itemView.findViewById(R.id.at_title);
            list_perc = itemView.findViewById(R.id.text_view_progress);
            circularProgressBar = itemView.findViewById(R.id.circularProgressBar);


        }

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}


