package com.example.proyectotatto;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentCartas extends Fragment {
    // Define your variables here

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartas, container, false);

        CardView cardView1 = view.findViewById(R.id.carta1);
        CardView cardView2 = view.findViewById(R.id.carta2);
        CardView cardView3 = view.findViewById(R.id.carta3);
        CardView cardView4 = view.findViewById(R.id.carta4);

        // Find other CardViews similarly

        // Set click listeners for each CardView
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open a new activity when the first card is clicked
                Intent intent = new Intent(getActivity(), loginActivity.class);
                startActivity(intent);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open a new activity when the first card is clicked
                Intent intent = new Intent(getActivity(), loginActivity.class);
                startActivity(intent);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open a new activity when the first card is clicked
                Intent intent = new Intent(getActivity(), loginActivity.class);
                startActivity(intent);
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open a new activity when the first card is clicked
                Intent intent = new Intent(getActivity(), loginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
