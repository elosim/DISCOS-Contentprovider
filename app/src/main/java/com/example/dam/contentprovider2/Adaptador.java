package com.example.dam.contentprovider2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.dam.contentprovider2.pojo.Disco;

/**
 * Created by ivan on 1/18/2016.
 */
public class Adaptador extends CursorAdapter {

    public Adaptador(Context co, Cursor cu) {
        super(co, cu, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.item, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvdisco = (TextView)view.findViewById(R.id.tvDisco);
        Disco m = new Disco();
        m.set(cursor);

        tvdisco.setText(m.getNombre());


    }

}
