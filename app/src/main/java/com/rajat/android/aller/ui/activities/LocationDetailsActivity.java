package com.rajat.android.aller.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rajat.android.aller.R;
import com.rajat.android.aller.Util.Utilities;
import com.rajat.android.aller.data.DataProvider;
import com.rajat.android.aller.data.TableColumns;
import com.rajat.android.aller.model.LocationPOJO;
import com.squareup.picasso.Picasso;

import java.io.File;

public class LocationDetailsActivity extends AppCompatActivity {
    final private String PARCEL_KEY = "parcelKey";

    LocationPOJO locationPOJO;
    TextView placeNameTextView;
    Toolbar toolbar;
    RatingBar ratingBar;

    String placeName;
    String placeAddress;
    String placeRating;
    String placeId;

    CardView cardView;
    ImageView placeImage;
    TextView placeAddressTextView;
    ImageView imageView;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationPOJO = getIntent().getParcelableExtra(PARCEL_KEY);
        setContentView(R.layout.activity_location_details);

        placeNameTextView = (TextView) findViewById(R.id.place_name);
        cardView = (CardView) findViewById(R.id.card_view);
        placeImage = (ImageView) findViewById(R.id.place_image);
        placeAddressTextView = (TextView) findViewById(R.id.place_address);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        imageView = (ImageView) findViewById(R.id.place_image);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinaterLayout);

        placeId = locationPOJO.getPlace_id();
        Picasso.with(this).load(Utilities.getPathToImage(placeId)).into(imageView);


        placeName = locationPOJO.getPlace_name();
        placeNameTextView.setText(placeName);

        placeAddress = locationPOJO.getPlace_address();
        if (placeAddress == null) {
            placeAddressTextView.setVisibility(View.GONE);
        } else {
            placeAddressTextView.setText(placeAddress);
        }

        placeRating = locationPOJO.getPlace_rating();
        ratingBar.setRating(Utilities.parseRating(placeRating));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.remove:
                Log.d("=============","clicked removed");
                deleteLocation();
            case R.id.add:
                // set as visited;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteLocation() {
        String args[] = {placeId};
        getContentResolver().delete(DataProvider.ToVisit.CONTENT_URI, TableColumns.PLACE_ID+"=?", args);
        deleteImage();
        Snackbar.make(coordinatorLayout, "Location removed", Snackbar.LENGTH_LONG).show();

    }

    private void deleteImage() {
        File file = Utilities.getPathToImage(placeId);
        if(file!=null && file.exists()){
            file.delete();
        }
    }
}
