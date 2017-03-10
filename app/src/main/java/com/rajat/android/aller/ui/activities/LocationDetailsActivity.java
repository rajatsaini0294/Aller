package com.rajat.android.aller.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rajat.android.aller.R;
import com.rajat.android.aller.model.LocationPOJO;

public class LocationDetailsActivity extends AppCompatActivity {
    final private String PARCEL_KEY = "parcelKey";

    LocationPOJO locationPOJO;
    TextView textView;
    Toolbar toolbar;
    String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationPOJO = getIntent().getParcelableExtra(PARCEL_KEY);
        setContentView(R.layout.activity_location_details);

        textView = (TextView) findViewById(R.id.place_name);

        placeName = locationPOJO.getPlace_name();
        textView.setText(placeName);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getSupportActionBar().setTitle(placeName);
    }
}
