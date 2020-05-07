package es.tiendaslocales;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static es.tiendaslocales.MainActivity.DATABASE_VERSION;
import static es.tiendaslocales.MainActivity.dataBaseSQL;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "tendeslocals";

    public MySQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // **************************************
        // * Metode abreviat del execucio SQL   *
        // **************************************

        for (String dbSQL : dataBaseSQL) {
            db.execSQL(dbSQL);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // **************************************
        // * Metode abreviat del execucio SQL   *
        // **************************************

        for (String dbSQL : dataBaseSQL) {
            db.execSQL(dbSQL);
        }
    }
}
