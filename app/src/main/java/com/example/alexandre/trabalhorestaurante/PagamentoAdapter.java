package com.example.alexandre.trabalhorestaurante;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alexandre on 15/11/2015.
 */
public class PagamentoAdapter extends BaseAdapter {
    private List<Produto> produtos;
    private LayoutInflater inflater;

    public PagamentoAdapter(Context context, List<Produto> produtos){
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.produtos = produtos;
    }

    public void novosDados(List<Produto> produtos){
        this.produtos = produtos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position,View arg1, ViewGroup arg2){
        View v = inflater.inflate(R.layout.item_pedido, null);
        ((TextView)(v.findViewById(R.id.txtNome))).setText(produtos.get(position).getNome());
        ((TextView)(v.findViewById(R.id.txtValor))).setText(produtos.get(position).getValor().toString());
        return v;
    }
}
