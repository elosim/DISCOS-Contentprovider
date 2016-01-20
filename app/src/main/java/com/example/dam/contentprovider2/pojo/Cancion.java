package com.example.dam.contentprovider2.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.dam.contentprovider2.Contrato;

/**
 * Created by ivan on 1/18/2016.
 */
public class Cancion {
    private long id, id_disco;
    private String titulo;

    public Cancion(){
        this(0,0,"");

    }

    public Cancion(long id, long id_disco, String titulo) {
        this.id = id;
        this.id_disco = id_disco;
        this.titulo = titulo;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_disco() {
        return id_disco;
    }

    public void setId_disco(long id_disco) {
        this.id_disco = id_disco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "id=" + id +
                ", id_disco=" + id_disco +
                ", titulo='" + titulo + '\'' +
                '}';
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(Contrato.TablaCancion.ID_DISCO,this.id_disco);
        cv.put(Contrato.TablaCancion.TITULO,this.titulo);

        return cv;
    }

    public void set(Cursor c){ //A partir del cursor recuperar nombre, apellido y telefono
        this.id = c.getLong(c.getColumnIndex(Contrato.TablaCancion._ID));
        this.id_disco = c.getLong(c.getColumnIndex(Contrato.TablaCancion.ID_DISCO));
        this.titulo= c.getString(c.getColumnIndex(Contrato.TablaCancion.TITULO));

    }

}
