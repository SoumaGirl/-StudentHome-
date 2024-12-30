package com.example.promobile;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OwnerOfferActivity extends AppCompatActivity {

    private ImageView backButton, homeIcon, chatIcon, favoriteIcon, profileIcon;
    private Button myOffersButton, addOfferButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_offer); // Replace with your layout name if different

        // Initialize Views
        backButton = findViewById(R.id.backButton);
        homeIcon = findViewById(R.id.homeIcon);
        chatIcon = findViewById(R.id.chatIcon);
        favoriteIcon = findViewById(R.id.favoriteIcon);
        profileIcon = findViewById(R.id.profileIcon);
        myOffersButton = findViewById(R.id.myOffersButton);
        addOfferButton = findViewById(R.id.addOfferButton);

        // Set OnClickListeners
        backButton.setOnClickListener(v -> finish());

        myOffersButton.setOnClickListener(v -> {
            // Navigate to "My Offers" screen
            Toast.makeText(OwnerOfferActivity.this, "My Offers clicked", Toast.LENGTH_SHORT).show();
            // Add intent if another activity exists
        });

        addOfferButton.setOnClickListener(v -> {
            // Navigate to "Add Offer" screen
            Toast.makeText(OwnerOfferActivity.this, "Add Offer clicked", Toast.LENGTH_SHORT).show();
            // Add intent if another activity exists
        });

        homeIcon.setOnClickListener(v -> {
            // Navigate to Home
            Toast.makeText(OwnerOfferActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
        });

        chatIcon.setOnClickListener(v -> {
            // Navigate to Chat
            Toast.makeText(OwnerOfferActivity.this, "Chat clicked", Toast.LENGTH_SHORT).show();
        });

        favoriteIcon.setOnClickListener(v -> {
            // Navigate to Favorites
            Toast.makeText(OwnerOfferActivity.this, "Favorites clicked", Toast.LENGTH_SHORT).show();
        });

        profileIcon.setOnClickListener(v -> {
            // Navigate to Profile
            Toast.makeText(OwnerOfferActivity.this, "Profile clicked", Toast.LENGTH_SHORT).show();
        });
    }
}
