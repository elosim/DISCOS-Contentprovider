package com.example.dam.contentprovider2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.dam.contentprovider2.R;
import com.example.dam.contentprovider2.pojo.Cancion;
import com.example.dam.contentprovider2.pojo.Interprete;

import javax.microedition.khronos.egl.EGLDisplay;

public class ActivityEditar extends AppCompatActivity {
    private EditText et;
    private Uri uriC = Contrato.TablaCancion.CONTENT_URI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        et = (EditText)findViewById(R.id.etTitulo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar(et.getText().toString());
            }
        });


        et.setText(getIntent().getExtras().getString("title"));
    }
    public void guardar(String title){
        Cancion can = new Cancion();
        can.setTitulo(title);
        can.setId(getIntent().getExtras().getLong("id"));
        can.setId_disco(getIntent().getExtras().getLong("id_disc"));
        String mSelectionClause =   Contrato.TablaCancion._ID+" = ?";
        Log.v("añ2", can.toString() + "");
        Log.v("añ2", getIntent().getExtras().getLong("id") + "");
        String [] selectionArgs = {getIntent().getExtras().getLong("id")+""};
        getContentResolver().update(uriC, can.getContentValues(),mSelectionClause,selectionArgs);
        finish();
    }

}
