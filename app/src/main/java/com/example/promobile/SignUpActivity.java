package com.example.promobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    // Firebase instances
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    // UI components
    private EditText usernameEditText, passwordEditText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase instances
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize UI components
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        signUpButton = findViewById(R.id.loginButton);

        // Sign-up button listener
        signUpButton.setOnClickListener(v -> {
            String email = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(SignUpActivity.this, "Le mot de passe doit comporter au moins 6 caractères", Toast.LENGTH_SHORT).show();
            } else {
                // Check if email already exists in Firebase Authentication (sign-in attempt)
                checkUserInAuthentication(email, password);
            }
        });
    }

    private void checkUserInAuthentication(String email, String password) {
        // Try to sign in with email and password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-in successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        checkUserRole(user);  // Check if user is "owner" or "renter"
                    } else {
                        // If sign-in fails (because the user doesn't exist), proceed with sign-up
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        signUpUser(email, password);
                    }
                });
    }

    private void signUpUser(String email, String password) {
        // Create user if email does not exist in Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-up success
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, "createUserWithEmail:success");
                        saveUserRole(user);  // Save role when user is successfully signed up
                    } else {
                        // Sign-up failed
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUpActivity.this, "Inscription échouée : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserRole(FirebaseUser user) {
        String email = user.getEmail();
        // For the sake of this example, let's assume the role is set to "renter" by default.
        String role = "renter"; // You can modify this based on your needs (e.g., based on a spinner selection).

        if (email != null) {
            User newUser = new User(email, role);
            databaseReference.child(user.getUid()).setValue(newUser)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                            navigateToNextActivity(user, role);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Erreur lors de l'enregistrement des données utilisateur", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void checkUserRole(FirebaseUser user) {
        if (user != null) {
            databaseReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve user role from the database
                        String role = dataSnapshot.child("role").getValue(String.class);
                        if (role != null) {
                            Toast.makeText(SignUpActivity.this, "Connexion réussie en tant que " + role, Toast.LENGTH_SHORT).show();
                            navigateToNextActivity(user, role);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Rôle introuvable, veuillez réessayer", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, "Database error: " + databaseError.getMessage());
                }
            });
        }
    }

    private void navigateToNextActivity(FirebaseUser user, String role) {

        Intent intent;
        if ("owner".equals(role)) {
            intent = new Intent(SignUpActivity.this, OwnerOfferActivity.class);
        } else {
            intent = new Intent(SignUpActivity.this, OfferDetailActivity .class);
        }
        startActivity(intent);
        finish();
    }
}
