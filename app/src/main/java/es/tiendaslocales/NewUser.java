package es.tiendaslocales;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static es.tiendaslocales.Login.editName;
import static es.tiendaslocales.MainActivity.usersDB;
import static es.tiendaslocales.MainActivity.usuari;

public class NewUser extends AppCompatActivity {
    DatabaseReference usuariReference= FirebaseDatabase.getInstance().getReference().child("users");


    ImageButton imgGallery, imgCamera;
    ImageView imageProfile;
    EditText editNom, editPass, editApell, editDir, editEmail, editTelefon;
    Button btnLogin, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        imgGallery=findViewById(R.id.imgGallery);
        imgCamera=findViewById(R.id.imgCamera);
        editNom=findViewById(R.id.editName);
        editPass=findViewById(R.id.editPass);
        editApell=findViewById(R.id.editLastName);
        editDir=findViewById(R.id.editAddress);
        editEmail=findViewById(R.id.editEmail);
        editTelefon=findViewById(R.id.editPhone);
        btnLogin=findViewById(R.id.btnLogin);
        btnCancel=findViewById(R.id.btnCancel);
        imageProfile=findViewById(R.id.imgProfile);

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
        nouUsuari.setImgperfil(imageProfile.getTransitionName());
        nouUsuari.setTelefon(editTelefon.getText().toString());




        try {
            if (nom.length()>=5){
                if (clau.length()>=5){
                    if (new FileManager().isEqual("nom",nom)){
                        displayToast("Nom de usuari ja existent en la Base de Dades.\nTriar altre nom !!");
                    }else{
                        cargarDatosFirebase(nouUsuari);
                        usersDB.add(nouUsuari);
                        usuari=nom;
                        editName.setText(nom);
                        displayToast("Usuari Guardat Correctament!");
                        new FileManager().fileUserExists(this);
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
        Map<String, Object> datosUsuari=new HashMap<>();
        datosUsuari.put("nom", user.getNom());
        datosUsuari.put("clau", user.getClau());
        datosUsuari.put("cognoms", user.getCognoms());
        datosUsuari.put("direccio", user.getDireccio());
        datosUsuari.put("email", user.getEmail());
        datosUsuari.put("telefon", user.getTelefon());
        datosUsuari.put("imgperfil", user.getImgperfil());

        usuariReference.push().setValue(datosUsuari);
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
