package com.example.loger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class RegisterActivity2 extends AppCompatActivity {

    TextView registertxt,usernametxt,passwordtxt,txtques1,repasswordtxt,textques_login,mobiletxt;
    EditText et_username,et_password,et_repassword,et_mobile;
    Button bregister;
    boolean a=false;
    boolean ps=false;
    boolean c=false;
    float v=0;

    Boolean is_new=true;



    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        pd=new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.setMessage("please wait");




        declaration();
        animationx();





        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                RegisterOncick();

            }
        });

    }

    public void RegisterOncick() {
        String S_username = et_username.getText().toString().trim();
        String S_password = et_password.getText().toString().trim();
        String S_repassword = et_repassword.getText().toString().trim();
        String S_mobile = et_mobile.getText().toString().trim();

        if (Password_Validation(S_password) == true) {
            if (S_password.equals(S_repassword)) {
                a = true;
            } else {
                pd.dismiss();
                et_repassword.setError("The passwords must be same");
                et_repassword.requestFocus();
            }

        } else {
            pd.dismiss();
            et_password.setError("The password must contain at least one letters,one number and one symbols");
            et_password.requestFocus();
        }

        if (S_username == null || S_username.trim().isEmpty()) {
            pd.dismiss();
            et_username.setError("Incorrect format of string");
            et_username.requestFocus();

        }
        Pattern p = Pattern.compile("[^A-Za-z0-9_]");
        Matcher m = p.matcher(S_username);
        // boolean b = m.matches();
        boolean b = m.find();
        if (b) {
            pd.dismiss();
            et_username.setError("There is special character in the string");
            et_username.requestFocus();
        } else {
            c = true;
        }
        if (S_mobile.length() == 10) {
            InputMethodManager inn = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inn.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            AlreadyRegistered(S_mobile);
            ps = true;
            if ((a && ps && c)) {

                Intent pintent=getIntent();
                String ps_regno= pintent.getStringExtra("psregno");
                String ps_batch=pintent.getStringExtra("psbatch");
                String ps_sem= pintent.getStringExtra("pssem");
                String ps_name= pintent.getStringExtra("psname");


                Intent intenti = new Intent(RegisterActivity2.this, VarifyActivity.class);

                intenti.putExtra("S_username", S_username);
                intenti.putExtra("phoneNo", S_mobile);
                intenti.putExtra("S_password", S_password);
                intenti.putExtra("psregno",ps_regno);
                intenti.putExtra("psbatch",ps_batch);
                intenti.putExtra("pssem",ps_sem);
                intenti.putExtra("psname",ps_name);
                intenti.putExtra("isRegistered",is_new);
                pd.dismiss();
                startActivity(intenti);
            } else {
                pd.dismiss();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }


        }
    }
    public void AlreadyRegistered(String testMobile){
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        CollectionReference reference = firestore.collection("Users");

        Query query = reference.whereEqualTo("phoneNo", testMobile);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String db_mobile = document.getString("phoneNo");
                        if (db_mobile.equals(testMobile)) {
                            pd.dismiss();
                            Toast.makeText(RegisterActivity2.this, "Already Registered", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity2.this, "query failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static boolean Password_Validation(String password)
    {

        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            //Pattern eight = Pattern.compile (".{8}");


            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }





    public void declaration(){
        registertxt=(TextView)findViewById(R.id.register_text);
        usernametxt=(TextView)findViewById(R.id.username_text);
        passwordtxt=(TextView)findViewById(R.id.password_text);
        textques_login=(TextView)findViewById(R.id.textques_login);
        txtques1=(TextView)findViewById(R.id.textques1);
        repasswordtxt=(TextView)findViewById(R.id.repassword_text);
        mobiletxt=(TextView)findViewById(R.id.mobile_text);
        et_username=(EditText)findViewById(R.id.et_username);
        et_password=(EditText)findViewById(R.id.et_password);
        et_repassword=(EditText)findViewById(R.id.et_repassword);
        et_mobile=(EditText)findViewById(R.id.et_mobile);
        bregister=(Button)findViewById(R.id.bregister);
    }

    public void animationx(){

        registertxt.setTranslationX(800);
        usernametxt.setTranslationX(800);
        passwordtxt.setTranslationX(800);
        txtques1.setTranslationX(800);
        repasswordtxt.setTranslationX(800);
        textques_login.setTranslationX(800);
        mobiletxt.setTranslationX(800);
        et_username.setTranslationX(800);
        et_password.setTranslationX(800);
        et_repassword.setTranslationX(800);
        et_mobile.setTranslationX(800);
        bregister.setTranslationX(800);

        registertxt.setAlpha(v);
        usernametxt.setAlpha(v);
        passwordtxt.setAlpha(v);
        txtques1.setAlpha(v);
        repasswordtxt.setAlpha(v);
        mobiletxt.setAlpha(v);
        textques_login.setAlpha(v);
        et_username.setAlpha(v);
        et_password.setAlpha(v);
        et_repassword.setAlpha(v);
        et_mobile.setAlpha(v);
        bregister.setAlpha(v);

        registertxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(250).start();
        usernametxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(300).start();
        passwordtxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(500).start();
        txtques1.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(750).start();
        repasswordtxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(600).start();
        mobiletxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(400).start();
        textques_login.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(750).start();
        et_username.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(350).start();
        et_password.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(550).start();
        et_repassword.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(650).start();
        et_mobile.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(450).start();
        bregister.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(700).start();


    }
}