package com.example.promobile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {
    private List<Offer> offers;

    public OfferAdapter(List<Offer> offers) {
        this.offers = offers;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_item, parent, false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        Offer offer = offers.get(position);
        holder.offerImage.setImageResource(offer.getImageResId());
        holder.offerType.setText(offer.getType());
        holder.offerLocation.setText(offer.getLocation());
        holder.offerPrice.setText(offer.getPrice());
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public static class OfferViewHolder extends RecyclerView.ViewHolder {
        ImageView offerImage;
        TextView offerType;
        TextView offerLocation;
        TextView offerPrice;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            offerImage = itemView.findViewById(R.id.offerImage);
            offerType = itemView.findViewById(R.id.offerType);
            offerLocation = itemView.findViewById(R.id.offerLocation);
            offerPrice = itemView.findViewById(R.id.offerPrice);
        }
    }
}
