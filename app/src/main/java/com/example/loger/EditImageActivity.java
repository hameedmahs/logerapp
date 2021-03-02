package com.example.loger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditImageActivity extends AppCompatActivity {

CircleImageView profileImageview;
private Button closeButton,saveButton;
private TextView profileChanger;

private DatabaseReference databaseReference;
private FirebaseAuth mAuth;

private Uri imageuri;
private String myUri;
private StorageTask uploadTask;

private StorageReference storageReference;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference noteref=db.collection("Users");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);


        mAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference().child("Profile Pic");


        profileImageview=findViewById(R.id.proflephoto);
        closeButton=findViewById(R.id.btnclose);
        saveButton=findViewById(R.id.btnSave);
        profileChanger=findViewById(R.id.change_profile_btn);


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditImageActivity.this,ProfileActivity.class));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileImage();
            }
        });

        profileChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(EditImageActivity.this);
            }
        });

        getUserinfo();
    }

    private void getUserinfo() {
        noteref.document(mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    if(value.contains("image")){
                        String image=value.getString("image").toString();
                        Picasso.get().load(image).into(profileImageview);
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
            profileImageview.setImageURI(imageuri);
        }
        else{
            Toast.makeText(this, "Error Try again", Toast.LENGTH_SHORT).show();
        }
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
            }).addOnCompleteListener( EditImageActivity.this,new OnCompleteListener<Uri>() {
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
                                Toast.makeText(EditImageActivity.this, "this task is succesfull", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditImageActivity.this, "this task is failed", Toast.LENGTH_SHORT).show();

                            }
                        });
                        progressDialog.dismiss();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(EditImageActivity.this, "Image is not selected", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}