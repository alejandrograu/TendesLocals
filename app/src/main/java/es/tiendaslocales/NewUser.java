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

import static es.tiendaslocales.Login.editName;
import static es.tiendaslocales.Login.imgPerfil_login_class;
import static es.tiendaslocales.MainActivity.Manager;
import static es.tiendaslocales.MainActivity.imgPerfil_toolbar_class;
import static es.tiendaslocales.MainActivity.usersDB;
import static es.tiendaslocales.MainActivity.usuari;

public class NewUser extends AppCompatActivity {
    DatabaseReference usuariReference= FirebaseDatabase.getInstance().getReference().child("users");


    ImageButton imgGallery, imgCamera;
    ImageView imgPerfil_newuser_class;
    EditText editNom, editPass, editApell, editDir, editEmail, editTelefon;
    Button btnLogin, btnCancel;
    private static final int PICK_IMAGE=100;
    public final static int RESP_TOMAR_FOTO=1;
    Uri imageUri;
    Context context=this;
    String pobleFavorit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        imageUri=Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.img_perfil);

        imgGallery=findViewById(R.id.imgGallery);
        imgCamera=findViewById(R.id.imgCamera);
        editNom=findViewById(R.id.editName);
        editPass=findViewById(R.id.editPassword);
        editApell=findViewById(R.id.editLastName);
        editDir=findViewById(R.id.editAdress);
        editEmail=findViewById(R.id.editeMail);
        editTelefon=findViewById(R.id.editPhone);
        btnLogin=findViewById(R.id.btnInsertar);
        btnCancel=findViewById(R.id.btnCancelar);
        imgPerfil_newuser_class=findViewById(R.id.imgProfile);
        pobleFavorit="m0";

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

            Log.e("NewUser", "Permission not granted WRITE_EXTERNAL_STORAGE.");
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
            Log.e("NewUser", "Permission not granted CAMERA.");
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
        Log.d("NewUser","PICK_IMAGE="+PICK_IMAGE);
    }

    private void openCamera(){
        File fotoFile=new File(context.getFilesDir(),"fotoPerfil");
        try {
            fotoFile.createNewFile();
        }catch(IOException ex){
            Log.e("NewUser","Error creant fitxer, "+ex);
        }
        String pathFotoFile=fotoFile.getAbsolutePath();
        Uri fotoUri=Uri.fromFile(fotoFile);
        Log.d("NewUser","fotoUri="+fotoUri);
        Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(camera.resolveActivity(getPackageManager()) != null){
            Log.d("NewUser","openCamera if resolveActivity not null");
            camera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            //startActivityForResult(camera, 0);
            startActivityForResult(camera, RESP_TOMAR_FOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("NewUser","onActivityRestult START!");
        Log.d("NewUser","resultCode="+resultCode+" requestCode="+requestCode);
        if(resultCode==RESULT_OK && (requestCode==PICK_IMAGE || requestCode==RESP_TOMAR_FOTO )){
            imageUri=data.getData();
            imgPerfil_newuser_class.setImageURI(imageUri);
            imgPerfil_toolbar_class.setImageURI(imageUri);
            Log.d("NewUser","requestCode="+requestCode);
            Log.d("NewUser","resultCode="+resultCode);
            Log.d("NewUser","data="+data);
            Log.d("NewUSer","imageUri="+imageUri);
            Log.d("NewUSer","imgPerfil_newuser_class="+imgPerfil_newuser_class);

        }
    }


    public void login(View view){
        User nouUsuari=new User();

        String nom=editNom.getText().toString();
        String clau=editPass.getText().toString();

        nouUsuari.setNom(editNom.getText().toString());
        nouUsuari.setClau(editPass.getText().toString());
        nouUsuari.setCognoms(editApell.getText().toString());
        nouUsuari.setDireccio(editDir.getText().toString());
        nouUsuari.setEmail(editEmail.getText().toString());
        nouUsuari.setTelefon(editTelefon.getText().toString());
        nouUsuari.setImgperfil("imgPerfil_"+editNom.getText().toString());
        nouUsuari.setFavorit(pobleFavorit);

        try {
            if (nom.length()>=5){
                if (clau.length()>=5){
                    if (Manager.isEqual("nom",nom)){
                        displayToast("Nom de usuari ja existent en la Base de Dades.\nTriar altre nom !!");
                    }else{
                        cargarDatosFirebase(nouUsuari);
                        usersDB.add(nouUsuari);
                        usuari=nom;
                        editName.setText(nom);
                        imgPerfil_login_class.setImageURI(imageUri);
                        imgPerfil_toolbar_class.setImageURI(imageUri);
                        Log.d("NewUser","imageUri="+imageUri);

                        Manager.usuaris();
                        Manager.createLocalFileUser(context);
                        Manager.createLocalFileDBUser(context);
                        Manager.uploadDB();
                        Manager.uploadImg(this,imageUri, "images/imgPerfil_"+usuari);

                        displayToast("Usuari Guardat Correctament!");
                        finish();
                    }
                }else{
                    displayToast("La clau es un camp obligatori i\na de tindre al menys 5 caracters!");
                }

                Log.d("*** LOGIN ***","editText= "+usuari);

            }else{
                displayToast("El nom es un camp obligatori i\na de tindre al menys 5 caracters!");
            }
        }catch (Exception e){
            Log.e("NewUser","Error =>"+e);
        }
    }

    private void cargarDatosFirebase(User user) {
        Log.d("NewUser","Start cargarDatosFirebase");
        Map<String, Object> datosUsuari=new HashMap<>();
        datosUsuari.put("nom", user.getNom());
        datosUsuari.put("clau", user.getClau());
        datosUsuari.put("cognoms", user.getCognoms());
        datosUsuari.put("direccio", user.getDireccio());
        datosUsuari.put("email", user.getEmail());
        datosUsuari.put("telefon", user.getTelefon());
        datosUsuari.put("imgperfil", user.getImgperfil());
        datosUsuari.put("favorit",user.getFavorit());

        usuariReference.push().setValue(datosUsuari);
        Log.d("NewUser","Usuari creat!");
        displayToast("Usuari Creat!!");
    }

    public void cancel(View view){
        usuari=null;
        finish();
    }

    public void displayToast(String text){
        int duration= Toast.LENGTH_LONG;
        Toast toast= Toast.makeText(this,text, duration);
        toast.show();
    }


}
