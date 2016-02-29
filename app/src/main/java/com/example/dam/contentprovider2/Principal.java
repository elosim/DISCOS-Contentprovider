package com.example.dam.contentprovider2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dam.contentprovider2.pojo.Cancion;
import com.example.dam.contentprovider2.pojo.Disco;
import com.example.dam.contentprovider2.pojo.Interprete;

import java.util.ArrayList;

/**
 * Created by ivan on 1/18/2016.
 */
public class Principal extends AppCompatActivity {
    private Uri uriC = Contrato.TablaCancion.CONTENT_URI;
    private Uri uriD = Contrato.TablaDisco.CONTENT_URI;
    private Uri uriI = Contrato.TablaInterprete.CONTENT_URI;
    private TextView tv;
    private TextView tv2;
    private ListView lv;
    private Cursor co;
    private SharedPreferences pc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        lv= (ListView)findViewById(R.id.lvDiscos);
        init();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            refresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        co = getContentResolver().query(uriD, null,null,null,null);
        Adaptador adp = new Adaptador(this,co);
        lv.setAdapter(adp);
    }

    public void refresh(){
        leer();
        Intent i = new Intent(this, Principal.class);
        startActivity(i);
    }
    public void leer(){
        Cursor cur = getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null);
        while (cur.moveToNext()) {
            escribir(cur);
        }
        cur.close();
    }
    public void escribir(Cursor cu){
        Cancion can;
        Interprete inter;
        Disco discc;
        long idinter, iddisc, idcan;
        Cursor c2;
        c2 = getContentResolver().query(uriI, null, null,null,null);
        idinter=0;
        if(cu.getString(25)!=null){

            while(c2.moveToNext()){
                if(c2.getString(1).equals(cu.getString(25))){
                    idinter = c2.getLong(0);
                }
            }
        }
        if(idinter==0){
            inter = new Interprete(cu.getString(25),0);
            idinter = getId(getContentResolver().insert(uriI,inter.getContentValues()),uriI);
        }
        c2 = getContentResolver().query(uriD, null, null,null,null);
        iddisc=0;
        if(cu.getString(28)!=null){

            while(c2.moveToNext()){
                if(c2.getString(1).equals(cu.getString(28))){
                    iddisc = c2.getLong(0);
                }
            }
        }
        if(iddisc==0){
            discc = new Disco(0,idinter,cu.getString(28));
            iddisc = getId(getContentResolver().insert(uriD,discc.getContentValues()),uriD);
        }
        c2 = getContentResolver().query(uriC, null, null,null,null);
        idcan=0;
        if(cu.getString(8)!=null){

            while(c2.moveToNext()){
                if(c2.getString(2).equals(cu.getString(8))){
                    idcan= c2.getLong(0);
                }
            }
        }
        if(idcan==0){
            can = new Cancion(0,iddisc,cu.getString(8));
            idcan = getId(getContentResolver().insert(uriC,can.getContentValues()),uriC);
        }
    }
    public long getId(Uri u, Uri cor){
        String aux = u.toString().substring(cor.toString().length()+1);
        return Long.parseLong(aux);
    }
    private void init(){
        pc = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext());
        String aux = pc.getString("start","no");
        if(aux.equals("no")) {
            SharedPreferences.Editor ed = pc.edit();
            ed.putString("start", "si");
            ed.commit();
            leer();

        }

        co = getContentResolver().query(uriD, null,null,null,null);
        Adaptador adp = new Adaptador(this,co);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cambiar(co);
            }
        });
    }
    public void cambiar(Cursor c) {
        Intent i=new Intent(this,ActivityCanciones.class);
        Disco x = new Disco();
        x.set(c);
        i.putExtra("id_disc", x.getId());

        startActivity(i);
    }


}
