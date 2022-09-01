package com.example.covax.Activities.ui.maps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covax.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Maps extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener {

    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(LOCATION_PERMS, 100);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();

        // Keep the UI Settings state in sync with the checkboxes.
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
        mUiSettings.setIndoorLevelPickerEnabled(true);
        mUiSettings.setCompassEnabled(true);

        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setMapToolbarEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(LOCATION_PERMS, 100);
            }
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnCameraIdleListener(this);

        LatLng v1 = new LatLng(40.70079875702597,-73.79536070246739);
        googleMap.addMarker(new MarkerOptions()
                .position(v1)
                .title("York College - Health and Physical Education Complex"));

        LatLng v2 = new LatLng(40.66666035287505, -73.95812314479738);
        googleMap.addMarker(new MarkerOptions()
                .position(v2)
                .title("Medgar Evers College"));

        LatLng v3 = new LatLng(40.93865877228171, -73.89692104478765);
        googleMap.addMarker(new MarkerOptions()
                .position(v3)
                .title("New York National Guard Armory - Yonkers and Mount Vernon"));

        LatLng v4 = new LatLng(41.037245090304886, -73.7789296619757);
        googleMap.addMarker(new MarkerOptions()
                .position(v4)
                .title("Westchester County Center"));

        LatLng v5 = new LatLng(40.623146678108526, -73.39804084802842);
        googleMap.addMarker(new MarkerOptions()
                .position(v5)
                .title("Jones Beach - Field 3"));

        LatLng v6 = new LatLng(43.07469493501387, -76.22163200881332);
        googleMap.addMarker(new MarkerOptions()
                .position(v6)
                .title("State Fair Expo Center: NYS Fairgrounds"));

        LatLng v7 = new LatLng(41.133722058958654, -74.08917962523327);
        googleMap.addMarker(new MarkerOptions()
                .position(v7)
                .title("SUNY Rockland Community College"));

        LatLng v8 = new LatLng(40.912495503759835, -73.12328862439182);
        googleMap.addMarker(new MarkerOptions()
                .position(v8)
                .title("Stony Brook University"));

        LatLng v9 = new LatLng(41.0046216719381, -72.4606315198177);
        googleMap.addMarker(new MarkerOptions()
                .position(v9)
                .title("Stony Brook - Southampton"));

        LatLng v10 = new LatLng(40.67231735488333, -73.83305564082681);
        googleMap.addMarker(new MarkerOptions()
                .position(v10)
                .title("Aqueduct Racetrack"));

        LatLng v11 = new LatLng(43.1579328147473, -77.55630190082776);
        googleMap.addMarker(new MarkerOptions()
                .position(v11)
                .title("Former Kodak Hawkeye Parking Lot - Rochester"));

        LatLng v12 = new LatLng(43.327392602580765, -73.67591917745096);
        googleMap.addMarker(new MarkerOptions()
                .position(v12)
                .title("Queensbury Aviation Mall - Sears"));

        LatLng v13 = new LatLng(44.66127373595047, -74.96684168461599);
        googleMap.addMarker(new MarkerOptions()
                .position(v13)
                .title("SUNY Potsdam"));

        LatLng v14 = new LatLng(44.659789121524874, -73.46449687597244);
        googleMap.addMarker(new MarkerOptions()
                .position(v14)
                .title("Plattsburgh International Airport"));

        LatLng v15 = new LatLng(42.485443379845655, -75.06453147493866);
        googleMap.addMarker(new MarkerOptions()
                .position(v15)
                .title("SUNY Oneonta"));

        LatLng v16 = new LatLng(40.72257885157121, -74.0052965522265);
        googleMap.addMarker(new MarkerOptions()
                .position(v16)
                .title("The Conference Center Niagara Falls"));

        LatLng v17 = new LatLng(40.76759384169581, -74.00165014571097);
        googleMap.addMarker(new MarkerOptions()
                .position(v17)
                .title("Javits Center"));

        LatLng v18 = new LatLng(41.73475609354457, -74.12380074112069);
        googleMap.addMarker(new MarkerOptions()
                .position(v18)
                .title("Ulster Fairgrounds in New Paltz"));

        LatLng v19 = new LatLng(41.44831105909947, -74.42748788290123);
        googleMap.addMarker(new MarkerOptions()
                .position(v19)
                .title("SUNY Orange"));

        LatLng v20 = new LatLng(42.10157189412293, -75.97155863208422);
        googleMap.addMarker(new MarkerOptions()
                .position(v20)
                .title("SUNY Binghamton"));

        LatLng v21 = new LatLng(43.075076994471395, -77.61806376712576);
        googleMap.addMarker(new MarkerOptions()
                .position(v21)
                .title("Rochester Dome Arena"));

        LatLng v22 = new LatLng(40.80583342003577, -73.57035330358543);
        googleMap.addMarker(new MarkerOptions()
                .position(v22)
                .title("SUNY Old Westbury"));

        LatLng v23 = new LatLng(42.129536929133934, -77.08127085550757);
        googleMap.addMarker(new MarkerOptions()
                .position(v23)
                .title("SUNY Corning Community College"));

        LatLng v24 = new LatLng(42.967057870553376, -78.81230699717213);
        googleMap.addMarker(new MarkerOptions()
                .position(v24)
                .title("University at Buffalo South Campus"));

        LatLng v25 = new LatLng(42.922454230393136, -78.82415269355329);
        googleMap.addMarker(new MarkerOptions()
                .position(v25)
                .title("Delavan Grider Community Center - Buffalo"));

        LatLng v26 = new LatLng(40.885382550756646, -73.84284471002405);
        googleMap.addMarker(new MarkerOptions()
                .position(v26)
                .title("Bronx - Bay Eden Senior Center"));

        LatLng v27 = new LatLng(40.864014814513304, -73.06052295858287);
        googleMap.addMarker(new MarkerOptions()
                .position(v27)
                .title("Suffolk CCC - Brentwood"));

        LatLng v28 = new LatLng(42.65786882347226, -73.76285300851818);
        googleMap.addMarker(new MarkerOptions()
                .position(v28)
                .title("Washington Avenue Armory - Albany, Schenectady, Troy"));

        LatLng v29 = new LatLng(42.689564367087094, -73.84848513971646);
        googleMap.addMarker(new MarkerOptions()
                .position(v29)
                .title("Crossgates Mall, Former Lord & Taylor Lower Leve"));

        LatLng v30 = new LatLng(40.85932905727446, -73.09409930210192);
        googleMap.addMarker(new MarkerOptions()
                .position(v30)
                .title("CVS (Pfizer)"));

        LatLng v31 = new LatLng(40.85480642979937, -73.19613265660088);
        googleMap.addMarker(new MarkerOptions()
                .position(v31)
                .title("CVS (Janssen)"));

        LatLng v32 = new LatLng(40.83631015865751, -73.10833861798682);
        googleMap.addMarker(new MarkerOptions()
                .position(v32)
                .title("CVS (Janssen)"));

        LatLng v33 = new LatLng(40.91190261318803, -73.29934929810524);
        googleMap.addMarker(new MarkerOptions()
                .position(v33)
                .title("CVS (Janssen)"));

        LatLng v34 = new LatLng(40.8124220848377, -73.07899122540738);
        googleMap.addMarker(new MarkerOptions()
                .position(v34)
                .title("CVS (Janssen)"));

        LatLng v35 = new LatLng(40.791520669583306, -73.20112703335472);
        googleMap.addMarker(new MarkerOptions()
                .position(v35)
                .title("CVS (Pfizer)"));

        LatLng v36 = new LatLng(40.89732165274149, -73.33561661795378);
        googleMap.addMarker(new MarkerOptions()
                .position(v36)
                .title("CVS (Pfizer)"));

        LatLng v37 = new LatLng(40.83844465727853, -73.32901959417684);
        googleMap.addMarker(new MarkerOptions()
                .position(v37)
                .title("CVS (Pfizer)"));

        LatLng v38 = new LatLng(40.8678530266926, -73.36510060209717);
        googleMap.addMarker(new MarkerOptions()
                .position(v38)
                .title("CVS (Janssen)"));

        LatLng v39 = new LatLng(40.76849042976082, -72.99578784077517);
        googleMap.addMarker(new MarkerOptions()
                .position(v39)
                .title("CVS (Pfizer)"));

        LatLng v40 = new LatLng(40.74357029085494, -73.26317514078863);
        googleMap.addMarker(new MarkerOptions()
                .position(v40)
                .title("CVS (Pfizer)"));

        LatLng v41 = new LatLng(40.88073606794091, -73.4219556634661);
        googleMap.addMarker(new MarkerOptions()
                .position(v41)
                .title("CVS (Janssen)"));

        LatLng v42 = new LatLng(40.82813033811903, -73.40694056402295);
        googleMap.addMarker(new MarkerOptions()
                .position(v42)
                .title("CVS (Janssen)"));
    }

    @Override
    public void onMapClick(LatLng point) {
        mMap.addMarker(new MarkerOptions().position(point).title("New Marker"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(point));
        Toast.makeText(getApplicationContext(), "point = " + point, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapLongClick(LatLng point) {
        mMap.addMarker(new MarkerOptions().position(point).title("New Marker"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(point));
        Toast.makeText(getApplicationContext(), "point = " + point, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraIdle() {
        //Toast.makeText(getApplicationContext(), mMap.getCameraPosition().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.mapSearchBar);
        String location = locationSearch.getText().toString();
        Geocoder geocoder = new Geocoder(this);

        if (location != null || !location.equals("")) {
            try {
                List<Address> geoResults = geocoder.getFromLocationName(location, 1);
                while (geoResults.size() == 0) {
                    geoResults = geocoder.getFromLocationName(location, 1);
                }
                if (geoResults.size() > 0) {
                    Address address = geoResults.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    Toast.makeText(getApplicationContext(),address.getLatitude() + ", " + address.getLongitude(), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }
}