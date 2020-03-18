package es.tiendaslocales;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class PoblacionsActivity extends MainMenu implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Poblacio> poblacions;
    private Poblacio poble;
    private LatLng latLng;
    private PoblacionsDAO myPoblacionsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poblacions);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_poblacions);
        mapFragment.getMapAsync(this);
        myPoblacionsDAO=new PoblacionsDAO(this);
        poblacions=myPoblacionsDAO.getPoblacions();
        Log.d("PoblacionsActivity:","poblacions.size(): "+poblacions.size()); // 0?

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
        mMap.setOnMarkerClickListener(this);

        for(int i=0;i<poblacions.size();i++){
            latLng = new LatLng(poblacions.get(i).getLat(),poblacions.get(i).getLon());
            mMap.addMarker(new MarkerOptions().position(latLng).title(poblacions.get(i).getPoblacio()).snippet(poblacions.get(i).getCp()));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    @Override
    public boolean onMarkerClick(Marker marker){
        Intent intent=new Intent(this,TendesActivity.class);
        Bundle b=new Bundle();
        b.putString("poblacio",marker.getId());
        intent.putExtras(b);
        startActivity(intent);
        return false;
    }
}
