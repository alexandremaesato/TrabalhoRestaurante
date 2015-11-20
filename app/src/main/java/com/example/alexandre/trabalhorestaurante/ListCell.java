package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Guilherme on 17/11/2015.
 */
public class ListCell extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] nome_produto;
    private final Bitmap[] imagem_produto;
    private final Double[] valor_produto;

    public ListCell(Activity context, String[] nome_produto, Bitmap[] imagem_produto, Double[] valor_produto) {
        super(context, R.layout.list_cell, nome_produto);
        this.context = context;
        this.nome_produto = nome_produto;
        this.imagem_produto = imagem_produto;
        this.valor_produto = valor_produto;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_cell, null, true);
        TextView txtNome = (TextView)rowView.findViewById(R.id.txtNome);
        TextView txtValor = (TextView)rowView.findViewById(R.id.txtValor);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.img);
        txtNome.setText(nome_produto[position]);
        txtValor.setText(String.valueOf(valor_produto[position]));
        imageView.setImageBitmap(imagem_produto[position]);
        return rowView;
    }
}
