package com.example.miolaapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private Button bSignin,bSignup;
    private EditText tfEmail,tfPassword;
//    private TextView message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bSignin = (Button)findViewById(R.id.signin);
        tfEmail = (EditText)findViewById(R.id.tfEmail);
        tfPassword = (EditText)findViewById(R.id.tfPassword);

        bSignup = (Button)findViewById(R.id.signup);
//        message = (TextView)findViewById(R.id.message);
//        message.setVisibility(View.GONE);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        bSignin.setOnClickListener(v -> {
            mAuth.signInWithEmailAndPassword(tfEmail.getText().toString(), tfPassword.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                            message.setText("USER IN");
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                            message.setText("BAD");
                        }

            });
        });

        bSignup.setOnClickListener(v -> {
            mAuth.createUserWithEmailAndPassword(tfEmail.getText().toString(), tfPassword.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                            message.setText("USER CREATED");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                            message.setText("USER NOT NOT CREATED");
                        }});
        });
    }
}