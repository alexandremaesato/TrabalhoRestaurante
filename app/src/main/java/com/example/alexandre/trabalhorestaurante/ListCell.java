package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Guilherme on 16/11/2015.
 */
public class ListCell extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] nome_produto;
    private final Integer[] foto_produto;
    private final Integer[] valor_produto;

    public ListCell(Activity context, String[] nome, Integer[] foto, Integer[] valor) {
        super(context, R.layout.list_cell, nome);
        this.context = context;
        this.nome_produto = nome;
        this.foto_produto = foto;
        this.valor_produto = valor;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_cell, null, true);
        TextView nome = (TextView)rowView.findViewById(R.id.nome_produto);
        TextView valor = (TextView)rowView.findViewById(R.id.valor_produto);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.foto_produto);
        nome.setText(nome_produto[position]);
        valor.setText(valor_produto[position]);
        imageView.setImageResource(foto_produto[position]);
        return rowView;
    }
}
