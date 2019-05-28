package com.gopglemapsunil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gopglemapsunil.model.LattitudeLongitude;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AutoCompleteTextView etCity;
    private Button btnSearch;
    private List<LattitudeLongitude> lattitudeLongitudeList;
    Marker markername;
    CameraUpdate center, zoom;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        etCity = findViewById(R.id.etCity);
        btnSearch = findViewById(R.id.btnSearch);
        
        fillArrayListAndSetAdapter();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etCity.getText().toString()))
                {
                    etCity.setError("please enter a place name");
                    return;
                }

                int position = SearchArrayList(etCity.getText().toString());
                if (position > -1)
                    loadMap(position);
                else
                    Toast.makeText(SearchActivity.this, "Location not found by name : "
                            + etCity.getText().toString(), Toast.LENGTH_SHORT).show();
                
            }




        });
       
    
    }

    private void fillArrayListAndSetAdapter() {
        lattitudeLongitudeList = new ArrayList<>();
        lattitudeLongitudeList.add(new LattitudeLongitude(27.7134481, 85.3241922, "NaagPokhari"));
        lattitudeLongitudeList.add(new LattitudeLongitude(27.7181749, 85.3173212, "Narayamhiti Palace"));
        lattitudeLongitudeList.add(new LattitudeLongitude(27.7127827, 85.3265391, "Hotel Brihaspati"));


        String[] data = new String[lattitudeLongitudeList.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = lattitudeLongitudeList.get(i).getMarker();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                SearchActivity.this,
                android.R.layout.simple_list_item_1,
                data
        );
        etCity.setAdapter(adapter);
        etCity.setThreshold(1);

    }
    private int SearchArrayList(String name) {
        for (int i = 0; i < lattitudeLongitudeList.size(); i++) {
            if (lattitudeLongitudeList.get(i).getMarker().contains(name)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        center = CameraUpdateFactory.newLatLng(new LatLng(27.7172453, 85.3239605));
        zoom = CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }
    private void loadMap(int position) {
        if (markername!=null)
        {
            markername.remove();
        }
        double latitude = lattitudeLongitudeList.get(position).getLat();
        double longitude = lattitudeLongitudeList.get(position).getLon();
        String marker = lattitudeLongitudeList.get(position).getMarker();
        center = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        zoom = CameraUpdateFactory.zoomTo(17);
        markername = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(marker));

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
