package com.ttcnpm.group28.weatherapp.namquan;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.ttcnpm.group28.weatherapp.R;

import java.net.MalformedURLException;
import java.net.URL;

public class ActivityMapLayers extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static String tile_url = "https://tile.openweathermap.org/map/%s/%d/%d/%d.png?appid=1af5471452978b9b863e32a587c0e7f4";

    private String tileType = "temp_new";
    private TileOverlay tileOver;
    private ImageView scale;
    private RadioGroup mapType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_layers);
        scale = findViewById(R.id.scale);
        mapType = findViewById(R.id.mapType);
        scale.setImageResource(R.drawable.map_temperature);

        String[] tileName = new String[]{"Clouds", "Precipitation", "Wind speed", "Temperature"};
        ArrayAdapter<String> adpt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tileName);

        mapType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbCloud:
                        tileType = "clouds_new";
                        scale.setImageResource(R.drawable.map_cloud);
                        break;
                    case R.id.rbPreci:
                        tileType = "precipitation_new";
                        scale.setImageResource(R.drawable.map_precipitation);
                        break;
                    case R.id.rbWind:
                        tileType = "wind_new";
                        scale.setImageResource(R.drawable.map_windspeed);
                        break;
                    case R.id.rbTemp:
                        tileType = "temp_new";
                        scale.setImageResource(R.drawable.map_temperature);
                        break;

                }
                tileOver.setVisible(false);
                tileOver.remove();

                tileOver=mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new TransparentTileOWM(tileType)));
            }
        });


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //mapFragment.getMapAsync(this);
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

        tileOver = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new TransparentTileOWM(tileType)));
        LatLng quangbinh = new LatLng(17.208195, 106.697664);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quangbinh,6));

    }

    private void setUpMap(){
        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {

                /* Define the URL pattern for the tile images */
                String s = String.format("http://my.image.server/images/%d/%d/%d.png",
                        zoom, x, y);

                if (!checkTileExists(x, y, zoom)) {
                    return null;
                }

                try {
                    return new URL(s);
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
            }
            private boolean checkTileExists(int x, int y, int zoom) {
                int minZoom = 12;
                int maxZoom = 16;

                if ((zoom < minZoom || zoom > maxZoom)) {
                    return false;
                }

                return true;
            }
        };
        mMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));
    }
}
