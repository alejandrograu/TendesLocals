package es.tiendaslocales;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME="tendeslocals";
    private static int DATABASE_VERSION=DATABASE_VERSION();
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final StorageReference storageRef = storage.getReference();
    private static final StorageReference dbRef=storageRef.child("db/TLDB.sql");
    private static final StorageReference verRef=storageRef.child("versionDB");
    private static final StorageReference imagesRef = storageRef.child("images");

    /*
    String sqlCreateTablePoblacions="CREATE TABLE poblacions (codi TEXT PRIMARY KEY, nom TEXT NOT NULL, cp TEXT, lat REAL, lon REAL)";
    String sqlCreateTableTendes ="CREATE TABLE tendes (codi TEXT PRIMARY KEY, nom TEXT NOT NULL, lat REAL, lon REAL, poblacio TEXT, telefon TEXT, adresa TEXT, foto TEXT, tipus TEXT, FOREIGN KEY(poblacio) REFERENCES poblacions(codi))";
    String sqlInsPoblacio1="INSERT INTO poblacions ('codi','nom','cp','lat','lon') VALUES ('m1','Tavernes de la Valldigna','46760','39.0727884','-0.274921')";
    String sqlInsPoblacio2="INSERT INTO poblacions ('codi','nom','cp','lat','lon') VALUES ('m2','Benifairo de la Valldigna','46791','39.0561748','-0.3084038')";
    String sqlInsPoblacio3="INSERT INTO poblacions ('codi','nom','cp','lat','lon') VALUES ('m3','Simat de la Valldigna','46750','39.0444525','-0.3149885')";
    String sqlInsPoblacio4="INSERT INTO poblacions ('codi','nom','cp','lat','lon') VALUES ('m4','Xeraco','46770','39.0329863','-0.2207621')";
    String sqlInsTenda1 = "INSERT INTO tendes ('codi','nom','lat','lon','poblacio','telefon','adresa','foto','tipus') " +
            "VALUES ('t1','Estrela / Romera','39.1217030','-0.4514444','p1'," +
            "'962430014','C/ Julian Ribera, 42','url foto','Farmacia')";

    String sqlInsTenda2 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t2', 'Escrivà Hermanos', '39.121083', '-0.449172', 'm1', " +
            " '962431932', 'C/ Comandante Hernandez, 26' ,'url foto','Farmacia')";

    String sqlInsTenda3 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t3', 'Mercadona', '39.0757036', '-0.2644538', 'm1', " +
            " '962820069', 'C/ Cami la Dula, 23' ,'url foto','Supermercat')";

    String sqlInsTenda4 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t4', 'Mercadona', '39.0707269', '-0.270149', 'm1', " +
            " '962820407', 'Gran Via de les Germanies, 12' ,'url foto','Supermercat')";

    String sqlInsTenda5 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t5', 'Masymas-Fornés', '39.0717597', '-0.2687574', 'm1', " +
            " '962837560', 'Gran Via de les Germanies, 29' ,'url foto','Supermercat')";

    String sqlInsTenda6 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t6', 'Charter (Consum)', '39.0733391', '-0.270763', 'm1', " +
            " '960234332', 'C/ Major, 39' ,'url foto','Supermercat')";

    String sqlInsTenda7 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t7', 'Fruteria-Verduleria Major', '39.0710267', '-0.2688675', 'm1', " +
            " '960000000', 'C/ Major, 33' ,'url foto','Fruteria-Verduleria')";

    String sqlInsTenda8 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t8', 'Fruteria-Verduleria Tavernes', '39.0716047', '-0.2689426', 'm1', " +
            " '960000000', 'Avinguda de les Germanies, 22' ,'url foto','Fruteria-Verduleria')";

    String sqlInsTenda9 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t9', 'Diaz Alberola C.B.', '39.0531294', '-0.3033352', 'm2', " +
            " '962810437', 'C/ Ample, 13' ,'url foto','Farmacia')";

    String sqlInsTenda10 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t10', 'Charter (Consum)', '39.0549752', '-0.3076077', 'm2', " +
            " '962810750', 'C/ Ricardo Romero, 34' ,'url foto','Supermercat')";

    String sqlInsTenda11 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t11', 'Eco-Finca', '39.0537784', '-0.3090068', 'm2', " +
            " '629441609', 'C/ Sant Francesc, 42' ,'url foto','Fruteria')";

    String sqlInsTenda12 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t12', 'DIA', '39.043755', '-0.3177501', 'm3', " +
            " '912170453', 'C/ Ausias March, 6' ,'url foto','Supermercat')";

    String sqlInsTenda13 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t13', 'Charter (Consum)', '39.0419551', '-0.3135658', 'm3', " +
            " '962810352', 'C/ Major, 26' ,'url foto','Supermercat')";

    String sqlInsTenda14 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t14', 'Masymas', '39.0362531', '-0.2183305', 'm4', " +
            " '962891064', 'C/ Ronda de Bruguieres, 85' ,'url foto','Supermercat')";

    String sqlInsTenda15 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t15', 'Spar', '39.0317817', '-0.2229372', 'm4', " +
            " '618820474', 'C/ Nou d´Octubre, 6' ,'url foto','Supermercat')";

    String sqlInsTenda16 = "INSERT INTO tendes (codi, nom, lat, lon, poblacio, telefon, adresa, foto, tipus) " +
            "VALUES ('t16', 'Mª Carmen Rubio Verdeguer', '39.0294891', '-0.2188083', 'm4', " +
            " '962891181', 'C/ de la Barcella, 19','url foto','Farmacia')";

     */


    public MySQLiteHelper(@Nullable Context context ) throws IOException {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.d("SQL: ","context");

        File localFile= File.createTempFile("version",".tmp");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        BufferedReader fileRead= null;
        try {
            fileRead = new BufferedReader(new FileReader(String.valueOf(dbRef)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> comanda=new ArrayList<>();
        try {
            String linia=fileRead.readLine();
            while(linia!=null){
                db.execSQL(linia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("SQL: ","onCreate");
        /*db.execSQL(sqlCreateTablePoblacions);
        db.execSQL(sqlCreateTableTendes);
        db.execSQL(sqlInsPoblacio1);
        db.execSQL(sqlInsPoblacio2);
        db.execSQL(sqlInsPoblacio3);
        db.execSQL(sqlInsPoblacio4);
        db.execSQL(sqlInsTenda1);
        db.execSQL(sqlInsTenda2);
        db.execSQL(sqlInsTenda3);
        db.execSQL(sqlInsTenda4);
        db.execSQL(sqlInsTenda5);
        db.execSQL(sqlInsTenda6);
        db.execSQL(sqlInsTenda7);
        db.execSQL(sqlInsTenda8);
        db.execSQL(sqlInsTenda9);
        db.execSQL(sqlInsTenda10);
        db.execSQL(sqlInsTenda11);
        db.execSQL(sqlInsTenda12);
        db.execSQL(sqlInsTenda13);
        db.execSQL(sqlInsTenda14);
        db.execSQL(sqlInsTenda15);
        db.execSQL(sqlInsTenda16);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        BufferedReader fileRead= null;
        try {
            fileRead = new BufferedReader(new FileReader(String.valueOf(dbRef)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> comanda=new ArrayList<>();
        try {
            String linia=fileRead.readLine();
            while(linia!=null){
                db.execSQL(linia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("SQL: ","onUpgrade");
        /*db.execSQL("DROP TABLE IF EXISTS tendes ");
        db.execSQL("DROP TABLE IF EXISTS poblacions ");
        db.execSQL(sqlCreateTablePoblacions);
        db.execSQL(sqlCreateTableTendes);
        db.execSQL(sqlInsPoblacio1);
        db.execSQL(sqlInsPoblacio2);
        db.execSQL(sqlInsPoblacio3);
        db.execSQL(sqlInsPoblacio4);
        db.execSQL(sqlInsTenda1);
        db.execSQL(sqlInsTenda2);
        db.execSQL(sqlInsTenda3);
        db.execSQL(sqlInsTenda4);
        db.execSQL(sqlInsTenda5);
        db.execSQL(sqlInsTenda6);
        db.execSQL(sqlInsTenda7);
        db.execSQL(sqlInsTenda8);
        db.execSQL(sqlInsTenda9);
        db.execSQL(sqlInsTenda10);
        db.execSQL(sqlInsTenda11);
        db.execSQL(sqlInsTenda12);
        db.execSQL(sqlInsTenda13);
        db.execSQL(sqlInsTenda14);
        db.execSQL(sqlInsTenda15);
        db.execSQL(sqlInsTenda16);*/
    }

    public ArrayList<String> FileManager() throws IOException {
        BufferedReader fileRead=new BufferedReader(new FileReader(String.valueOf(dbRef)));
        ArrayList<String> comanda=new ArrayList<>();
        try {
            String linia=fileRead.readLine();
            while(linia!=null){
                comanda.add(linia);
                linia=fileRead.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileRead.close();
        return comanda;
    }

    public int DATABASE_VERSION() throws IOException {
        BufferedReader fileRead=new BufferedReader(new FileReader(String.valueOf(verRef)));
        int versio = 0;
        try {
            versio=Integer.parseInt(fileRead.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileRead.close();
        return versio;
    }
}
