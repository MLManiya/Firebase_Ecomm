package com.example.firebase_ecomm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class Signin_Activity extends AppCompatActivity {
    EditText email,password,forgetpwd;
    Button signin;
    private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        forgetpwd = findViewById(R.id.forgotpassword);

        mAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(view -> signin());
    }
    private void signin() {

        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TTT", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Signin_Activity.this, MainActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TTT", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Signin_Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

    }
}