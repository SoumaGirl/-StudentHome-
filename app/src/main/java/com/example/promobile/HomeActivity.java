package com.example.promobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Récupérer les boutons depuis le layout
        Button signInButton = findViewById(R.id.signInButton);
        Button signUpButton = findViewById(R.id.signUpButton);

        // Définir un écouteur de clic pour le bouton Sign In
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer SignInActivity
                Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        // Définir un écouteur de clic pour le bouton Sign Up
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour démarrer SignUpActivity
                Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
