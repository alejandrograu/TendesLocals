package es.tiendaslocales;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;

import static es.tiendaslocales.MainActivity.Manager;
import static es.tiendaslocales.MainActivity.favorit;
import static es.tiendaslocales.MainActivity.numOption;
import static es.tiendaslocales.MainActivity.usersDB;
import static es.tiendaslocales.MainActivity.usuari;

public class PoblacionsActivity extends MainMenu implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapLongClickListener {

    private static final int LOCATION_REQUEST_CODE=1;
    private Marker markerPoble;
    private LatLng homeLatLng = new LatLng(39.470120, -0.377187);
    private GoogleMap mMap;
    static ArrayList<Poblacio> poblacions;
    private LatLng latLng;
    static PoblacionsDAO myPoblacionsDAO;
    static Poblacio poblenou;
    TextView txtLatLng, txtFavorit;
    ImageView iconstar;
    Uri imageUriTrue, imageUriFalse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poblacions);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_poblacions);
        mapFragment.getMapAsync(this);

        for( User user:usersDB){
            if(user.getNom().equals(usuari)){
                favorit=user.getFavorit();
            }
        }
        Log.d("PoblacionsActivity","onCreate START!");

        myPoblacionsDAO=new PoblacionsDAO(this);
        poblacions=myPoblacionsDAO.getPoblacions();

        imageUriTrue= Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.star_icon_1);
        imageUriFalse= Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.star_icon_0);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        iconstar=(ImageView) findViewById(R.id.imgstaricon);
        iconstar.setVisibility(View.INVISIBLE);
        txtFavorit=(TextView) findViewById(R.id.txtFavorit);
        txtFavorit.setVisibility(View.INVISIBLE);
        txtLatLng=findViewById(R.id.textView_latlng);
        //markerFavorit=mMap.addMarker(new MarkerOptions().position(new LatLng(39.470120, -0.377187)).title("VALENCIA").snippet("Comunitat Valenciana").icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_compass)).anchor(0.5f, 0.5f));


        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, 15));

        // Controles UI i Permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
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

        // Eventos
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        // Marcadores
        mMap.addMarker(new MarkerOptions().position(new LatLng(39.470120, -0.377187)).title("VALENCIA").snippet("Comunitat Valenciana").icon(BitmapDescriptorFactory
                .fromResource(android.R.drawable.ic_menu_compass)).anchor(0.5f, 0.5f));

        for(int i=0;i<poblacions.size();i++){
            latLng = new LatLng(poblacions.get(i).getLat(),poblacions.get(i).getLon());
            mMap.addMarker(new MarkerOptions().position(latLng).title(poblacions.get(i).getPoblacio()).snippet(poblacions.get(i).getCp()));
            if(poblacions.get(i).getCodi().equals(favorit)){
                homeLatLng=latLng;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, 15));
            }
        }
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

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

        //if(marker.equals(markerFavorit)){
        if(marker.getId().equals(favorit)){
            iconstar.setImageURI(imageUriTrue);
            txtFavorit.setVisibility(View.VISIBLE);
        }else{
            iconstar.setImageURI(imageUriFalse);
            txtFavorit.setVisibility(View.INVISIBLE);
        }

        iconstar.setVisibility(View.VISIBLE);
        txtLatLng.setText(marker.getPosition().toString());

        Log.d("PoblacionsActivity","onMarkerClick marker.getId()="+markerPoble.getId());
        Log.d("PoblacionsActivity","onMarkerClick marker.getTitle()="+markerPoble.getTitle());
        Log.d("PoblacionsActivity","onMarkerClick marker.getSnippet()="+markerPoble.getSnippet());

        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        iconstar.setVisibility(View.INVISIBLE);
        txtFavorit.setVisibility(View.INVISIBLE);
        txtLatLng.setText(latLng.toString());
    }

    public void back(View view) {
        this.finish();
    }

    public void favorit(View view) {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(homeLatLng));
    }


    public void seticonstar(View view){
       homeLatLng= new LatLng(markerPoble.getPosition().latitude, markerPoble.getPosition().longitude);
       favorit=markerPoble.getId();
       iconstar.setImageURI(imageUriTrue);
       txtFavorit.setVisibility(View.VISIBLE);

        for (int i = 0; i <usersDB.size() ; i++) {
            if(usersDB.get(i).getNom().equals(usuari)){
                usersDB.get(i).setFavorit(favorit);
            }
        }
        Manager.insertarDades("favorit", favorit);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        try{
            if(!markerPoble.getId().equals("m0")){
                Intent intent=new Intent(this,TendesActivity.class);
                Bundle b=new Bundle();
                b.putString("poblacio",markerPoble.getId());
                b.putString("cp",markerPoble.getSnippet());
                intent.putExtras(b);
                startActivity(intent);
            }
        }catch (Exception e){
            Log.e("PoblacionsActivity","onInfoWindowClick ERROR => "+e);
        }

    }

    @Override
    public void onMapLongClick(LatLng point) {

        /*Intent intent=new Intent(this, PoblacioNova.class);
        Bundle b=new Bundle();
        b.putDouble("lat", point.latitude);
        b.putDouble("lon", point.longitude);
        intent.putExtras(b);
        startActivity(intent);
        mMap.addMarker(new MarkerOptions().position(point).draggable(true));*/

        Log.d("PoblacionsActivity","Start onMapLongClick");
        new PoblacioNova(this, point, mMap);

        /*if(dades!=null){
            markerPoble=mMap.addMarker(new MarkerOptions().position(point).title(dades[0]).snippet(dades[1]));
            new FileManager().insertDB(this,markerPoble, "poblacions");

            //mMap.addMarker(new MarkerOptions().position(point).title(dades[0]).snippet(dades[1]));


            //markerPoble.showInfoWindow();
        }*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        numOption=0;
    }
}
