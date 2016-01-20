package com.example.dam.contentprovider2.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.dam.contentprovider2.Contrato;

/**
 * Created by ivan on 1/18/2016.
 */
public class Disco {
    private long id, id_interprete;

    private String nombre;

    public Disco(long id, long id_interprete, String nombre) {
        this.id = id;
        this.id_interprete = id_interprete;
        this.nombre = nombre;
    }

    public Disco() {
    }

    public long getId_interprete() {
        return id_interprete;
    }

    public void setId_interprete(long id_interprete) {
        this.id_interprete = id_interprete;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Disco{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(Contrato.TablaDisco.NOMBRE,this.nombre);
        cv.put(Contrato.TablaDisco.ID_INTERPRETE,this.id_interprete);

        return cv;
    }

    public void set(Cursor c){
        this.id = c.getLong(c.getColumnIndex(Contrato.TablaDisco._ID));
        this.nombre = c.getString(c.getColumnIndex(Contrato.TablaDisco.NOMBRE));
        this.id_interprete = c.getLong(c.getColumnIndex(Contrato.TablaDisco.ID_INTERPRETE));

    }
}
