package com.rajat.android.aller.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.rajat.android.aller.R;
import com.rajat.android.aller.Util.Constants;
import com.rajat.android.aller.Util.Utilities;
import com.rajat.android.aller.data.DataProvider;
import com.rajat.android.aller.data.TableColumns;
import com.rajat.android.aller.model.LocationPOJO;
import com.rajat.android.aller.ui.fragments.LocationDetailsFragment;

import java.io.File;

public class LocationDetailsActivity extends AppCompatActivity {
    int fragment_id;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    LocationPOJO locationPOJO;
    FloatingActionButton floatingActionButton;
    String placeId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinaterLayout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_remove);

        Bundle bundle = getIntent().getExtras();
        locationPOJO = bundle.getParcelable(Constants.PARCEL_KEY);
        placeId = locationPOJO.getPlace_id();
        fragment_id = bundle.getInt(Constants.FRAGMENT_KEY);

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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLocation();
            }
        });
    }

    private void deleteLocation() {

        String args[] = {placeId};
        if (fragment_id == Constants.FRAGMENT_VISITED) {
            try {
                getContentResolver().delete(DataProvider.Visited.CONTENT_URI, TableColumns.PLACE_ID + "=?", args);
            } catch (Exception ex) {
                Log.d("Database Error", "Delete Error");
            }
        } else if (fragment_id == Constants.FRAGMENT_TOVISIT) {
            try {
                getContentResolver().delete(DataProvider.ToVisit.CONTENT_URI, TableColumns.PLACE_ID + "=?", args);
            } catch (Exception ex) {
                Log.d("Database Error", "Delete Error");
            }
        }
        deleteImage();
        Snackbar.make(coordinatorLayout, "Location removed", Snackbar.LENGTH_LONG).show();
    }

    private void deleteImage() {
        File file = Utilities.getPathToImage(placeId);
        if (file != null && file.exists()) {
            file.delete();
        }
    }
}
