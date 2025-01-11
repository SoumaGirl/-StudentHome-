package com.example.promobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private ImageView backButton, profilePicture;
    private EditText emailField, phoneField, dobField;
    private RadioButton maleButton, femaleButton;
    private Button editButton, logoutButton;

    private FirebaseAuth mAuth; // Firebase Authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Remplacez par le nom exact de votre fichier XML

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize the views
        backButton = findViewById(R.id.back_button);
        profilePicture = findViewById(R.id.profile_picture);
        emailField = findViewById(R.id.email_field);
        phoneField = findViewById(R.id.phone_field);
        dobField = findViewById(R.id.dob_field);
        maleButton = findViewById(R.id.male_button);
        femaleButton = findViewById(R.id.female_button);
        editButton = findViewById(R.id.edit_button);
        logoutButton = findViewById(R.id.logout_button);

        // Handling back button click
        backButton.setOnClickListener(v -> onBackPressed());

        // Handling edit button click
        editButton.setOnClickListener(v -> {
            // Retrieve the entered information
            String email = emailField.getText().toString();
            String phone = phoneField.getText().toString();
            String dob = dobField.getText().toString();
            String gender = maleButton.isChecked() ? "Male" : "Female";

            // Display the information in a Toast
            Toast.makeText(this,
                    "Email: " + email + "\nPhone: " + phone + "\nDate of Birth: " + dob + "\nGender: " + gender,
                    Toast.LENGTH_LONG).show();
        });

        // Handling logout button click
        logoutButton.setOnClickListener(v -> {
            // Sign out the user from Firebase
            mAuth.signOut();

            // Display a Toast confirming logout
            Toast.makeText(this, "You have been logged out", Toast.LENGTH_SHORT).show();

            // Redirect to SignInActivity
            Intent intent = new Intent(ProfileActivity.this, SignInActivity.class); // Navigate to SignInActivity
            startActivity(intent);
            finish(); // Close the current activity (ProfileActivity)
        });
    }
}
