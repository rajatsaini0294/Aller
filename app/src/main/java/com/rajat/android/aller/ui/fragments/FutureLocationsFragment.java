package com.rajat.android.aller.ui.fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.rajat.android.aller.adapters.GridAdapter;
import com.rajat.android.aller.data.DataProvider;
import com.rajat.android.aller.data.TableColumns;
import com.rajat.android.aller.model.LocationPOJO;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.location.places.ui.PlacePicker.getPlace;
import static com.rajat.android.aller.data.TableColumns.PLACE_LATITUDE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FutureLocationsFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LoaderManager.LoaderCallbacks<Cursor> {

    FloatingActionButton floatingActionButton;
    int PLACE_PICKER_REQUEST = 1;
    ImageView imageView;
    GoogleApiClient mGoogleApiClient;
    GridAdapter adapter;
    RecyclerView recyclerView;
    LocationPOJO locationPOJO;
    public FutureLocationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getSupportLoaderManager().initLoader(1,null,this);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_future_locations, container, false);
        //imageView = (ImageView) view.findViewById(R.id.image);
       recyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);



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
                saveToDb(data);
            }
        }
    }

    private void saveToDb(Intent data){
        Place place = getPlace(getContext(), data);
     /*   ContentValues values = new ContentValues();
        values.put(TableColumns.PLACE_ID, place.getId());
        values.put(TableColumns.PLACE_NAME, Utilities.convertToString(place.getName()));
        values.put(TableColumns.PLACE_ADDRESS, Utilities.convertToString(place.getAddress()));
        values.put(TableColumns.PLACE_PHONE, Utilities.convertToString(place.getPhoneNumber()));
        values.put(TableColumns.PLACE_WEBSITE, Utilities.convertToString(place.getWebsiteUri()));
        values.put(PLACE_LATITUDE, place.getLatLng().latitude);
        values.put(TableColumns.PLACE_LONGITUDE, place.getLatLng().longitude);
        values.put(TableColumns.PLACE_RATING, Utilities.convertToString(place.getRating()));

        getContext().getContentResolver().insert(DataProvider.ToVisit.CONTENT_URI, values);
       */

        locationPOJO = new LocationPOJO();
        locationPOJO.setPlace_id(place.getId());
        locationPOJO.setPlace_name(Utilities.convertToString(place.getName()));
        locationPOJO.setPlace_address(Utilities.convertToString(place.getAddress()));
        locationPOJO.setPlace_phone(Utilities.convertToString(place.getPhoneNumber()));
        locationPOJO.setPlace_website( Utilities.convertToString(place.getWebsiteUri()));
        locationPOJO.setPlace_latitude((place.getLatLng().latitude)+"");
        locationPOJO.setPlace_longitude(place.getLatLng().longitude+"");
        locationPOJO.setPlace_rating(Utilities.convertToString(place.getRating()));

        new saveDataAsync().execute(locationPOJO);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri FUTURE_URI = DataProvider.ToVisit.CONTENT_URI;
        CursorLoader cursorLoader = new CursorLoader(this.getActivity(), FUTURE_URI,
                new String[]{ TableColumns._ID, TableColumns.PLACE_ID, TableColumns.PLACE_NAME,
                        TableColumns.PLACE_ADDRESS, TableColumns.PLACE_PHONE, TableColumns.PLACE_WEBSITE,
                        PLACE_LATITUDE, TableColumns.PLACE_LONGITUDE,
                        TableColumns.PLACE_RATING, TableColumns.PLACE_IMAGE},
                null,
                null,
                null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        adapter = new GridAdapter(getContext(), cursor);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    class saveDataAsync extends AsyncTask{

        public saveDataAsync(){

        }
        @Override
        protected String doInBackground(Object[] objects) {
            LocationPOJO object = (LocationPOJO) objects[0];
            if(object!=null){
                ContentValues values = new ContentValues();
                values.put(TableColumns.PLACE_ID, object.getPlace_id());
                values.put(TableColumns.PLACE_NAME, object.getPlace_name());
                values.put(TableColumns.PLACE_ADDRESS, object.getPlace_address());
                values.put(TableColumns.PLACE_PHONE, object.getPlace_phone());
                values.put(TableColumns.PLACE_WEBSITE, object.getPlace_website());
                values.put(PLACE_LATITUDE, object.getPlace_latitude());
                values.put(TableColumns.PLACE_LONGITUDE, object.getPlace_longitude());
                values.put(TableColumns.PLACE_RATING, object.getPlace_rating());

                getContext().getContentResolver().insert(DataProvider.ToVisit.CONTENT_URI, values);

            }
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Toast.makeText(getContext(), "Saved To db", Toast.LENGTH_LONG).show();
        }
    }
}
