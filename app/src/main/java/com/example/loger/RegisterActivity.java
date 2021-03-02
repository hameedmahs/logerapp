package com.example.loger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    public static final String MY_SHARED_PREFS = "sharedPrefs";
    public static final String KEY_REGNO = "text";
    TextView registertxt, regnotxt, dobtxt, txtques1, lcodetxt, textques_login;
    EditText et_regno, et_dob, et_lcode;
    Button bnext;
    ProgressDialog pd;
    float v = 0;
    String date, s_lcode;

    SharedPreferences sharedPreferences;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference noteref = db.collection("collegestud");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        declaration();
        animationx();
        sharedPreferences = getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE);

        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_REGNO, et_regno.getText().toString());
                editor.apply();
                Body();


            }
        });
        textques_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    public void saveData() {

        Toast.makeText(this, "data saved", Toast.LENGTH_SHORT).show();
    }


    public void Body() {


        String s_regno;
        s_regno = et_regno.getText().toString().trim();
        s_lcode = et_lcode.getText().toString().trim();


        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date d_dob;
        String s_dob = (et_dob.getText().toString());
        if (s_dob.isEmpty()) {
            pd.dismiss();
            Toast.makeText(RegisterActivity.this, "Please enter your date of birth", Toast.LENGTH_SHORT).show();
        } else {
            try {
                d_dob = formatter.parse(s_dob);
                date = new SimpleDateFormat("MM/dd/yyy").format(d_dob);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if (s_regno.isEmpty()) {
            pd.dismiss();
            Toast.makeText(RegisterActivity.this, "Please fill your register number", Toast.LENGTH_SHORT).show();
        } else if (s_dob.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please enter your date of birth", Toast.LENGTH_SHORT).show();
        } else if (s_lcode.isEmpty()) {
            pd.dismiss();
            Toast.makeText(RegisterActivity.this, s_dob, Toast.LENGTH_SHORT).show();
        } else {
            pd.setMessage("please wait");
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            noteref.document(s_lcode).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String db_regno = documentSnapshot.getString("regno");
                                Date db_dob = documentSnapshot.getDate("dob");
                                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                                Date dob;

                                String t_dob = (db_dob.getMonth() + 1) + "/" + db_dob.getDate() + "/" + (1900 + db_dob.getYear());


                                if (db_regno.equals(s_regno) && t_dob.equals(s_dob)) {
                                    pd.dismiss();
                                    Intent intent = new Intent(RegisterActivity.this, RegisterActivity1.class);
                                    intent.putExtra("pslcode", s_lcode);
                                    intent.putExtra("psregno", db_regno);

                                    startActivity(intent);

                                } else {
                                    pd.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Enter the correct values", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                pd.dismiss();
                                Toast.makeText(RegisterActivity.this, "Can't obtain the document", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Failed to query", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void declaration() {

        pd = new ProgressDialog(RegisterActivity.this);

        registertxt = (TextView) findViewById(R.id.register_text);
        regnotxt = (TextView) findViewById(R.id.regno_text);
        dobtxt = (TextView) findViewById(R.id.dob_text);
        txtques1 = (TextView) findViewById(R.id.textques1);
        lcodetxt = (TextView) findViewById(R.id.lcode);
        textques_login = (TextView) findViewById(R.id.textques_login);
        et_regno = (EditText) findViewById(R.id.et_regno);
        et_dob = (EditText) findViewById(R.id.et_dob);
        et_lcode = (EditText) findViewById(R.id.et_lcode);
        bnext = (Button) findViewById(R.id.bnext);
    }

    public void animationx() {

        registertxt.setTranslationX(800);
        regnotxt.setTranslationX(800);
        dobtxt.setTranslationX(800);
        txtques1.setTranslationX(800);
        lcodetxt.setTranslationX(800);
        textques_login.setTranslationX(800);
        et_regno.setTranslationX(800);
        et_dob.setTranslationX(800);
        et_lcode.setTranslationX(800);
        bnext.setTranslationX(800);

        registertxt.setAlpha(v);
        regnotxt.setAlpha(v);
        dobtxt.setAlpha(v);
        txtques1.setAlpha(v);
        lcodetxt.setAlpha(v);
        textques_login.setAlpha(v);
        et_regno.setAlpha(v);
        et_dob.setAlpha(v);
        et_lcode.setAlpha(v);
        bnext.setAlpha(v);

        registertxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(250).start();
        regnotxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(300).start();
        dobtxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(400).start();
        txtques1.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(650).start();
        lcodetxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(500).start();
        textques_login.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(650).start();
        et_regno.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(350).start();
        et_dob.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(450).start();
        et_lcode.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(550).start();
        bnext.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(600).start();


    }
}