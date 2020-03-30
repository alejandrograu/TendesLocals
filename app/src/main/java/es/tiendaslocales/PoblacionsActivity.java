package es.tiendaslocales;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

public class PoblacionsActivity extends MainMenu implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final int LOCATION_REQUEST_CODE=1;
    private Marker markerPoble;
    private final LatLng home = new LatLng(39.470120, -0.377187);
    private GoogleMap mMap;
    private ArrayList<Poblacio> poblacions;
    private Poblacio poble;
    private LatLng latLng;
    private PoblacionsDAO myPoblacionsDAO;
    String nomTenda;
    Button infobtn3;
    TextView txtLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poblacions);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_poblacions);
        mapFragment.getMapAsync(this);
        Log.d("PoblacionsActivity","onCreate");
        try {
            myPoblacionsDAO=new PoblacionsDAO(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        poblacions=myPoblacionsDAO.getPoblacions();
        Log.d("PoblacionsActivity","poblacions.size(): "+poblacions.size());

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
        infobtn3=(Button)findViewById(R.id.button3);
        infobtn3.setVisibility(View.INVISIBLE);
        txtLatLng=findViewById(R.id.textView_latlng);

        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 15));

        // Controles UI
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicatiu
            } else {
                // Solicitar permis
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Marcadores
        mMap.addMarker(new MarkerOptions().position(new LatLng(39.470120, -0.377187)).title("CV").snippet("Comunitat Valenciana").icon(BitmapDescriptorFactory
                .fromResource(android.R.drawable.ic_menu_compass)).anchor(0.5f, 0.5f));

        for(int i=0;i<poblacions.size();i++){
            latLng = new LatLng(poblacions.get(i).getLat(),poblacions.get(i).getLon());
            mMap.addMarker(new MarkerOptions().position(latLng).title(poblacions.get(i).getPoblacio()).snippet(poblacions.get(i).getCp()));
        }
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
        }

    }

    // Permisos
    //@Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int [] grantResults){
        if(requestCode == LOCATION_REQUEST_CODE){
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker){
        markerPoble=marker;
        infobtn3.setVisibility(View.VISIBLE);
        txtLatLng.setText(marker.getPosition().toString());

        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        infobtn3.setVisibility(View.INVISIBLE);
        txtLatLng.setText(latLng.toString());

    }

    public void back(View view) {
    }

    public void favorit(View view) {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(home));
    }

    public void entrar(View view) {
        Intent intent=new Intent(this,TendesActivity.class);
        Bundle b=new Bundle();
        b.putString("poblacio",markerPoble.getId());
        b.putString("cp",markerPoble.getSnippet());
        Log.d("PoblacionsActivity","onMarkerClick marker.getId()="+markerPoble.getId());
        Log.d("PoblacionsActivity","onMarkerClick marker.getTitle()="+markerPoble.getTitle());
        Log.d("PoblacionsActivity","onMarkerClick marker.getSnippet()="+markerPoble.getSnippet());

        intent.putExtras(b);
        startActivity(intent);

    }
}
