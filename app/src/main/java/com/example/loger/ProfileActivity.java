package com.example.loger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.loger.RegisterActivity.KEY_REGNO;
import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;

public class ProfileActivity extends AppCompatActivity {

    TextView username,phoneNo,full_name,batch,semester;
    CircleImageView profile;
    private String s_batch,s_name,s_phone,s_username,s_sem,user_id;
    Button Logout;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference noteref=db.collection("Users");
    private Uri imageuri;
    private String myUri;
    private StorageTask uploadTask;

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("User");
        mAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference().child("Profile Pic");


        SharedPreferences preferences=getSharedPreferences(MY_SHARED_PREFS,MODE_PRIVATE);
        String s_lcode=preferences.getString(KEY_REGNO,null);
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        phoneNo=findViewById(R.id.phone);
        Logout=findViewById(R.id.logout);
        username=findViewById(R.id.username);
        full_name=findViewById(R.id.fullname);
        batch=findViewById(R.id.batch);
        semester=findViewById(R.id.sem);
        profile=findViewById(R.id.profiledb);

        noteref.document(user_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    s_name=documentSnapshot.getString("NAME");
                    s_batch=documentSnapshot.getString("BATCH");
                    s_sem=documentSnapshot.getString("CURRENT_SEM");
                    s_phone=documentSnapshot.getString("PHONE_NO");
                    s_username=documentSnapshot.getString("USERNAME");
                    phoneNo.setText("+91 "+s_phone);
                    username.setText(s_username);
                    full_name.setText(s_name);
                    batch.setText(s_batch);
                    semester.setText(s_sem);
                }else{
                    Toast.makeText(ProfileActivity.this, "Failed to get your information", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Error while queriying", Toast.LENGTH_SHORT).show();
            }
        });


            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CropImage.activity().setAspectRatio(1,1).start(ProfileActivity.this);


                  //  Intent intent=new Intent(ProfileActivity.this,EditImageActivity.class);
                    //startActivity(intent);
                }
            });

            Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences.Editor editor=preferences.edit();
                    editor.clear();
                    editor.apply();
                    mAuth.signOut();
                    Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });

                getUserInfo();

    }

    private void getUserInfo() {


        noteref.document(mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){


                    if(value.contains("image")){
                        String image=value.getString("image").toString();
                        Picasso.get().load(image).into(profile);
                    }
                }
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data !=null)
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageuri=result.getUri();
            profile.setImageURI(imageuri);
        }
        else{
            Toast.makeText(this, "Error Try again", Toast.LENGTH_SHORT).show();
        }
        uploadProfileImage();

    }


    private void uploadProfileImage() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Set Your Profile");
        progressDialog.setMessage( "Please wait, while we are setting your data");
        progressDialog.show();
        if(imageuri !=null){
            final StorageReference fileRef=storageReference.child(mAuth.getCurrentUser().getUid()+".jpg");

            uploadTask=fileRef.putFile(imageuri);


            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener( ProfileActivity.this,new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){

                        Uri downloadUrl= task.getResult();
                        myUri=downloadUrl.toString();
                        HashMap<String,Object> userMap=new HashMap<>();
                        userMap.put("image",myUri);
                        String namei=mAuth.getCurrentUser().getUid().toString();
                        DocumentReference documentReference = db.collection("Users").document(namei);
                        documentReference.update(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProfileActivity.this, "Profile has been updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, "The updating of profile is failed", Toast.LENGTH_SHORT).show();

                            }
                        });
                        progressDialog.dismiss();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "Image is not selected", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}