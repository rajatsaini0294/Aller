package com.rajat.android.aller.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rajat.android.aller.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteLocationsFragment extends Fragment {


    public FavouriteLocationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_locations, container, false);
    }

}
