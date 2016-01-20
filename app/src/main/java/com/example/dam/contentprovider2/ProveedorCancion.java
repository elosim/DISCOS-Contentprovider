package com.example.dam.contentprovider2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ivan on 1/18/2016.
 */
public class ProveedorCancion extends ContentProvider{

    public static final UriMatcher convierteUri2Int;
    public static final int CANCION = 1;
    public static final int CANCION_ID = 2;

    static{
        convierteUri2Int = new UriMatcher(UriMatcher.NO_MATCH);
        convierteUri2Int.addURI(Contrato.TablaCancion.AUTHORITY, Contrato.TablaCancion.TABLA, CANCION);
        convierteUri2Int.addURI(Contrato.TablaCancion.AUTHORITY, Contrato.TablaCancion.TABLA + "/#", CANCION_ID);
    }

    private Ayudante abd;

    @Override
    public boolean onCreate() {
        abd = new Ayudante(getContext());
        return true;
    }



    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (convierteUri2Int.match(uri)){
            case CANCION:
                return Contrato.TablaCancion.MULTIPLE_MIME;
            case CANCION_ID:
                return Contrato.TablaCancion.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida " + uri);
        }
    }

    //METODO INSERT
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (convierteUri2Int.match(uri) != CANCION) {
            throw new IllegalArgumentException("URI desconocida : " + uri);
        }

        ContentValues contentValues;
        if (values == null) {
            throw new IllegalArgumentException("Cancion null ");
        }

        SQLiteDatabase db = abd.getWritableDatabase();
        long rowId = db.insert(Contrato.TablaCancion.TABLA, null, values);
        if (rowId > 0) {
            Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaCancion.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(uri_actividad, null);

            return uri_actividad;
        }

            throw new SQLException("Error al insertar fila en : " + uri);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();
        int match = convierteUri2Int.match(uri);
        int affected;
        switch (match) {
            case CANCION:
                affected = db.delete(Contrato.TablaCancion.TABLA,selection,selectionArgs);
                break;
            case CANCION_ID:
                long idActividad = ContentUris.parseId(uri);
                affected = db.delete(Contrato.TablaCancion.TABLA, Contrato.TablaCancion._ID + "= ?" , new String [] {idActividad + ""});

                break;
            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " +
                        uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();
        int affected;
        switch (convierteUri2Int.match(uri)) {
            case CANCION:
                affected = db.update(Contrato.TablaCancion.TABLA, values, selection, selectionArgs);
                break;
            case CANCION_ID:
                String idActividad = uri.getPathSegments().get(1);
                affected = db.update(Contrato.TablaCancion.TABLA, values,
                        Contrato.TablaCancion._ID + "= ?" , new String [] {idActividad});
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        abd.close();
        return affected;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = abd.getReadableDatabase();
        int match = convierteUri2Int.match(uri);

        Cursor c;

        switch (match) {
            case CANCION:
                c = db.query(Contrato.TablaCancion.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CANCION_ID:
                long idActividad = ContentUris.parseId(uri);
                c = db.query(Contrato.TablaCancion.TABLA, projection, Contrato.TablaCancion._ID + "= ? " , new String [] {idActividad + ""},
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), Contrato.TablaCancion.CONTENT_URI);
        return c;
    }

}

