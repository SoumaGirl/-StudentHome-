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
            navigateToActivity(OwnerOfferActivity.class, "My Offers clicked");
        });

        addOfferButton.setOnClickListener(v -> {
            navigateToActivity(AddOffer.class, "Add Offer clicked");
        });

        homeIcon.setOnClickListener(v -> {
            navigateToActivity(HomePage.class, "Home clicked");
        });

        chatIcon.setOnClickListener(v -> {
            navigateToActivity(MessageActivity.class, "Chat clicked");
        });

        favoriteIcon.setOnClickListener(v -> {
            navigateToActivity(FavoritesActivity.class, "Favorites clicked");
        });

        profileIcon.setOnClickListener(v -> {
            navigateToActivity(ProfileActivity.class, "Profile clicked");
        });
    }

    private void navigateToActivity(Class<?> activityClass, String toastMessage) {
        Toast.makeText(OwnerOfferActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(OwnerOfferActivity.this, activityClass);
        startActivity(intent);
    }
}
