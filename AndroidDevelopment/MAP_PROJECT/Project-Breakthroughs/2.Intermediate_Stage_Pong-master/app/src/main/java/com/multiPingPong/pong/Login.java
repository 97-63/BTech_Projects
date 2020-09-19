package com.multiPingPong.pong;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceGroup;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email,password;
    Button login;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email=(EditText)findViewById(R.id.two);
        password=(EditText)findViewById(R.id.three);
        login=(Button)findViewById(R.id.four);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        fAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emai=email.getText().toString().trim();
                String pass=password.getText().toString().trim();
 /*Validating whether the email and password are correctly entered or not.So we first check whether its empty or not by using utility
class "TextUtils"*/
                if(TextUtils.isEmpty(emai))
                {
                    email.setError("Email is required");
                }
                if(TextUtils.isEmpty(pass))
                {
                    password.setError("Password is required");
                }
                if(password.length()<6)
                {
                    password.setError("Password maust be >=6 characters");
                }
                progressBar.setVisibility(View.VISIBLE);
                //Now we will try to authenticate the user
                fAuth.signInWithEmailAndPassword(emai,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this,"Logged In Successfully",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),StartActivity.class));/*If task is successful we will redirect
 the user to the main activity page*/
                        }
                        else
                        {
                            Toast.makeText(Login.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}
