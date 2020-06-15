package es.tiendaslocales;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends MainMenu {
    static int numOption=0;

    static int DATABASE_VERSION;
    static ArrayList<String> dataBaseSQL=new ArrayList<>();
    static ArrayList<String> userBaseSQL=new ArrayList<>();
    static ArrayList<User> usersDB=new ArrayList<>();
    static String usuari,clau, favorit;
    static ImageView imgPerfil_toolbar_class;
    static File local_DBfile_user, local_file_user, local_imgPerfil_user;
    static FileManager Manager;
    static ArrayList<Producte> productesDB=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgPerfil_toolbar_class=findViewById(R.id.imgPerfil_toolbar_activity);
        local_file_user=new File(this.getFilesDir(),"user");

        try {
            Manager=new FileManager();
            DATABASE_VERSION=Manager.FileVersion();
            Log.d("MainActivity","DATABASE_VERSION="+DATABASE_VERSION);
            dataBaseSQL=Manager.FileDatabase();
            usersDB=Manager.usuaris();

            //Manager.concatDB();
            //Log.d("MainActivity","userBaseSQL="+userBaseSQL.toString());
            //Log.d("MainActivity","dataBaseSQL="+dataBaseSQL.toString());
            //Log.d("MainActivity","dataBaseSQL="+dataBaseSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            usuari=Manager.fileUserExists(this);
            Log.d("MainActivity","userBaseSQL="+userBaseSQL.toString());
            Log.d("MainActivity","dataBaseSQL="+dataBaseSQL);
        } catch (Exception e){
            e.printStackTrace();
        }

        Log.d("MainActivity","userDB.size()= "+usersDB.size());

        if (usuari==null){
            Log.d("MainActivity","Manager.fileUserExists()=NULL =>"+usuari);
            Intent intent=new Intent(this, Login.class);
            startActivity(intent);
        }else{
            try {
                Log.d("MainActivity","usuari="+usuari);
                displayToast("Benvingut de nou \""+usuari+"\"!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("MainActivity","Manager.fileUserExists() =>"+usuari);
        }

    }
    public void clickBtntoMap(View view) {
        if(usuari!=null){
            numOption=1;
            Log.d("MainActivity","usuari="+usuari+" DATABASE_VERSION="+DATABASE_VERSION);
            Log.d("MainActivity","dataBaseSQL="+dataBaseSQL.get(dataBaseSQL.size()-1));

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
            Intent intent=new Intent(this, ShopsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
            Intent intent=new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
