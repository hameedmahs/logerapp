package com.example.loger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.loger.RegisterActivity.KEY_REGNO;
import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;
import static com.example.loger.RegisterActivity1.KEY_BATCH;

public class FeeInfoActivity extends AppCompatActivity {

    String stitle;
    androidx.appcompat.widget.Toolbar toolBar;
    private RecyclerView mfirstorelist;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    SharedPreferences sharedPreferences;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_info);

        toolBar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolBar);

        sharedPreferences = getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE);
        String REGNO = sharedPreferences.getString(KEY_REGNO, null);
        Intent intent = getIntent();
        stitle = intent.getStringExtra("ptitle");
        getSupportActionBar().setTitle(stitle);





        mfirstorelist = findViewById(R.id.feerecy);
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Fees").document(REGNO).collection(stitle);
        FirestoreRecyclerOptions<Fees> options = new FirestoreRecyclerOptions.Builder<Fees>()
                .setQuery(query, Fees.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<Fees, FeeInfoActivity.FeesViewHolder>(options) {
            @NonNull
            @Override
            public FeeInfoActivity.FeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.feestruc, parent, false);
                return new FeeInfoActivity.FeesViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull FeeInfoActivity.FeesViewHolder holder, int position, @NonNull Fees model) {


                holder.list_pending.setText(model.getPending());
                holder.list_finshed.setText(model.getRemitted());
                Date db_dob=model.getDate();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date dob;

                String t_dob = (db_dob.getMonth() + 1) + "/" + db_dob.getDate() + "/" + (1900 + db_dob.getYear());
                holder.date.setText(t_dob);


                // holder.priority.setText(model.getPriority());

                //here you can describe the color of the recyclerview on a condition (change to better color green)

            }


        };
        mfirstorelist.setHasFixedSize(true);
        mfirstorelist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mfirstorelist.setAdapter(adapter);

    }


    private class FeesViewHolder extends RecyclerView.ViewHolder {

        private TextView list_finshed;
        private TextView list_pending;
        private TextView priority;
        private TextView date;


        public FeesViewHolder(@NonNull View itemView) {
            super(itemView);
            list_finshed = itemView.findViewById(R.id.vfinished);
            list_pending = itemView.findViewById(R.id.vpending);
            date = itemView.findViewById(R.id.date1);
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
