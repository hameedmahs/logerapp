package com.example.loger;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static android.content.Context.MODE_PRIVATE;
import static com.example.loger.Notify.CHANNEL_1_ID;
import static com.example.loger.RegisterActivity.KEY_REGNO;
import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;


public class AlertFragment extends Fragment {

    private NotificationManagerCompat notificationManager;


    private RecyclerView mfirstorelist;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private FirestoreRecyclerAdapter adapter1;
    SharedPreferences sharedPreferences;


    private String USER_ID;
    public String REGNO;



    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference noteref = db.collection("Users");

    public static final String KEY_COUNT="3";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alert, container, false);

        SharedPreferences preferences=getContext().getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE);

        REGNO=preferences.getString(KEY_REGNO,null);





        notificationManager = NotificationManagerCompat.from(getContext());


        mfirstorelist = view.findViewById(R.id.alert_Recy);
        firebaseFirestore = FirebaseFirestore.getInstance();


        Query query = firebaseFirestore.collection("Alerts").document(REGNO).collection("Notification")
                .orderBy("priority");
        FirestoreRecyclerOptions<AlertModel> options = new FirestoreRecyclerOptions.Builder<AlertModel>()
                .setQuery(query, AlertModel.class)
                .build();




        adapter = new FirestoreRecyclerAdapter<AlertModel, AlertViewHolder>(options) {
            int i=0;
            @NonNull
            @Override
            public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_notification, parent, false);
                return new AlertViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull AlertViewHolder holder, int position,
                                            @NonNull AlertModel model) {


                SharedPreferences.Editor editor=preferences.edit();






                holder.text_title.setText(model.getTitle_key());
                holder.text_description.setText(model.getDiscription());
                i=i+1;
                String count=Integer.toString(i);
                editor.putString(KEY_COUNT,count);
                editor.apply();

                switch (model.getCategory()) {
                    case "FEE":

                        holder.constraintLayout.setBackgroundColor(0XFFe71837);
                        holder.layout_expanded.setBackgroundColor(0XFFe71837);
                        holder.icon.setImageResource(R.drawable.ic_fee);
                        holder.icon.setColorFilter(0xFFFFF);
                        break;
                    case "ATTENDANCE":
                        holder.icon.setImageResource(R.drawable.ic_attendance);
                        holder.icon.setColorFilter(0xFFFFF);

                        break;

                    case "INTERNAL":
                        holder.icon.setImageResource(R.drawable.ic_result);
                        holder.icon.setColorFilter(0xFFF);
                    case "RESULT":
                        holder.constraintLayout.setBackgroundColor(0XFFe71837);
                        holder.layout_expanded.setBackgroundColor(0XFFe71837);
                        holder.icon.setImageResource(R.drawable.ic_result);
                        holder.icon.setColorFilter(0xFFFFF);
                        break;
                    case "CURESULT":
                    case "CUFEE":
                        holder.constraintLayout.setBackgroundColor(0XFFe71837);
                        holder.layout_expanded.setBackgroundColor(0XFFe71837);
                        holder.icon.setImageResource(R.drawable.ic_baseline_account_balance_24);
                        holder.icon.setColorFilter(0xFFFFF);
                        break;
                    case "SYLLABUS":
                        holder.icon.setImageResource(R.drawable.ic_result);
                        holder.icon.setColorFilter(0xFFFFF);
                        break;
                    case "EVENT":
                        holder.icon.setImageResource(R.drawable.ic_imagegallery);
                        holder.icon.setColorFilter(0xFFFFF);
                        break;
                    default:
                        Toast.makeText(getContext(), "thsi is", Toast.LENGTH_SHORT).show();
                }
                holder.icon_open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.layout_expanded.getVisibility() == View.GONE) {
                            TransitionManager.beginDelayedTransition(holder.layout_expanded, new AutoTransition());
                            holder.icon_open.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                            holder.layout_expanded.setVisibility(View.VISIBLE);
                        } else {
                            TransitionManager.beginDelayedTransition(holder.layout_expanded, new AutoTransition());
                            holder.icon_open.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);

                            holder.layout_expanded.setVisibility(View.GONE);
                        }
                    }
                });
                holder.text_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent;
                        switch (model.getCategory()) {
                            case "FEE":
                                intent = new Intent(getContext(), FeeInfoActivity.class);
                                startActivity(intent);

                                break;
                            case "ATTENDANCE":
                                break;
                            case "INTERNAL":
                                intent = new Intent(getContext(), InternalsActivity.class);
                                startActivity(intent);
                                break;
                            case "RESULT":
                                intent = new Intent(getContext(), ResultActivity.class);
                                startActivity(intent);
                                break;
                            case "CURESULT":
                                OpenBrowser("http://results.uoc.ac.in/");
                                break;
                            default:
                                Toast.makeText(getContext(), model.getCategory(), Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }
                });

            }
        };

        mfirstorelist.setHasFixedSize(true);
        mfirstorelist.setLayoutManager(new LinearLayoutManager(getContext()));
        mfirstorelist.setAdapter(adapter);

        return view;
    }

    public void OpenBrowser(String url){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


    public void sendOnChannel1(String title, String discription) {
        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_profile)
                .setContentTitle(title)
                .setContentText(discription)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);

    }


    private class AlertViewHolder extends RecyclerView.ViewHolder {

        private TextView text_title;
        private TextView text_description;
        private ImageView icon;
        private ImageView icon_open;
        RelativeLayout layout_expanded;
        ConstraintLayout constraintLayout;


        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.title_text);
            text_description = itemView.findViewById(R.id.discrip_alert);
            icon = itemView.findViewById(R.id.icon_alert);
            layout_expanded = itemView.findViewById(R.id.expan_alert);
            icon_open = itemView.findViewById(R.id.icon_open);
            constraintLayout = itemView.findViewById(R.id.constrain_alert);
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
