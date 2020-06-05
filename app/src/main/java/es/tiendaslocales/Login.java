package es.tiendaslocales;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static es.tiendaslocales.MainActivity.Manager;
import static es.tiendaslocales.MainActivity.clau;
import static es.tiendaslocales.MainActivity.usersDB;
import static es.tiendaslocales.MainActivity.usuari;
import static es.tiendaslocales.MainActivity.favorit;


public class Login extends AppCompatActivity {

    static EditText editName;
    static ImageView imgPerfil_login_class;
    EditText editPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgPerfil_login_class=findViewById(R.id.imgPerfil_login_activity);
        editName=findViewById(R.id.editName);
        editPass=findViewById(R.id.editPass);
    }

    public void login(View view){
        Log.d("Login","Start login");
        String username= String.valueOf(editName.getText());
        String passuser= String.valueOf(editPass.getText());
        if (username.length()>=5 && passuser.length()>=5){
            Boolean valor=false;
            for(User user:usersDB){
                if ((user.getNom().equals(username)) && (user.getClau().equals(passuser)) && !valor) {
                    usuari = username;
                    clau = passuser;
                    try {
                        Manager.usuaris();
                        Manager.fileUserExists(this);

                    }catch(Exception e){
                        Log.e("Login","Error="+e);
                    }
                    displayToast("Acces correcte!\nBenvingut "+usuari+" !!!");
                    valor=true;
                    favorit=user.getFavorit();
                    finish();
                }//else{ displayToast("Nom o Clau INCORRECTA!");}
            }if(!valor)displayToast("Nom o Clau INCORRECTA!");
        }else{
            displayToast("El nom i pass han de tindre al menys 5 caracteres!");
        }
    }

    public void cancel(View view){
        usuari=null;
        finish();
    }

    public void newUser(View view){
        Intent intent=new Intent(this, NewUser.class);
        startActivity(intent);
    }

    public void displayToast(String text){
        int duration= Toast.LENGTH_LONG;
        Toast toast= Toast.makeText(this,text, duration);
        toast.show();
    }
}
