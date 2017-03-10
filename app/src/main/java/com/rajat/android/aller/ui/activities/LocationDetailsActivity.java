package com.rajat.android.aller.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajat.android.aller.R;
import com.rajat.android.aller.model.LocationPOJO;

public class LocationDetailsActivity extends AppCompatActivity {
    final private String PARCEL_KEY = "parcelKey";

    LocationPOJO locationPOJO;
    TextView placeNameTextView;
    Toolbar toolbar;

    String placeName;
    String placeAddress;

    CardView cardView;
    ImageView placeImage;
    TextView placeAddressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationPOJO = getIntent().getParcelableExtra(PARCEL_KEY);
        setContentView(R.layout.activity_location_details);

        placeNameTextView = (TextView) findViewById(R.id.place_name);
        cardView = (CardView) findViewById(R.id.card_view);
        placeImage = (ImageView) findViewById(R.id.place_image);
        placeAddressTextView = (TextView) findViewById(R.id.place_address);

        placeName = locationPOJO.getPlace_name();
        placeNameTextView.setText(placeName);

        placeAddress = locationPOJO.getPlace_address();
        if (placeAddress == null) {
            placeAddressTextView.setVisibility(View.GONE);
        } else {
            placeAddressTextView.setText(placeAddress);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(placeName);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}
