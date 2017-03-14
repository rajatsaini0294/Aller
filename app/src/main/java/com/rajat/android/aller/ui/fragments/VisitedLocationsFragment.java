package com.rajat.android.aller.ui.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.rajat.android.aller.R;
import com.rajat.android.aller.adapters.GridAdapter;
import com.rajat.android.aller.model.LocationPOJO;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitedLocationsFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    int PLACE_PICKER_REQUEST = 1;
    ImageView imageView;
    GoogleApiClient mGoogleApiClient;
    GridAdapter adapter;
    RecyclerView recyclerView;
    LocationPOJO locationPOJO;
    FrameLayout frameLayout;

    String latitude = null;
    String longitude = null;
    Bitmap VolleyBitmap;

    Cursor loadedCursor = null;

    public VisitedLocationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visited_locations, container, false);

        frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);

        //imageView = (ImageView) view.findViewById(R.id.image);
        recyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GridAdapter(getContext());
        recyclerView.setAdapter(adapter);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlacePicker();
            }
        });

        return view;
    }

    private void startPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                //Place place = getPlace(data, getContext());
                // String toastMsg = String.format("Place: %s", place.getId());
                // Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();
                //placePhotosAsync(place.getId());
               // saveToDb(data);
                Log.d("..........", "000000000");

            }
        }
    }
}
