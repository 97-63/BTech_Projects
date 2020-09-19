package com.multiPingPong.pong;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText name, email, password;
    Button register;
    TextView mLoginButton;
    FirebaseAuth fAuth;//creating instance of firebase
    ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private ImageView userProfilePic;
    public static int PICK_Image = 143;
    String userID;
    Uri imagepath;
    private int count = 0;
    String Uname,emai,pass;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Intent intent = getIntent();
        setupUIViews();
        // userProfilePic = findViewById(R.id.one);


        fAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();/*This basically tells us where data will be stored and under which parent
data will be stored and "storageReference" points to the root and under it various child nodes or subdirectories will be created.*/
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        if(fAuth.getCurrentUser()!=null)//if already a user is logged in then he will be sent back to home page
        {
            startActivity(new Intent(getApplicationContext(),StartActivity.class));
            finish();
        }

        /*As soon as ImageView is clicked we want to generate an intent which would pick up images from Gallery or that intent should
take me to the Gallery of my phone*/
        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    Intent intent = new Intent();
                    intent.setType("image/*");/*here we have set intent to pick any type of images if we want to have specificaly .png
images then you should use "intent.setType("image/png");" ,here there is a "*" inside  "image/*" so it means everything inside image
 category .If you want audio type files then use "intent.setType("audio/*");"*/
                    intent.setAction(intent.ACTION_GET_CONTENT);/*Since we want to get images from gallery therefore we have set
  action as "ACTION_GET_CONTENT" */

                    startActivityForResult(Intent.createChooser(intent, "Select an Image"), PICK_Image);/*Refer Profile.doc*/
                    count = count + 1;
                } else {
                    StorageReference imagereference = storageReference.child(fAuth.getUid()).child("Images").child("Profile pic");
                    UploadTask uploadTask = imagereference.putFile(imagepath);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, "Failed Uploading an Image", Toast.LENGTH_SHORT).show();
                        }
                    });
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(Register.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uname = name.getText().toString().trim();
                emai = email.getText().toString().trim();
                pass = password.getText().toString().trim();

 /*Validating whether the email and password are correctly entered or not.So we first check whether its empty or not by using utility
class "TextUtils"*/
                if (TextUtils.isEmpty(emai)) {
                    email.setError("Email is required");
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password is required");
                }
                if (password.length() < 6) {
                    password.setError("Password maust be >=6 characters");
                }
                progressBar.setVisibility(View.VISIBLE);
                //Now we have to register the user in the firebase
                fAuth.createUserWithEmailAndPassword(emai, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        /*Here the task of registering the user is called "Task"*/
                        if (task.isSuccessful()) {
                            userID = fAuth.getCurrentUser().getUid();
                            sendUserData();
                            Toast.makeText(Register.this, "User created", Toast.LENGTH_LONG).show();


                            startActivity(new Intent(getApplicationContext(), StartActivity.class));/*If task is successful we will redirect
 the user to the main activity page*/
                        } else {
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_Image ) {
            imagepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);/*SEE right now the image data which
 we have got is in form of path and it can't be processed so we need to convert it into Bitmap form so that it can be processed*/
                userProfilePic.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(fAuth.getUid());
        StorageReference imageReference = storageReference.child(fAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
        UploadTask uploadTask = imageReference.putFile(imagepath);//Now we have to upload the image data using class "UploadTask"
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Register.this, "Upload successful!", Toast.LENGTH_SHORT).show();
            }
        });
        Item item = new Item(Uname, emai, pass);
        myRef.setValue(item);
    }

    @SuppressLint("WrongViewCast")
    private void setupUIViews(){
        userProfilePic = (ImageView)findViewById(R.id.one);
        name = (EditText) findViewById(R.id.two);
        email = (EditText) findViewById(R.id.three);
        password = (EditText) findViewById(R.id.four);
        register = (Button) findViewById(R.id.five);
        mLoginButton = (TextView) findViewById(R.id.six);

    }
}

