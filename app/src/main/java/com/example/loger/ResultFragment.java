package com.example.loger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.List;

import static com.example.loger.RegisterActivity.KEY_REGNO;
import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;


public class ResultFragment extends Fragment {

    private RecyclerView nfirestorelist;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    SharedPreferences sharedPreferences;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_TEXT = "text";

    List<reult_First> result_list;
    private CollectionReference collectionReference;
    resultAdapter resultAdapt;
    public String REGNO;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);


        nfirestorelist = view.findViewById(R.id.expand_recy);
        firebaseFirestore = FirebaseFirestore.getInstance();

        result_list = new ArrayList<>();

        SharedPreferences preferences=getContext().getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE);

        REGNO=preferences.getString(KEY_REGNO,null);

        Query query=firebaseFirestore.collection("Result").document(REGNO).collection("sempassed")
                .orderBy("priority", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<reult_First> options=new FirestoreRecyclerOptions.Builder<reult_First>()
                .setQuery(query,reult_First.class)
                .build();



        adapter= new FirestoreRecyclerAdapter<reult_First, ResultViewHolder>(options) {
            @NonNull
            @Override
            public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.result, parent, false);
                return new ResultViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull ResultViewHolder holder, int position, @NonNull reult_First model) {

                holder.title.setText(model.getSem());
                holder.title.setOnClickListener(new View.OnClickListener() {
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

                holder.result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(getContext(),ResultActivity.class);
                        intent.putExtra("SEM_NAME",model.getSem());
                        startActivity(intent);

                    }
                });

                holder.internals.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(getContext(),InternalsActivity.class);
                        startActivity(intent);

                    }
                });

                holder.syllabus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(getContext(),SyllubusActivity.class);
                        intent.putExtra("sem",model.getSem());
                        startActivity(intent);

                    }
                });

            }


        };

        nfirestorelist.setHasFixedSize(true);
        nfirestorelist.setLayoutManager(new LinearLayoutManager(getContext()));
        nfirestorelist.setAdapter(adapter);

        return view;


    }
    private class ResultViewHolder extends RecyclerView.ViewHolder {


        private TextView title;
        ConstraintLayout expandableLayout;
        CardView cardView;
        Button result,internals,syllabus;


        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.titleTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            cardView=itemView.findViewById(R.id.cardview1);
            result=itemView.findViewById(R.id.result);
            internals=itemView.findViewById(R.id.internals);
            syllabus=itemView.findViewById(R.id.syllubus);




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






        /*collectionReference=firebaseFirestore.collection("Result")
                .document(sp_regno).collection("sempassed");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for (QueryDocumentSnapshot journels:queryDocumentSnapshots){
                        reult_First reult_first=journels.toObject(reult_First.class);
                        Toast.makeText(getContext(), reult_first.getSem(), Toast.LENGTH_SHORT).show();
                        result_list.add(new reult_First(reult_first.getSem()));


                    }
                }else {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        nfirestorelist.setLayoutManager(new LinearLayoutManager(getContext()));
        resultAdapt=new resultAdapter(result_list);

        nfirestorelist.setAdapter(resultAdapt);




        return  view;

    }


    }



        FirestoreRecyclerOptions<reult_First> options = new FirestoreRecyclerOptions.Builder<reult_First>()
                .setQuery(query, reult_First.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<reult_First, ResultfViewHolder>(options) {
            @NonNull
            @Override
            public ResultfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.result, parent, false);
                return new ResultfViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull ResultfViewHolder holder, int position, @NonNull reult_First model) {
              holder.list_title.setText(model.getSem());



            }

        };
        nfirestorelist.setHasFixedSize(true);
        nfirestorelist.setLayoutManager(new LinearLayoutManager(getContext()));
        nfirestorelist.setAdapter(adapter);
        return view;
    }

    private class ResultfViewHolder extends RecyclerView.ViewHolder {

        private TextView list_title;
        private Button internals;
        private Button results;
        ConstraintLayout expandableLayout;


        public ResultfViewHolder(@NonNull View itemView) {
            super(itemView);
            list_title = itemView.findViewById(R.id.titleTextView);
            expandableLayout=itemView.findViewById(R.id.expandableLayout);


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
} */