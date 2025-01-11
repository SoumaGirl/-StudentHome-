package com.example.promobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // Initialize UI components
        ImageButton backButton = findViewById(R.id.backButton);
        Button appartButton = findViewById(R.id.AppartButton);
        Button roomButton = findViewById(R.id.RoomButton);
        Button allButton = findViewById(R.id.AllButton);
        ImageView homeIcon = findViewById(R.id.homeIcon);
        ImageView chatIcon = findViewById(R.id.chatIcon);
        ImageView favoriteIcon = findViewById(R.id.favoriteIcon);
        ImageView profileIcon = findViewById(R.id.profileIcon);

        // Back button listener
        backButton.setOnClickListener(view -> finish());

        // Button listeners for filtering properties
        appartButton.setOnClickListener(view ->
                Toast.makeText(HomePage.this, "Appartements sélectionnés", Toast.LENGTH_SHORT).show()
        );

        roomButton.setOnClickListener(view ->
                Toast.makeText(HomePage.this, "Chambres sélectionnées", Toast.LENGTH_SHORT).show()
        );

        allButton.setOnClickListener(view ->
                Toast.makeText(HomePage.this, "Tous les biens sélectionnés", Toast.LENGTH_SHORT).show()
        );

        // Navigation bar icon listeners
        homeIcon.setOnClickListener(view ->
                Toast.makeText(HomePage.this, "Vous êtes déjà sur la page d'accueil", Toast.LENGTH_SHORT).show()
        );

        chatIcon.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, MessageActivity.class);
            startActivity(intent);
        });

        favoriteIcon.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, FavoritesActivity.class);
            startActivity(intent);
        });

        profileIcon.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Set up RecyclerView for property listings
        RecyclerView propertyRecyclerView = findViewById(R.id.propertyRecyclerView);
        propertyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch property data from the database (dummy example below)
        List<Offer> offerList = fetchDataFromDatabase();

        OfferAdapter adapter = new OfferAdapter(offerList);
        propertyRecyclerView.setAdapter(adapter);
    }

    /**
     * This method simulates fetching data from the database.
     * Replace this logic with your actual database query.
     */
    private List<Offer> fetchDataFromDatabase() {
        List<Offer> offerList = new ArrayList<>();
        // Example data - Replace this with actual database data
        offerList.add(new Offer(R.drawable.apart, "Appartement équipé", "Taroudant, Maroc", "1000dh/M"));
        offerList.add(new Offer(R.drawable.apart2, "Chambre équipée", "Taroudant, Maroc", "500dh/M"));
        // Add more offers from the database here
        return offerList;
    }
}
