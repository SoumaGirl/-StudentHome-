package com.example.promobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private EditText usernameEditText, confirmPasswordEditText;
    private Spinner roleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        // Initialize the views
        usernameEditText = findViewById(R.id.username);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        roleSpinner = findViewById(R.id.roleSpinner);
        Button signInButton = findViewById(R.id.signInButton);

        // Set the sign-in button action
        signInButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            String role = roleSpinner.getSelectedItem().toString();

            // Validate if fields are empty
            if (username.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                // Sign up or sign in
                signInOrSignUp(username, confirmPassword, role);
            }
        });
    }

    private void signInOrSignUp(String username, String confirmPassword, String role) {
        // For sign-up and sign-in we are using email/password. Assuming 'username' is the email.
        String email = username;
        String password = confirmPassword;

        // Try signing in first
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-in success
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        // If sign-in successful, update user role and navigate
                        updateUserRole(user, role);
                    } else {
                        // If sign-in fails, try creating a new user
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        signUpUser(email, password, role);
                    }
                });
    }

    private void signUpUser(String email, String password, String role) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-up success
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        // If sign-up successful, update user role and navigate
                        updateUserRole(user, role);
                    } else {
                        // If sign-up fails, display a message to the user
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUserRole(FirebaseUser user, String role) {
        if (user != null) {
            // Create user data object and save it to Firebase Realtime Database
            User newUser = new User(user.getEmail(), role);
            myRef.child(user.getUid()).setValue(newUser)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Role saved successfully, navigate to next activity
                            Toast.makeText(SignInActivity.this, "Connexion réussie en tant que " + role, Toast.LENGTH_SHORT).show();
                            navigateToNextActivity();
                        } else {
                            // Error saving user data
                            Toast.makeText(SignInActivity.this, "Error saving user data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void navigateToNextActivity() {
        // Navigate to the next screen after successful sign-in/signup
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class); // Change to your desired activity
        startActivity(intent);
        finish(); // Close this activity
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Vérifiez si un utilisateur est déjà connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Redirigez directement vers l'activité suivante si l'utilisateur est déjà connecté
            Toast.makeText(this, "Utilisateur déjà connecté", Toast.LENGTH_SHORT).show();
            navigateToNextActivity();
        }
    }

}
