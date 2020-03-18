package es.tiendaslocales;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TendesActivity extends MainMenu implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Tenda> tendes;
    private Tenda tenda;
    private LatLng latLng;
    private TendesDAO tendesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tendes);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_tendes);
        mapFragment.getMapAsync(this);
        Bundle bundle=this.getIntent().getExtras();
        tendesDAO=new TendesDAO(this);
        tendes=tendesDAO.getTendesPoblacio(bundle.getString("poblacio"));
        Log.d("TendesActivity","onCreate");
        Log.d("TendesActivity","tendes.size(): "+tendes.size()); // 0?

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        Log.d("TendesActivity","onMapReady Start");
        Log.d("TendesActivity","tendes.size(): "+tendes.size()); // 0?

        for(int i=0;i<tendes.size();i++){
            latLng = new LatLng((float)tendes.get(i).getLat(),(float)tendes.get(i).getLon());
            Log.d("TendesActivity","latLng:"+latLng);
            mMap.addMarker(new MarkerOptions().position(latLng).title(tendes.get(i).getNom()).snippet(tendes.get(i).getTelefon()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }

    @Override
    public boolean onMarkerClick(Marker marker){
        Intent intent=new Intent(this,InfoTendaActivity.class);
        Bundle b=new Bundle();
        b.putString("Tenda",marker.getSnippet());
        intent.putExtras(b);
        startActivity(intent);
        return false;
    }
}
