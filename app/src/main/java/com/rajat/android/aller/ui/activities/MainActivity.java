package com.rajat.android.aller.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.rajat.android.aller.R;
import com.rajat.android.aller.adapters.CustomPagerAdapter;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST= 1;

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

        createAppDirectory();
    }

    private int createAppDirectory() {

        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return 0;
        }
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return 0;
        }

        if (ContextCompat.checkSelfPermission(this, // request permission when it is not granted.
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST);

            }
        }

        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getResources().getString(R.string.app_name));
        int result = 0;
        if (folder.exists()) {
            result = 2;
        } else {
            try {
                if (folder.mkdir()) {
                    result = 1;
                } else {
                    result = 0;
                }
            } catch (Exception ecp) {
                ecp.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),getResources().getString(R.string.app_name) );
                    if (folder.exists()) {

                    } else {
                        try {
                            if (folder.mkdir()) {
                            }
                        } catch (Exception ecp) {
                            ecp.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(this, getResources().getString(R.string.permission_denied), Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
