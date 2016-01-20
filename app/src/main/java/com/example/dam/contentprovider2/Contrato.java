package com.example.dam.contentprovider2;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ivan on 1/18/2016.
 */
public class Contrato {

    private Contrato(){
    }

    public static abstract class TablaCancion implements BaseColumns {
        public static final String TABLA = "cancion";
        public static final String TITULO = "titulo";
        public static final String ID_DISCO = "id_disco";

        public final static String AUTHORITY =
                "com.example.dam.contentprovider2.ProveedorCancion";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MULTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;

    }
    public static abstract class TablaDisco implements BaseColumns {
        public static final String TABLA = "disco";
        public static final String NOMBRE = "nombre";
        public static final String ID_INTERPRETE = "id_interprete";

        public final static String AUTHORITY =
                "com.example.dam.contentprovider2.ProveedorDisco";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MULTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }
    public static abstract class TablaInterprete implements BaseColumns {
        public static final String TABLA = "interprete";
        public static final String NOMBRE = "nombre";
        public final static String AUTHORITY =
                "com.example.dam.contentprovider2.ProveedorInterprete";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MULTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }
}