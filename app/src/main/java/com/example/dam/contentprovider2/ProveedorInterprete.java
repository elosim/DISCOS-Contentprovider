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
public class ProveedorInterprete extends ContentProvider{
    public static final UriMatcher convierteUri2Int;
    public static final int INTERPRETE = 1;
    public static final int INTERPRETE_ID = 2;

    static{
        convierteUri2Int = new UriMatcher(UriMatcher.NO_MATCH);
        convierteUri2Int.addURI(Contrato.TablaInterprete.AUTHORITY, Contrato.TablaInterprete.TABLA, INTERPRETE);//Le damos la instrucción de qué hacer a la URI
        convierteUri2Int.addURI(Contrato.TablaInterprete.AUTHORITY, Contrato.TablaInterprete.TABLA + "/#", INTERPRETE_ID);
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
            case INTERPRETE:
                return Contrato.TablaInterprete.MULTIPLE_MIME;
            case INTERPRETE_ID:
                return Contrato.TablaInterprete.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (convierteUri2Int.match(uri) != INTERPRETE) {
            throw new IllegalArgumentException("URI desconocida : " + uri);
        }

        ContentValues contentValues;
        if (values == null) {
            throw new IllegalArgumentException("Interprete null ");
        }

        SQLiteDatabase db = abd.getWritableDatabase();
        long rowId = db.insert(Contrato.TablaInterprete.TABLA, null, values);
        if (rowId > 0) {
            Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaInterprete.CONTENT_URI, rowId);
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
            case INTERPRETE:
                affected = db.delete(Contrato.TablaInterprete.TABLA,selection,selectionArgs);
                break;
            case INTERPRETE_ID:
                long idActividad = ContentUris.parseId(uri);
                affected = db.delete(Contrato.TablaInterprete.TABLA, Contrato.TablaInterprete._ID + "= ?" , new String [] {idActividad + ""});

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
            case INTERPRETE:
                affected = db.update(Contrato.TablaInterprete.TABLA, values, selection, selectionArgs);
                break;
            case INTERPRETE_ID:
                String idActividad = uri.getPathSegments().get(1);
                affected = db.update(Contrato.TablaInterprete.TABLA, values,
                        Contrato.TablaInterprete._ID + "= ?" , new String [] {idActividad});
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
            case INTERPRETE:
                c = db.query(Contrato.TablaInterprete.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case INTERPRETE_ID:
                long idActividad = ContentUris.parseId(uri);
                c = db.query(Contrato.TablaInterprete.TABLA, projection, Contrato.TablaInterprete._ID + "= ? " , new String [] {idActividad + ""},
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), Contrato.TablaInterprete.CONTENT_URI);
        return c;
    }

}
