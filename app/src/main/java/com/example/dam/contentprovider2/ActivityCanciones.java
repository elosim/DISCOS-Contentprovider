package com.example.dam.contentprovider2;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dam.contentprovider2.pojo.Cancion;
import com.example.dam.contentprovider2.pojo.Interprete;

/**
 * Created by ivan on 1/18/2016.
 */
public class ActivityCanciones extends AppCompatActivity {
    private ListView lv;
    private AdaptadorCanciones adp;
    private Uri uriC = Contrato.TablaCancion.CONTENT_URI;
    private Uri uriD = Contrato.TablaDisco.CONTENT_URI;
    private Uri uriI = Contrato.TablaInterprete.CONTENT_URI;
    private Cursor cam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones);
        init();
    }

    public void init(){
        lv = (ListView)findViewById(R.id.lvCan);

        String mSelectionClause =   Contrato.TablaCancion.ID_DISCO+" = ?";
        String [] selectionArgs = {getIntent().getExtras().getLong("id_disc")+""};
        final Cursor cam = getContentResolver().query(uriC, null, mSelectionClause,selectionArgs,null);

        adp = new AdaptadorCanciones(this,cam,buscarInterprete());
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editar(cam);
            }
        });
    }
    public Interprete buscarInterprete() {
        String mSelectionClause = Contrato.TablaDisco._ID + " = ?";
        Log.v("aa",getIntent().getExtras().getLong("id_disc") + "");
        String[] selectionArgs = {getIntent().getExtras().getLong("id_disc") + ""};
        cam = getContentResolver().query(uriD, null, mSelectionClause, selectionArgs, null);
        long idInt = 0;

        while (cam.moveToNext()) {
            idInt = cam.getLong(2);
            Log.v("aa", "id del interprete"+idInt);
        }

        mSelectionClause = Contrato.TablaInterprete._ID + " = ?";
        String[] selectionArgs2 = {idInt + ""};
        cam = getContentResolver().query(uriI, null, mSelectionClause, selectionArgs2, null);
        Interprete inter = new Interprete();
        while (cam.moveToNext()) {
            inter.setNombre(cam.getString(1));
            Log.v("aa", inter.toString());
        }
        return inter;
    }
    public void editar(Cursor cam){
        Cancion can = new Cancion();
        can.set(cam);
        Intent i = new Intent(this, ActivityEditar.class);
        i.putExtra("title", can.getTitulo());
        i.putExtra("id", can.getId());
        i.putExtra("id_disc", getIntent().getExtras().getLong("id_disc"));
        startActivity(i);
    }
}
