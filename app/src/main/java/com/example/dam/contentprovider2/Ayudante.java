package com.example.dam.contentprovider2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ivan on 1/18/2016.
 */
public class Ayudante extends SQLiteOpenHelper{

    public static final String DATABASE_NAME ="libreria.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {

        String sql="drop table if exists "
                + Contrato.TablaCancion.TABLA;
        db.execSQL(sql);
        onCreate(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {//Cuando se baja la aplicación y se crea por primera vez(no hay versión previa de la aplicación)

        String sql;
        sql="create table "+ Contrato.TablaCancion.TABLA+ " ("+
                Contrato.TablaCancion._ID+ " integer primary key autoincrement, "+
                Contrato.TablaCancion.ID_DISCO+" long, "+
                Contrato.TablaCancion.TITULO+" text)";

        db.execSQL(sql);

        sql="create table "+ Contrato.TablaDisco.TABLA+ " ("+
                Contrato.TablaDisco._ID+ " integer primary key autoincrement, "+
                Contrato.TablaDisco.NOMBRE+" text,"+
                    Contrato.TablaDisco.ID_INTERPRETE+" long)";

        db.execSQL(sql);

        sql="create table "+ Contrato.TablaInterprete.TABLA+ " ("+
                Contrato.TablaInterprete._ID+ " integer primary key autoincrement, "+
                Contrato.TablaInterprete.NOMBRE +" text)";

        db.execSQL(sql);
    }

}
