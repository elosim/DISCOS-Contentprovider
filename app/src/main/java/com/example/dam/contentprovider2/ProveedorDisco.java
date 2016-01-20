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
public class ProveedorDisco extends ContentProvider {
    public static final UriMatcher convierteUri2Int;
    public static final int DISCO = 1;
    public static final int DISCO_ID = 2;

    static{
        convierteUri2Int = new UriMatcher(UriMatcher.NO_MATCH);
        convierteUri2Int.addURI(Contrato.TablaDisco.AUTHORITY, Contrato.TablaDisco.TABLA, DISCO);
        convierteUri2Int.addURI(Contrato.TablaDisco.AUTHORITY, Contrato.TablaDisco.TABLA + "/#", DISCO_ID);
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
            case DISCO:
                return Contrato.TablaDisco.MULTIPLE_MIME;
            case DISCO_ID:
                return Contrato.TablaDisco.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (convierteUri2Int.match(uri) != DISCO) {
            throw new IllegalArgumentException("URI desconocida : " + uri);
        }

        ContentValues contentValues;
        if (values == null) {
            throw new IllegalArgumentException("Disco null ");
        }

        SQLiteDatabase db = abd.getWritableDatabase();
        long rowId = db.insert(Contrato.TablaDisco.TABLA, null, values);
        if (rowId > 0) {
            Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaDisco.CONTENT_URI, rowId);
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
            case DISCO:
                affected = db.delete(Contrato.TablaDisco.TABLA,selection,selectionArgs);
                break;
            case DISCO_ID:
                long idActividad = ContentUris.parseId(uri);
                affected = db.delete(Contrato.TablaDisco.TABLA, Contrato.TablaDisco._ID + "= ?" , new String [] {idActividad + ""});

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
            case DISCO:
                affected = db.update(Contrato.TablaDisco.TABLA, values, selection, selectionArgs);
                break;
            case DISCO_ID:
                //Distintas formas de obtener el idActividad
                //uri.getLastPathSegment()
                //ContentUris.parseId(uri)
                //uri.getPathSegments().get(l)
                String idActividad = uri.getPathSegments().get(1);
                affected = db.update(Contrato.TablaDisco.TABLA, values,
                        Contrato.TablaDisco._ID + "= ?" , new String [] {idActividad});
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

        // Obtener base de datos
        SQLiteDatabase db = abd.getReadableDatabase();
        // Comparar Uri
        int match = convierteUri2Int.match(uri);

        Cursor c;

        switch (match) {
            case DISCO:
                // Consultando todos los registros
                c = db.query(Contrato.TablaDisco.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                Log.v("Camino", "nos hemos metido por el camino del case CLIENTE_ID");
                break;
            case DISCO_ID:
                // Consultando un solo registro basado en el Id del Uri
                long idActividad = ContentUris.parseId(uri);
                c = db.query(Contrato.TablaDisco.TABLA, projection, Contrato.TablaDisco._ID + "= ? " , new String [] {idActividad + ""},
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), Contrato.TablaDisco.CONTENT_URI);
        return c;
    }
}
