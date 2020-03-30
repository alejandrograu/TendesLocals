package es.tiendaslocales;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class TendesDAO {
    private MySQLiteHelper connexio;
    static SQLiteDatabase db;
    ArrayList<Tenda> llistaTendes;

    public TendesDAO(Context context) throws IOException {
        connexio=new MySQLiteHelper(context);
        db=connexio.getWritableDatabase();
    }
    static Tenda getTendaTelefon(String telefon){
        String sql="SELECT * FROM tendes WHERE telefon='"+telefon+"';";
        Log.d("TendesDAO","telefon="+telefon);
        Cursor c=db.rawQuery(sql,null);
        Tenda tenda=new Tenda();
        if(c.moveToFirst()) {
            tenda.setCodi(c.getString(0));
            tenda.setNom(c.getString(1));
            tenda.setLat(c.getDouble(2));
            tenda.setLon(c.getDouble(3));
            tenda.setPoblacio(c.getString(4));
            tenda.setTelefon(c.getString(5));
            tenda.setAdresa(c.getString(6));
            tenda.setFoto(c.getString(7));
            tenda.setTipus(c.getString(8));
        }else{
            System.out.println("Error de dades");
        }
        return tenda;
    }

    public ArrayList<Tenda> getTendesPoblacio(String poblacio){
        String sql="SELECT * FROM tendes WHERE poblacio='"+poblacio+"';";
        Log.d("TendesDAO","poblacio="+poblacio);
        Cursor c=db.rawQuery(sql,null);
        llistaTendes=new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                Tenda tenda = new Tenda();
                tenda.setCodi(c.getString(0));
                tenda.setNom(c.getString(1));
                tenda.setLat(c.getDouble(2));
                tenda.setLon(c.getDouble(3));
                tenda.setPoblacio(c.getString(4));
                tenda.setTelefon(c.getString(5));
                tenda.setAdresa(c.getString(6));
                tenda.setFoto(c.getString(7));
                tenda.setTipus(c.getString(8));
                llistaTendes.add(tenda);
            }while(c.moveToNext());
        }
        Log.d("TendesDAO","tendes="+llistaTendes.size());
        return llistaTendes;
    }


    public void closeDB(){
        db.close();
    }
}



