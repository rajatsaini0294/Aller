package com.rajat.android.aller.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rajat.android.aller.R;
import com.rajat.android.aller.model.LocationPOJO;
import com.rajat.android.aller.ui.fragments.LocationDetailsFragment;

public class LocationDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
LocationPOJO locationPOJO;
    final private String PARCEL_KEY = "parcelKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);


        Bundle bundle = getIntent().getExtras();
        locationPOJO = bundle.getParcelable(PARCEL_KEY);
        LocationDetailsFragment fragment = LocationDetailsFragment.newInstance(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(locationPOJO.getPlace_name());

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
