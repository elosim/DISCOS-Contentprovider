package com.example.dam.contentprovider2.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.dam.contentprovider2.Contrato;

/**
 * Created by ivan on 1/18/2016.
 */
public class Interprete {
    private String nombre;
    private long id;

    public Interprete(String nombre, long id) {
        this.nombre = nombre;
        this.id = id;
    }

    public Interprete() {
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
        return "Interprete{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }
    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(Contrato.TablaInterprete.NOMBRE,this.nombre);

        return cv;
    }

    public void set(Cursor c){
        this.id = c.getLong(c.getColumnIndex(Contrato.TablaInterprete._ID));
        this.nombre = c.getString(c.getColumnIndex(Contrato.TablaInterprete.NOMBRE));
    }
}
