package com.example.firebase_ecomm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase_ecomm.ui.Addproduct.AddProductFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Google_Auth_Activity extends AppCompatActivity {
    SignInButton googleSignInButton;
    private GoogleSignInClient googleSignInClient;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_auth);
        googleSignInButton=findViewById(R.id.google_button);
        mAuth= FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("164346495880-g7u188sufgc48lev012utnfrhhldf533.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(Google_Auth_Activity.this, googleSignInOptions);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });
    }

    private void signInWithGoogle() {
        Intent intent = googleSignInClient.getSignInIntent();
        // Start activity for result
        startActivityForResult(intent, 100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            // check condition
            if (signInAccountTask.isSuccessful()) {
                // When google sign in successful initialize string
                String s = "Google sign in successful";

                // Display Toast
                displayToast(s);
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential

                        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Check condition
                                if (task.isSuccessful()) {
                                    Log.d("TTT", "onComplete: Who signed in?  " + mAuth.getCurrentUser().getEmail());
                                    // When task is successful redirect to profile activity display Toast
                                    startActivity(new Intent(Google_Auth_Activity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    Log.d("TTT", "onComplete: Who signed in?  " + mAuth.getCurrentUser().getEmail());
                                    displayToast("Firebase authentication successful");
                                } else {
                                    // When task is unsuccessful display Toast
                                    displayToast("Authentication Failed :" + task.getException().getMessage());
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
