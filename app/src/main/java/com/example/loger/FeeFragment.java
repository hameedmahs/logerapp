package com.example.loger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.loger.RegisterActivity.KEY_BATCH;
import static com.example.loger.RegisterActivity.KEY_REGNO;
import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;



interface onNotelistener{
    void onNoteClick(int position);
}

public class FeeFragment extends Fragment implements onNotelistener{

    private RecyclerView mfirstorelist;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    String REGNO,BATCH;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fee, container, false);

        mfirstorelist=view.findViewById(R.id.recyclerview);
        firebaseFirestore=FirebaseFirestore.getInstance();

        SharedPreferences preferences=getContext().getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE);

        REGNO=preferences.getString(KEY_REGNO,null);

        BATCH=preferences.getString(KEY_BATCH,null);

        Query query=firebaseFirestore.collection("Fees").document(REGNO).collection("Ongoing").orderBy("priority", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options=new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class)
                .build();




         adapter= new FirestoreRecyclerAdapter<Note, NoteViewHolder>(options) {
            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1=LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
                return new NoteViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note model) {



                holder.list_pending.setText(model.getPending());
                holder.list_finshed.setText(model.getFinished());
                holder.title.setText(model.getTitle());
               // holder.priority.setText(model.getPriority());

                //here you can describe the color of the recyclerview on a condition (change to better color green)
                holder.itemView.setBackgroundColor((model.getPending().equals("Closed"))?  0XFF90a4ae : 0XFF2f4760);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(),FeeInfoActivity.class);
                        intent.putExtra("ptitle",model.getTitle());
                        intent.putExtra("ppending",model.getPending());
                        intent.putExtra("pfinished",model.getFinished());
                        startActivity(intent);
                    }
                });
                    }
         };

         mfirstorelist.setHasFixedSize(true);
         mfirstorelist.setLayoutManager(new LinearLayoutManager(getContext()));
         mfirstorelist.setAdapter(adapter);

        return view;
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent=new Intent(getContext(),FeeInfoActivity.class);
        startActivity(intent);
    }

    private class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView list_finshed;
        private TextView list_pending;
        private TextView priority;
        private TextView title;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            list_finshed=itemView.findViewById(R.id.vfinished);
            list_pending=itemView.findViewById(R.id.vpending);
            title=itemView.findViewById(R.id.title);
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
