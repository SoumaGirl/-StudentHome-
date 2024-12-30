package com.example.promobile;



import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private ImageView backButton, profilePicture;
    private EditText emailField, phoneField, dobField;
    private RadioButton maleButton, femaleButton;
    private Button editButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Remplacez par le nom exact de votre fichier XML

        // Initialisation des vues
        backButton = findViewById(R.id.back_button);
        profilePicture = findViewById(R.id.profile_picture);
        emailField = findViewById(R.id.email_field);
        phoneField = findViewById(R.id.phone_field);
        dobField = findViewById(R.id.dob_field);
        maleButton = findViewById(R.id.male_button);
        femaleButton = findViewById(R.id.female_button);
        editButton = findViewById(R.id.edit_button);
        logoutButton = findViewById(R.id.logout_button);

        // Gestion des clics
        backButton.setOnClickListener(v -> onBackPressed());

        editButton.setOnClickListener(v -> {
            // Récupérer les informations saisies
            String email = emailField.getText().toString();
            String phone = phoneField.getText().toString();
            String dob = dobField.getText().toString();
            String gender = maleButton.isChecked() ? "Male" : "Female";

            // Afficher les informations dans un Toast
            Toast.makeText(this,
                    "Email: " + email + "\nPhone: " + phone + "\nDate of Birth: " + dob + "\nGender: " + gender,
                    Toast.LENGTH_LONG).show();
        });

        logoutButton.setOnClickListener(v -> {
            // Rediriger vers l'activité de connexion
            Intent intent = new Intent(ProfileActivity.this,OfferDetailActivity.class); // Remplacez LoginActivity par votre activité de connexion
            startActivity(intent);
            finish(); // Fermer l'activité actuelle
        });
    }
}
