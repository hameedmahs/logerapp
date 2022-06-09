package com.example.loger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;

public class RegisterActivity1 extends AppCompatActivity {

    TextView registertxt,regnotxt,nametxt,txtques1,textques_login,et_regno,et_name,et_batch,et_semester,semtxt,batchtxt;

    Button bconfirm;
    String s_lcode;
    String s_regno;
    float v=0;
    ProgressDialog pd;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference noteref=db.collection("collegestud");

    SharedPreferences sharedPreferences;


    public static  final String SHARED_PREFS="sharedPrefs";
    String s_name,s_batch,s_sem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        sharedPreferences=getSharedPreferences(MY_SHARED_PREFS,MODE_PRIVATE);

        declaration();
        animationx();
        Setbody();


        bconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent intent=new Intent(RegisterActivity1.this,RegisterActivity2.class);
                intent.putExtra("psregno",s_regno);
                intent.putExtra("psbatch",s_batch);
                intent.putExtra("pssem",s_sem);
                intent.putExtra("psname",s_name);
                startActivity(intent);
            }
        });

        textques_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(RegisterActivity1.this,LoginActivity.class);
                startActivity(intent);



            }
        });

    }

    public  void Setbody(){
        pd=new ProgressDialog(RegisterActivity1.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        Intent intent=getIntent();
         s_lcode=intent.getStringExtra("pslcode");
         s_regno=intent.getStringExtra("psregno");

        noteref.document(s_lcode).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                     s_name=documentSnapshot.getString("name");
                     s_batch=documentSnapshot.getString("batch");
                     s_sem=documentSnapshot.getString("semester");
                    et_regno.setText(s_regno);
                    et_name.setText(s_name);
                    et_batch.setText(s_batch);
                    et_semester.setText(s_sem);

                    pd.dismiss();

                }else{
                    pd.dismiss();
                    Toast.makeText(RegisterActivity1.this, "Failed to get your information", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterActivity1.this, "Error while queriying", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void declaration(){
        registertxt=(TextView)findViewById(R.id.register_text);
        regnotxt=(TextView)findViewById(R.id.regno_text);
        nametxt=(TextView)findViewById(R.id.name_text);
        batchtxt=(TextView)findViewById(R.id.batch_text);
        txtques1=(TextView)findViewById(R.id.textques1);
        semtxt=(TextView)findViewById(R.id.semester_text);
        textques_login=(TextView)findViewById(R.id.textques_login);
        et_regno=(TextView)findViewById(R.id.tx_regno);
        et_name=(TextView)findViewById(R.id.tx_name);
        et_batch=(TextView)findViewById(R.id.tx_batch);
        et_semester=(TextView)findViewById(R.id.tx_semester);
        bconfirm=(Button)findViewById(R.id.bconfirm);


    }

    public void animationx(){

        registertxt.setTranslationX(800);
        regnotxt.setTranslationX(800);
        nametxt.setTranslationX(800);
        txtques1.setTranslationX(800);
        batchtxt.setTranslationX(800);
        textques_login.setTranslationX(800);
        et_regno.setTranslationX(800);
        semtxt.setTranslationX(800);
        et_semester.setTranslationX(800);
        et_name.setTranslationX(800);
        et_batch.setTranslationX(800);
        bconfirm.setTranslationX(800);

        registertxt.setAlpha(v);
        regnotxt.setAlpha(v);
        nametxt.setAlpha(v);
        txtques1.setAlpha(v);
        batchtxt.setAlpha(v);
        textques_login.setAlpha(v);
        et_regno.setAlpha(v);
        semtxt.setAlpha(v);
        et_semester.setAlpha(v);
        et_name.setAlpha(v);
        et_batch.setAlpha(v);
        bconfirm.setAlpha(v);

        registertxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(250).start();
        regnotxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(300).start();
        nametxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(400).start();
        txtques1.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(750).start();
        batchtxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(500).start();
        textques_login.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(750).start();
        et_regno.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(350).start();
        semtxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(600).start();
        et_semester.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(650).start();
        et_name.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(450).start();
        et_batch.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(550).start();
        bconfirm.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(700).start();


    }
}