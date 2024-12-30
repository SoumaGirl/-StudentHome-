package com.example.promobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OfferDetailActivity extends AppCompatActivity {

    private TextView propertyTitle, propertyLocation, ownerName, ownerLabel, propertyPrice;
    private ImageView propertyImage, ownerImage, messageIcon, callIcon;
    private Button bookNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);

        // Initialiser les vues
        propertyTitle = findViewById(R.id.propertyTitle);
        propertyLocation = findViewById(R.id.propertyLocation);
        ownerName = findViewById(R.id.ownerName);
        ownerLabel = findViewById(R.id.ownerLabel);
        propertyPrice = findViewById(R.id.propertyPrice);
        propertyImage = findViewById(R.id.propertyImage);
        ownerImage = findViewById(R.id.ownerImage);
        messageIcon = findViewById(R.id.messageIcon);
        callIcon = findViewById(R.id.callIcon);
        bookNowButton = findViewById(R.id.bookNowButton);

        // Initialiser les données (exemple)
        propertyTitle.setText("Appartement équipé");
        propertyLocation.setText("Taroudant, Maroc");
        ownerName.setText("Ahmed Chawi");
        ownerLabel.setText("Propriétaire");
        propertyPrice.setText("1000DH/Mois");

        // Action pour l'icône de message
        messageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lancer MessageActivity lorsque l'icône de message est cliquée
                Intent intent = new Intent(OfferDetailActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });

        // Action pour l'icône d'appel
        callIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logique pour passer un appel
                Toast.makeText(OfferDetailActivity.this, "Appel à " + ownerName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Action pour le bouton "BOOK NOW" (suppression de l'intent vers BookingConfirmationActivity)
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logique pour réserver l'offre
                Toast.makeText(OfferDetailActivity.this, "Réservation effectuée pour l'offre", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
