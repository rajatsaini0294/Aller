package com.rajat.android.aller.ui.fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.rajat.android.aller.R;
import com.rajat.android.aller.Util.Utilities;
import com.rajat.android.aller.data.DataProvider;
import com.rajat.android.aller.data.TableColumns;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.location.places.ui.PlacePicker.getPlace;
import static com.rajat.android.aller.data.TableColumns.PLACE_LATITUDE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FutureLocationsFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    FloatingActionButton floatingActionButton;
    int PLACE_PICKER_REQUEST = 1;
    ImageView imageView;
    GoogleApiClient mGoogleApiClient;

    public FutureLocationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_future_locations, container, false);
        //imageView = (ImageView) view.findViewById(R.id.image);

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
                Place place = getPlace(data, getContext());
                String toastMsg = String.format("Place: %s", place.getId());
                Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();
                //placePhotosAsync(place.getId());
                saveToDb(data);
            }
        }
    }

    private void saveToDb(Intent data){
        Place place = getPlace(getContext(), data);
        ContentValues values = new ContentValues();
        values.put(TableColumns.PLACE_ID, place.getId());
        values.put(TableColumns.PLACE_NAME, Utilities.convertToString(place.getName()));
        values.put(TableColumns.PLACE_ADDRESS, Utilities.convertToString(place.getAddress()));
        values.put(TableColumns.PLACE_PHONE, Utilities.convertToString(place.getPhoneNumber()));
        values.put(TableColumns.PLACE_WEBSITE, Utilities.convertToString(place.getWebsiteUri()));
        values.put(PLACE_LATITUDE, place.getLatLng().latitude);
        values.put(TableColumns.PLACE_LONGITUDE, place.getLatLng().longitude);
        values.put(TableColumns.PLACE_RATING, Utilities.convertToString(place.getRating()));

        getContext().getContentResolver().insert(DataProvider.ToVisit.CONTENT_URI, values);
        Log.d("..........", "saved!!");
    }

    private ResultCallback<PlacePhotoResult> mDisplayPhotoResultCallback
            = new ResultCallback<PlacePhotoResult>() {
        @Override
        public void onResult(PlacePhotoResult placePhotoResult) {
            if (!placePhotoResult.getStatus().isSuccess()) {
                return;
            }
            //imageView.setImageBitmap(placePhotoResult.getBitmap());
        }
    };

    /**
     * Load a bitmap from the photos API asynchronously
     * by using buffers and result callbacks.
     */
    private void placePhotosAsync(String placeId) {
        Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {


                    @Override
                    public void onResult(PlacePhotoMetadataResult photos) {
                        if (!photos.getStatus().isSuccess()) {
                            return;
                        }

                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                        if (photoMetadataBuffer.getCount() > 0) {
                            // Display the first bitmap in an ImageView in the size of the view
                            photoMetadataBuffer.get(0)
                                    .getScaledPhoto(mGoogleApiClient, imageView.getWidth(),
                                            imageView.getHeight())
                                    .setResultCallback(mDisplayPhotoResultCallback);
                        }
                        photoMetadataBuffer.release();
                    }
                });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }
}
