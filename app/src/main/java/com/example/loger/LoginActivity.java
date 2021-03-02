package com.example.loger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.loger.RegisterActivity.KEY_REGNO;

import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;


public class LoginActivity extends AppCompatActivity {

    TextView logintxt, usernametxt, passwordtxt, txtques1, forgot_password, txtques2, textques_register, phonetext;
    EditText et_username, et_password, et_phoneNo;
    Button login;
    float v = 0;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userId;
    ProgressDialog pd;
    public static final String KEY_CUR_SEM="sem";

    Boolean is_new=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        declaration();
        animationx();
        pd = new ProgressDialog(this);
        pd.setMessage("please wait...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();

                LoginAct();


            }
        });
    }

    public void LoginAct()

    {
        String Ac_username = et_username.getText().toString().trim();
        String Ac_password = et_password.getText().toString().trim();
        String Ac_phoneno = et_phoneNo.getText().toString().trim();

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        CollectionReference reference = firestore.collection("Users");

        Query query = reference.whereEqualTo("PHONE_NO", Ac_phoneno);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String db_username = document.getString("USERNAME");
                        String db_password = document.getString("PASSWORD");
                        String db_regno = document.getString("REG_NO");
                        String db_sem = document.getString("CURRENT_SEM");
                        if (db_username.equals(Ac_username)) {
                            if (db_password.equals(Ac_password)) {
                                pd.dismiss();
                                SharedPreferences preferences=getSharedPreferences(MY_SHARED_PREFS,MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString(KEY_REGNO,db_regno);
                                editor.putString(KEY_CUR_SEM,db_sem);
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, VarifyActivity.class);
                                intent.putExtra("phoneNo",Ac_phoneno );
                                intent.putExtra("isRegistered",is_new);
                                startActivity(intent);

                            } else {
                                pd.dismiss();
                                et_password.setError("Password is incorrect");
                            }
                        } else {
                            pd.dismiss();
                            et_username.setError("Username not found");
                        }

                    }
                } else {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "query failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void declaration() {
        et_phoneNo = (EditText) findViewById(R.id.et_phoneNo);
        logintxt = (TextView) findViewById(R.id.login_text);
        phonetext = (TextView) findViewById(R.id.phone_text);
        usernametxt = (TextView) findViewById(R.id.username_text);
        passwordtxt = (TextView) findViewById(R.id.password_text);
        txtques1 = (TextView) findViewById(R.id.textques1);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        txtques2 = (TextView) findViewById(R.id.textques2);
        textques_register = (TextView) findViewById(R.id.textques_register);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        login = (Button) findViewById(R.id.blogin);
    }

    public void animationx() {

        et_phoneNo.setTranslationX(800);
        phonetext.setTranslationX(800);
        logintxt.setTranslationX(800);
        usernametxt.setTranslationX(800);
        passwordtxt.setTranslationX(800);
        txtques1.setTranslationX(800);
        forgot_password.setTranslationX(800);
        txtques2.setTranslationX(800);
        textques_register.setTranslationX(800);
        et_username.setTranslationX(800);
        et_password.setTranslationX(800);
        login.setTranslationX(800);

        phonetext.setAlpha(v);
        et_phoneNo.setAlpha(v);
        logintxt.setAlpha(v);
        usernametxt.setAlpha(v);
        passwordtxt.setAlpha(v);
        txtques1.setAlpha(v);
        forgot_password.setAlpha(v);
        txtques2.setAlpha(v);
        textques_register.setAlpha(v);
        et_username.setAlpha(v);
        et_password.setAlpha(v);
        login.setAlpha(v);

        et_phoneNo.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(550).start();
        phonetext.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(500).start();
        logintxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(250).start();
        usernametxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(300).start();
        passwordtxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(400).start();
        txtques1.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(600).start();
        forgot_password.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(550).start();
        txtques2.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(650).start();
        textques_register.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(650).start();
        et_username.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(350).start();
        login.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(600).start();
        et_password.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(450).start();

    }
}