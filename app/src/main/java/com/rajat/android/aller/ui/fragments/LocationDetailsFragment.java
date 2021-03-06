package com.rajat.android.aller.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rajat.android.aller.R;
import com.rajat.android.aller.Util.Constants;
import com.rajat.android.aller.Util.Utilities;
import com.rajat.android.aller.model.LocationPOJO;
import com.rajat.android.aller.ui.widget.BezelImageView;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationDetailsFragment extends Fragment {

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
    ScrollView mScrollView;

    public LocationDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_details, container, false);

        Bundle extras = getArguments().getBundle(Constants.FRAGMENT_BUNDLE);
        locationPOJO = extras.getParcelable(Constants.PARCEL_KEY);

        placeNameTextView = (TextView) view.findViewById(R.id.place_name);
        cardView = (CardView) view.findViewById(R.id.card_view);
        placeImage = (BezelImageView) view.findViewById(R.id.place_image);
        placeAddressTextView = (TextView) view.findViewById(R.id.place_address);
        ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        imageView = (ImageView) view.findViewById(R.id.place_image);
        mScrollView = (ScrollView) view.findViewById(R.id.linear_layout_component);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_remove);
        placePhoneView = (TextView) view.findViewById(R.id.place_phone);
        placeWesiteView = (TextView) view.findViewById(R.id.place_website);

        placeId = locationPOJO.getPlace_id();
        Picasso.with(getContext()).load(Utilities.getPathToImage(placeId)).placeholder(R.drawable.ic_image_placeholder).into(imageView);


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

        return view;
    }

    public static LocationDetailsFragment newInstance(Bundle bundle) {
        LocationDetailsFragment fragment = new LocationDetailsFragment();
        Bundle args = new Bundle();
        args.putBundle(Constants.FRAGMENT_BUNDLE, bundle);
        fragment.setArguments(args);
        return fragment;
    }


}
