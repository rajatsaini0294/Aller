package com.rajat.android.aller.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.rajat.android.aller.R;
import com.rajat.android.aller.model.LocationPOJO;

public class LocationDetailsActivity extends AppCompatActivity {
    final private String PARCEL_KEY = "parcelKey";

    LocationPOJO locationPOJO;
    TextView textView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationPOJO = getIntent().getParcelableExtra(PARCEL_KEY);
        setContentView(R.layout.activity_location_details);
        textView = (TextView) findViewById(R.id.place_name);
        textView.setText(locationPOJO.getPlace_name());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
