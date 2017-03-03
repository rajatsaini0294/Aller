package com.rajat.android.aller.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.rajat.android.aller.R;
import com.rajat.android.aller.adapters.CustomPagerAdapter;

public class MainActivity extends AppCompatActivity {

    int PLACE_PICKER_REQUEST = 1;

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(customPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlacePicker();
            }
        });
    }

    private void startPlacePicker(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
