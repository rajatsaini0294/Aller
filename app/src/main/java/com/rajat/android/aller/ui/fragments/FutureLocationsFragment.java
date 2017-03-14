package com.rajat.android.aller.ui.fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.rajat.android.aller.R;
import com.rajat.android.aller.Util.Utilities;
import com.rajat.android.aller.adapters.GridAdapter;
import com.rajat.android.aller.data.DataProvider;
import com.rajat.android.aller.data.TableColumns;
import com.rajat.android.aller.model.LocationPOJO;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.location.places.ui.PlacePicker.getPlace;
import static com.rajat.android.aller.Util.Utilities.convertBitmapToJPEG;
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
    FrameLayout frameLayout;

    String latitude = null;
    String longitude = null;
    Bitmap VolleyBitmap;

    Cursor loadedCursor = null;

    public FutureLocationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getSupportLoaderManager().initLoader(1, null, this);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_future_locations, container, false);

        frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);

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
                saveToDb(data);
            }
        }
    }

    private void saveToDb(Intent data) {
        Place place = getPlace(getContext(), data);

        locationPOJO = new LocationPOJO();
        locationPOJO.setPlace_id(place.getId());
        locationPOJO.setPlace_name(Utilities.convertToString(place.getName()));
        locationPOJO.setPlace_address(Utilities.convertToString(place.getAddress()));
        locationPOJO.setPlace_phone(Utilities.convertToString(place.getPhoneNumber()));
        locationPOJO.setPlace_website(Utilities.convertToString(place.getWebsiteUri()));
        locationPOJO.setPlace_latitude((place.getLatLng().latitude) + "");
        locationPOJO.setPlace_longitude(place.getLatLng().longitude + "");
        locationPOJO.setPlace_rating(Utilities.convertToString(place.getRating()));

        new saveDataAsync().execute(locationPOJO);
        Log.d("..........", "saved!!");

        latitude = (place.getLatLng().latitude) + "";
        longitude = (place.getLatLng().longitude) + "";
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
                new String[]{TableColumns._ID, TableColumns.PLACE_ID, TableColumns.PLACE_NAME,
                        TableColumns.PLACE_ADDRESS, TableColumns.PLACE_PHONE, TableColumns.PLACE_WEBSITE,
                        PLACE_LATITUDE, TableColumns.PLACE_LONGITUDE,
                        TableColumns.PLACE_RATING},
                null,
                null,
                null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        loadedCursor = cursor;
        adapter.setCursor(cursor);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    class saveDataAsync extends AsyncTask {

        String placeId = null;
        boolean flag = false;

        public saveDataAsync() {

        }

        private boolean checkLocationExist(String placeId) {
            if (loadedCursor != null &&loadedCursor.getCount() > 0) {
                do {
                    String id = loadedCursor.getString(loadedCursor.getColumnIndex(TableColumns.PLACE_ID));
                    if (id.equals(placeId)) {
                        return true;
                    }
                } while (loadedCursor.moveToNext());
            } else {
                Log.d("Cursor Error:", "LoadedCursor is null");
            }
            return false;
        }

        @Override
        protected String doInBackground(Object[] objects) {
            LocationPOJO object = (LocationPOJO) objects[0];
            if (object != null) {
                ContentValues values = new ContentValues();
                placeId = object.getPlace_id();
                flag = checkLocationExist(placeId);
                if (!flag) {
                    values.put(TableColumns.PLACE_ID, placeId);

                    values.put(TableColumns.PLACE_NAME, object.getPlace_name());
                    values.put(TableColumns.PLACE_ADDRESS, object.getPlace_address());
                    values.put(TableColumns.PLACE_PHONE, object.getPlace_phone());
                    values.put(TableColumns.PLACE_WEBSITE, object.getPlace_website());
                    values.put(PLACE_LATITUDE, object.getPlace_latitude());
                    values.put(TableColumns.PLACE_LONGITUDE, object.getPlace_longitude());
                    values.put(TableColumns.PLACE_RATING, object.getPlace_rating());


                    getContext().getContentResolver().insert(DataProvider.ToVisit.CONTENT_URI, values);
                    longitude = object.getPlace_longitude();
                    latitude = object.getPlace_latitude();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (flag) {
                Snackbar.make(frameLayout, "Location already exist", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(frameLayout, "Location saved", Snackbar.LENGTH_LONG).show();
                new savePlaceImageAsyncTask().execute(placeId);
            }
        }
    }

    class savePlaceImageAsyncTask extends AsyncTask {
        boolean flag = false;
        String placeId = null;

        @Override
        protected Object doInBackground(Object[] objects) {
            placeId = (String) objects[0];
            ContentValues values = new ContentValues();
            File file = getPlacePhoto(placeId);
            if (file != null) {
                if (file.exists()) {
                    flag = true;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Toast.makeText(getContext(), "Image Saved To db", Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
            if (!flag) {
                RequestQueue rq = Volley.newRequestQueue(getContext());
                ImageRequest ir = null;
                if (latitude != null && longitude != null) {
                    String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&style=feature:all&size=400x400&markers=color:red|" + latitude + "," + longitude + "&key=" + getResources().getString(R.string.STATIC_MAP_IMAGE_API_KEY);
                    ir = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            File file = Utilities.convertBitmapToJPEG(response, placeId);
                            adapter.notifyDataSetChanged();
                        }
                    }, 0, 0, null, null, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error:", "Error volley");
                        }
                    });
                }
                rq.add(ir);
            }
        }

        private File getPlacePhoto(String place_id) {
            File imagePath = null;
            PlacePhotoMetadataResult result = Places.GeoDataApi
                    .getPlacePhotos(mGoogleApiClient, place_id).await();
            if (result != null && result.getStatus().isSuccess()) {
                PlacePhotoMetadataBuffer photoMetadataBuffer = result.getPhotoMetadata();
                PlacePhotoMetadata photo = null;
                Bitmap bitmap = null;
                try {
                    photo = photoMetadataBuffer.get(0);
                } catch (Exception ex) {

                }
                if (photo != null) {
                    bitmap = photo.getPhoto(mGoogleApiClient).await().getBitmap();
                    Log.d("....................", "phto not null");

                    if (bitmap != null) {
                        imagePath = convertBitmapToJPEG(bitmap, place_id);
                        Log.d("....................", "bitmpa not null");
                    }

                }
                photoMetadataBuffer.release();
            }
            return imagePath;

        }


    }
}
