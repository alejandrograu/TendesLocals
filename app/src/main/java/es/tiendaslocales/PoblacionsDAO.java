package es.tiendaslocales;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PoblacionsDAO {
    private MySQLiteHelper connexio;
    private SQLiteDatabase db;
    ArrayList<Poblacio> llistaPoblacions;


    public PoblacionsDAO(Context context){
        connexio=new MySQLiteHelper(context);
        db=connexio.getWritableDatabase();
    }
    public ArrayList<Poblacio> getPoblacions(){
        String sql="SELECT * FROM poblacions";
        Cursor c=db.rawQuery(sql,null);
        llistaPoblacions=new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                Poblacio m = new Poblacio();
                m.setCodi(c.getString(0));
                m.setPoblacio(c.getString(1));
                m.setCp(c.getString(2));
                m.setLat(c.getDouble(3));
                m.setLon(c.getDouble(4));
                llistaPoblacions.add(m);
            }while(c.moveToNext());
        }
        return llistaPoblacions;
    }
    public boolean insertPoblacio(Poblacio poblacio){
        String codi=poblacio.getCodi();
        String nom=poblacio.getPoblacio();
        String cp=poblacio.getCp();
        double lat=poblacio.getLat();
        double lon=poblacio.getLon();
        String sql="INSERT INTO poblacions (codi, nom, cp, lat, lon) VALUE ('"+codi+"','"+nom+"','"+cp+"','"+lat+"','"+lon+"');";
        db.execSQL(sql);
        return true;
    }
    public boolean deletePoblacio(String key){
        return false;
    }
    public boolean updatePoblacio(String key){
        return false;
    }
    public void closeDB(){
        db.close();
    }
}
