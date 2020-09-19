package com.multiPingPong.pong;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    Button buttoSingle;
    Button buttoDouble;
    Button buttoRegister;
    Button buttoHelp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setTitle("Multi Pong");

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttoSingle = (Button)findViewById(R.id.buttonSingle);
        buttoDouble = (Button)findViewById(R.id.buttonDouble);
        buttoRegister = (Button)findViewById(R.id.pro);
        buttoHelp = (Button)findViewById(R.id.buttonHelp);

        buttoSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment gameModesFragment = new GameModesFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, gameModesFragment);
                fragmentTransaction.commit();
            }
        });

        buttoDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment bluetoothFrag = new BluetoothFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, bluetoothFrag);
                fragmentTransaction.commit();
            }
        });

        buttoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,Register.class);
                startActivity(intent);
            }
        });
        buttoHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut();//logOut the User
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

}
