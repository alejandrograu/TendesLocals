package es.tiendaslocales;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class MainActivity extends MainMenu {
    static int numOption=0;

    static int DATABASE_VERSION;
    static ArrayList<String> dataBaseSQL=new ArrayList<>();
    static String usuari,clau, favorit;
    static ArrayList<User> usersDB=new ArrayList<>();
    static ImageView imgPerfil_toolbar_class;
    static File local_DBfile_user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgPerfil_toolbar_class=findViewById(R.id.imgPerfil_toolbar_activity);

        try {
            usersDB=new FileManager().usuaris();
            DATABASE_VERSION=new FileManager().FileVersion();
            Log.d("MainActivity","DATABASE_VERSION="+DATABASE_VERSION);
            dataBaseSQL=new FileManager().FileDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("MainActivity","userDB.size()= "+usersDB.size());

        if ((new FileManager().fileUserExists(this))==null){
            Intent intent=new Intent(this, Login.class);
            startActivity(intent);
        }else{
            displayToast("Benvingut de nou \""+usuari+"\"!");
        }

    }
    public void clickBtntoMap(View view) {
        if(usuari!=null){
            numOption=1;
            Log.d("MainActivity","usuari="+usuari+" DATABASE_VERSION="+DATABASE_VERSION);
            Intent intent=new Intent(this, PoblacionsActivity.class);
            startActivity(intent);
        }else{

            Intent intent=new Intent(this, Login.class);
            startActivity(intent);
        }

    }

    public void clickBtntoShop(View view) {
        if(usuari!=null){
            numOption=2;
            Intent intent=new Intent(this, PoblacionsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
            Intent intent=new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
