package com.example.loger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static com.example.loger.AlertFragment.KEY_COUNT;
import static com.example.loger.RegisterActivity.MY_SHARED_PREFS;

public class HomeActivity extends AppCompatActivity {

    private MeowBottomNavigation bnv_Main;
    androidx.appcompat.widget.Toolbar toolBar;

    FirebaseAuth auth;
    FirebaseUser user;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth=FirebaseAuth.getInstance();


        toolBar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolBar);

        SharedPreferences preferences=getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE);

        bnv_Main=findViewById(R.id.bnv_Main);
        bnv_Main.add(new MeowBottomNavigation.Model(1,R.drawable.ic_notifications));
        bnv_Main.add(new MeowBottomNavigation.Model(2,R.drawable.ic_attendance));
        bnv_Main.add(new MeowBottomNavigation.Model(3,R.drawable.ic_result));
        bnv_Main.add(new MeowBottomNavigation.Model(4,R.drawable.ic_fee));
        bnv_Main.add(new MeowBottomNavigation.Model(5,R.drawable.ic_imagegallery));
        bnv_Main.show(1,true);
        String count=preferences.getString(KEY_COUNT,"2");
        bnv_Main.setCount(1,count);
        replace(new AlertFragment());
        bnv_Main.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        replace(new AlertFragment());
                        break;

                    case 2:
                        replace(new AttendanceFragment());
                        break;

                    case 3:
                        replace(new ResultFragment());
                        break;

                    case 4:
                        replace(new FeeFragment());
                        break;

                    case 5:
                        replace(new ImageGalleryFragment());
                        break;

                }
                return null;
            }
        });




      /*  Button signout=findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });*/
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.settings){
            Intent intent=new Intent(HomeActivity.this,ProfileActivity.class);
            startActivity(intent);
        }
        return true;
    }
}