package com.example.dam.contentprovider2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.dam.contentprovider2.pojo.Cancion;
import com.example.dam.contentprovider2.pojo.Interprete;

/**
 * Created by ivan on 1/18/2016.
 */
public class AdaptadorCanciones extends CursorAdapter {
    Interprete aux = new Interprete();
    public AdaptadorCanciones(Context co, Cursor cu, Interprete inter) {
        super(co, cu, true);
        aux = inter;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.itemcan, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvCan = (TextView)view.findViewById(R.id.tvCan);
        TextView tvBy = (TextView)view.findViewById(R.id.tvInter);
        Cancion can = new Cancion();

        can.set(cursor);
        tvCan.setText(can.getTitulo());
        tvBy.setText(aux.getNombre());


    }


}
