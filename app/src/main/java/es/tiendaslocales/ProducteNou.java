package es.tiendaslocales;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static es.tiendaslocales.MainActivity.Manager;
import static es.tiendaslocales.MainActivity.productesDB;

public class ProducteNou extends AppCompatActivity {

    private String codi;
    private String nom;
    private String poblacio;
    private String tenda;
    private String pvp;
    private String categoria;
    private String subcategoria;
    private String descripcio;
    private String imageProducte;

    DatabaseReference producteReference= FirebaseDatabase.getInstance().getReference().child("productes");
    EditText editNom, editPoblacio, editTenda, editPvp, editDescripcio, editCategoria, editSubCategoria;
    ImageView imageViewProducte;
    ImageButton imgGallery, imgCamera;
    Button btnInsertar, btnCancelar;
    private static final int PICK_IMAGE=100;
    public final static int RESP_TOMAR_FOTO=1;
    Uri imageUri;
    Context context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producte_nou);

        imageUri=Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.icon_alimentacion);
        imageProducte="";

        imgGallery=findViewById(R.id.imgGallery);
        imgCamera=findViewById(R.id.imgCamera);
        editPoblacio=findViewById(R.id.editName);
        editTenda=findViewById(R.id.editPassword);
        editPvp=findViewById(R.id.editLastName);
        editCategoria=findViewById(R.id.editAdress);
        editSubCategoria=findViewById(R.id.editeMail);
        editDescripcio=findViewById(R.id.editPhone);
        imageViewProducte=findViewById(R.id.imgProfile);
        btnInsertar=findViewById(R.id.btnInsertar);
        btnCancelar=findViewById(R.id.btnCancelar);


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

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            // Verifica permisos para Android 6+
            checkExternalStoragePermission();
        }
    }

    private void checkExternalStoragePermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Log.e("ProducteNou", "Permission not granted WRITE_EXTERNAL_STORAGE.");
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
            Log.e("ProducteNou", "Permission not granted CAMERA.");
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
        Log.d("ProducteNou","openGallery() PICK_IMAGE="+PICK_IMAGE);
    }

    private void openCamera(){
        File fotoFile=new File(context.getFilesDir(),"imageProducte");
        try {
            fotoFile.createNewFile();
            Log.d("ProducteNou","openCamera() fitxer Creat!");
        }catch(IOException ex){
            Log.e("ProducteNou","openCamera() Error creant fitxer, "+ex);
        }
        String pathFotoFile=fotoFile.getAbsolutePath();
        Uri fotoUri=Uri.fromFile(fotoFile);
        Log.d("ProducteNou","fotoUri="+fotoUri);
        Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(camera.resolveActivity(getPackageManager()) != null){
            Log.d("ProducteNou","openCamera if resolveActivity not null");
            camera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            //startActivityForResult(camera, 0);
            startActivityForResult(camera, RESP_TOMAR_FOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ProducteNou","onActivityRestult START!");
        Log.d("ProducteNou","resultCode="+resultCode+" requestCode="+requestCode);
        if(resultCode==RESULT_OK && (requestCode==PICK_IMAGE || requestCode==RESP_TOMAR_FOTO )){
            imageUri=data.getData();
            imageViewProducte.setImageURI(imageUri);
            imageProducte=imageUri.toString();
            Log.d("ProducteNou","requestCode="+requestCode);
            Log.d("ProducteNou","resultCode="+resultCode);
            Log.d("ProducteNou","data="+data);
            Log.d("ProducteNou","imageUri="+imageUri);
            Log.d("ProducteNou","imageViewProducte="+imageViewProducte);
        }
    }


    public void InsertarProducte(View view){

        Producte nouProducte=new Producte();

        nom=editNom.getText().toString();
        poblacio=editPoblacio.getText().toString();
        tenda=editTenda.getText().toString();
        pvp=editPvp.getText().toString();
        categoria=editCategoria.getText().toString();
        subcategoria=editSubCategoria.getText().toString();
        descripcio=editDescripcio.getText().toString();

        nouProducte.setNom(nom);
        nouProducte.setPoblacio(poblacio);
        nouProducte.setTenda(tenda);
        nouProducte.setPvp(pvp);
        nouProducte.setCategoria(categoria);
        nouProducte.setSubcategoria(subcategoria);
        nouProducte.setDescripcio(descripcio);
        nouProducte.setImage(imageProducte);

        try {

            cargarDatosFirebase(nouProducte);
            productesDB.add(nouProducte);
            Manager.uploadImg(this,imageUri, "images/productes/producte_"+nom);
            displayToast("Producte Guardat Correctament!");
            finish();

        }catch (Exception e){
            Log.e("ProducteNou","Error =>"+e);
        }
    }

    private void cargarDatosFirebase(Producte nouProducte) {
        Log.d("nouProducte","Start cargarDatosFirebase");
        Map<String, Object> dadesProducte=new HashMap<>();
        dadesProducte.put("poblacio", nouProducte.getPoblacio());
        dadesProducte.put("tenda", nouProducte.getTenda());
        dadesProducte.put("pvp", nouProducte.getPvp());
        dadesProducte.put("categoria", nouProducte.getCategoria());
        dadesProducte.put("subcategoria", nouProducte.getSubcategoria());
        dadesProducte.put("descripcio", nouProducte.getDescripcio());
        dadesProducte.put("image", nouProducte.getImage());

        producteReference.push().setValue(dadesProducte);
        Log.d("ProducteNou","Producte carregat!");
        displayToast("Producte Carregat!!");
    }

    public void cancel(View view){
        finish();
    }

    public void displayToast(String text){
        int duration= Toast.LENGTH_LONG;
        Toast toast= Toast.makeText(this,text, duration);
        toast.show();
    }
}
