package com.rajat.android.aller.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rajat.android.aller.R;
import com.rajat.android.aller.Util.Constants;
import com.rajat.android.aller.Util.Utilities;
import com.rajat.android.aller.data.DataProvider;
import com.rajat.android.aller.data.TableColumns;
import com.rajat.android.aller.model.LocationPOJO;
import com.rajat.android.aller.ui.widget.BezelImageView;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationDetailsFragment extends Fragment {

    final private String PARCEL_KEY = "parcelKey";

    LocationPOJO locationPOJO;
    TextView placeNameTextView;
    TextView placePhoneView;
    TextView placeWesiteView;
    RatingBar ratingBar;

    String placeName;
    String placeAddress;
    String placeRating;
    String placeId;
    String placeWebsite;
    String placePhone;

    CardView cardView;
    BezelImageView placeImage;
    TextView placeAddressTextView;
    ImageView imageView;
    FloatingActionButton floatingActionButton;
    int fragment_id;
    LinearLayout mLinearLayout;

    public LocationDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_details, container, false);

        Bundle extras = getArguments().getBundle("MY_BUNDLE");
        locationPOJO = extras.getParcelable(PARCEL_KEY);
        fragment_id = extras.getInt(Constants.FRAGMENT_KEY);

        placeNameTextView = (TextView) view.findViewById(R.id.place_name);
        cardView = (CardView) view.findViewById(R.id.card_view);
        placeImage = (BezelImageView) view.findViewById(R.id.place_image);
        placeAddressTextView = (TextView) view.findViewById(R.id.place_address);
        ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        imageView = (ImageView) view.findViewById(R.id.place_image);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.linear_layout_component);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_remove);
        placePhoneView = (TextView) view.findViewById(R.id.place_phone);
        placeWesiteView = (TextView) view.findViewById(R.id.place_website);

        placeId = locationPOJO.getPlace_id();
        Picasso.with(getContext()).load(Utilities.getPathToImage(placeId)).into(imageView);


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

        placePhone = locationPOJO.getPlace_phone();
        if(placePhone != null){
            placePhoneView.setText(placePhone);
        }else{
            placePhoneView.setVisibility(View.GONE);
        }

        placeWebsite = locationPOJO.getPlace_website();
        if(placeWebsite != null){
            placeWesiteView.setText(placeWebsite);
        }else {
            placeWesiteView.setVisibility(View.GONE);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLocation();
            }
        });
        return view;
    }

    public static LocationDetailsFragment newInstance(Bundle bundle) {
        LocationDetailsFragment fragment = new LocationDetailsFragment();
        Bundle args = new Bundle();
        args.putBundle("MY_BUNDLE", bundle);
        fragment.setArguments(args);
        return fragment;
    }

    private void deleteLocation() {

        String args[] = {placeId};
        if (fragment_id == Constants.FRAGMENT_VISITED) {
            try {
                getContext().getContentResolver().delete(DataProvider.Visited.CONTENT_URI, TableColumns.PLACE_ID + "=?", args);
            } catch (Exception ex) {
                Log.d("Database Error", "Delete Error");
            }
        } else if (fragment_id == Constants.FRAGMENT_TOVISIT) {
            try {
                getContext().getContentResolver().delete(DataProvider.ToVisit.CONTENT_URI, TableColumns.PLACE_ID + "=?", args);
            } catch (Exception ex) {
                Log.d("Database Error", "Delete Error");
            }
        }
        deleteImage();
        Snackbar.make(mLinearLayout, "Location removed", Snackbar.LENGTH_LONG).show();
    }

    private void deleteImage() {
        File file = Utilities.getPathToImage(placeId);
        if (file != null && file.exists()) {
            file.delete();
        }
    }
}
