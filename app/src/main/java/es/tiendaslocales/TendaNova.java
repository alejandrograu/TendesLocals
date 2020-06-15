package es.tiendaslocales;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;

import static es.tiendaslocales.TendesActivity.tendaNova;
import static es.tiendaslocales.TendesActivity.tendes;
import static es.tiendaslocales.TendesActivity.tendesDAO;

public class TendaNova extends AppCompatActivity {

    EditText editName, editAdressa, editTelefon, editTipus;
    ImageView imgGallery, imgCamera, imgAccept, imgCancel, imgTenda;
    Button btnAccpet, btnCancel;
    private static final int PICK_IMAGE=100;
    public final static int RESP_TOMAR_FOTO=1;
    Uri imageUri;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenda_nova);
        imageUri=Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.iconshop);

        imgGallery=findViewById(R.id.imgGallery);
        imgCamera=findViewById(R.id.imgCamera);
        imgTenda=findViewById(R.id.imgTenda);
        editName=(EditText) findViewById(R.id.editName);
        editAdressa=(EditText) findViewById(R.id.editAdressa);
        editTelefon=(EditText) findViewById(R.id.editTelefon);
        editTipus=(EditText) findViewById(R.id.editTipus);
        imgAccept=(ImageView) findViewById(R.id.img_accept);
        imgCancel=(ImageView) findViewById(R.id.img_cancel);
        Log.d("TendaNova","Start TendaNova");

        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });


    }

    public TendaNova(final Context context, final LatLng point, final GoogleMap mMap) {

        imgAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nom=editName.getText().toString();
                String adressa=editAdressa.getText().toString();
                String telefon=editTelefon.getText().toString();
                String tipus=editTipus.getText().toString();

                if(nom.length()>=3 && telefon.length()>=9){
                    Marker marker=mMap.addMarker(new MarkerOptions().position(point).title(nom).snippet(telefon));
                    tendaNova=new Tenda();
                    tendaNova.setCodi(marker.getId());
                    tendaNova.setNom(nom);
                    tendaNova.setLat(point.latitude);
                    tendaNova.setLon(point.longitude);
                    tendaNova.setPoblacio(nom);
                    tendaNova.setAdresa(adressa);
                    tendaNova.setTelefon(telefon);
                    tendaNova.setFoto("Tenda_"+editName.getText().toString());
                    tendaNova.setTipus(tipus);

                    if(tendesDAO.insertarTenda(tendaNova)){
                        tendes.add(tendaNova);
                    }
                    finish();

                }else{
                    if(nom.length()<3){
                        displayToast(context, "El nom ha de tindre mes de 3 lletres.");
                    }else if(telefon.length()<5){
                        displayToast(context, "El telefon ha de tindre al menys 9 numeros.");
                    }
                }
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void checkExternalStoragePermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Log.e("TendaNova", "Permission not granted WRITE_EXTERNAL_STORAGE.");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        225);
            }
        }if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("TendaNova", "Permission not granted CAMERA.");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        226);
            }
        }
    }

    private void openGallery(){
        Intent gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
        Log.d("TendaNova","PICK_IMAGE="+PICK_IMAGE);
    }

    private void openCamera(){
        File fotoFile=new File(context.getFilesDir(),"fotoTenda");
        try {
            fotoFile.createNewFile();
        }catch(IOException ex){
            Log.e("TendaNova","Error creant fitxer, "+ex);
        }
        String pathFotoFile=fotoFile.getAbsolutePath();
        Uri fotoUri=Uri.fromFile(fotoFile);
        Log.d("TendaNova","fotoUri="+fotoUri);
        Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(camera.resolveActivity(getPackageManager()) != null){
            Log.d("TendaNova","openCamera if resolveActivity not null");
            camera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            //startActivityForResult(camera, 0);
            startActivityForResult(camera, RESP_TOMAR_FOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TendaNova","onActivityRestult START!");
        Log.d("TendaNova","resultCode="+resultCode+" requestCode="+requestCode);
        if(resultCode==RESULT_OK && (requestCode==PICK_IMAGE || requestCode==RESP_TOMAR_FOTO )){
            imageUri=data.getData();
            imgTenda.setImageURI(imageUri);
            Log.d("TendaNova","requestCode="+requestCode);
            Log.d("TendaNova","resultCode="+resultCode);
            Log.d("TendaNova","data="+data);
            Log.d("TendaNova","imageUri="+imageUri);
            Log.d("TendaNova","imgTenda="+imgTenda);
        }
    }

    public void displayToast(Context context,String text){
        int duration= Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context,text, duration);
        toast.show();
    }
}
