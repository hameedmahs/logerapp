package com.example.loger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LogVarifyActivity extends AppCompatActivity {
    private static final String TAG = "read";
    TextView registertxt, varifytxt, textques_login, txtques1;
    EditText et_otp;
    Button bvarify;
    ProgressDialog pd;
    boolean c;
    float v = 0;
    private String verifivationCodeBysystem;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private boolean getotpclicked=false;
    private TextView countdowntimer;
    FirebaseUser user;
    FirebaseFirestore firebaseFirestore;
    private TextView resentotp;
    String user_id;
    public static final String KEY_REGISTERED="is_registerd";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varify);
        pd=new ProgressDialog(LogVarifyActivity.this);
        pd.setMessage("please wait...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

        firebaseFirestore=FirebaseFirestore.getInstance();


        declaration();
        animationx();
        phonenum_setup();





        bvarify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.show();
                String otps=et_otp.getText().toString().trim();
                if(!otps.isEmpty()) {
                    verifyCode(otps);
                }else{
                    pd.dismiss();
                    Toast.makeText(LogVarifyActivity.this, "Enter the OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void phonenum_setup() {
        if (!getotpclicked) {
            Intent intent = getIntent();
            String mobileNo = intent.getStringExtra("phoneNo");
            if (mobileNo.length() != 10) {
                Toast.makeText(this, "enter a valid phone no", Toast.LENGTH_SHORT).show();
            } else {
                getotpclicked = true;
                String phoneNumber = "+91" + mobileNo;
                pd.setMessage("OTP is sending....");
                pd.show();
                sendVerificationCodeToUser(phoneNumber);

            }
        }
    }

    private void sendVerificationCodeToUser(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber( phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(LogVarifyActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verifivationCodeBysystem=s;
            countdowntimer.setVisibility(View.VISIBLE);
            pd.dismiss();

            new CountDownTimer(60000,1000){
                @Override
                public void onTick(long millisUntilFinished) {

                    countdowntimer.setText(""+millisUntilFinished/1000);

                }

                @Override
                public void onFinish() {
                    resentotp.setVisibility(View.VISIBLE);
                    countdowntimer.setVisibility(View.INVISIBLE);

                }

            }.start();
            Toast.makeText(LogVarifyActivity.this, "The OTP has sent to your number", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code !=null){
                pd.show();
                et_otp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            getotpclicked=false;
            Toast.makeText(LogVarifyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    private void verifyCode(String verificationCode){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verifivationCodeBysystem,verificationCode);
        FirebaseAuth.getInstance().getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        signInTheUserByCredentials(credential);
        pd.dismiss();
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LogVarifyActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            pd.dismiss();
                            Intent intenti = new Intent(LogVarifyActivity.this, HomeActivity.class);
                            intenti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intenti);
                            finish();
                            }
                        else{
                            pd.dismiss();
                            Toast.makeText(LogVarifyActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    /*public boolean AlreadyRegistered(String userID){
        mAuth=FirebaseAuth.getInstance();

        firebaseFirestore=FirebaseFirestore.getInstance();








        /*reference.document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String userid=documentSnapshot.getString("USER_ID");
                    if(userid.equals(userID)){
                        c=false;
                        SharedPreferences preferences = getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putBoolean(KEY_REGISTERED,c);
                        editor.apply();

                    }
                }else {
                    c = true;
                    SharedPreferences preferences = getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(KEY_REGISTERED, c);
                    editor.apply();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                c=true;
                SharedPreferences preferences = getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(KEY_REGISTERED, c);
                editor.apply();
            }
        });*/

       /* Query query = reference.whereEqualTo("USER_ID", userID);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String suser_id = document.getString("USER_ID");
                        if (suser_id.equals(userID)) {
                            pd.dismiss();
                           c=true;
                            Toast.makeText(VarifyActivity.this, "Already Registered", Toast.LENGTH_SHORT).show();
                        }else{
                            pd.dismiss();
                            c=false;
                        }
                    }

                } else {
                   pd.dismiss();
                    Toast.makeText(VarifyActivity.this, "query failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return c=true;
    }*/


    public void declaration() {
        countdowntimer=(TextView)findViewById(R.id.countdown_timer);
        resentotp=(TextView)findViewById(R.id.resent_otp);
        registertxt = (TextView) findViewById(R.id.register_text);
        varifytxt = (TextView) findViewById(R.id.varify_text);
        et_otp = (EditText) findViewById(R.id.et_otp);
        bvarify = (Button) findViewById(R.id.bvarify);
        textques_login = (TextView) findViewById(R.id.textques_login);
        txtques1 = (TextView) findViewById(R.id.textques1);
    }

    public void animationx() {

        registertxt.setTranslationX(800);
        varifytxt.setTranslationX(800);
        et_otp.setTranslationX(800);
        txtques1.setTranslationX(800);
        bvarify.setTranslationX(800);
        textques_login.setTranslationX(800);

        registertxt.setAlpha(v);
        varifytxt.setAlpha(v);
        et_otp.setAlpha(v);
        txtques1.setAlpha(v);
        bvarify.setAlpha(v);
        textques_login.setAlpha(v);

        registertxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(250).start();
        varifytxt.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(300).start();
        et_otp.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(350).start();
        txtques1.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(450).start();
        bvarify.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(400).start();
        textques_login.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(450).start();

    }
}