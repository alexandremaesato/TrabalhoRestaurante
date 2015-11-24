package com.example.alexandre.trabalhorestaurante;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandre on 15/11/2015.
 */
public class PagamentoAdapter extends BaseAdapter {
    private ArrayList<PedidoProduto> pedidoProdutos;
    private ArrayList<Produto> produtos = new ArrayList<Produto>();
    private LayoutInflater inflater;
    private Double total;

    public PagamentoAdapter(Context context, ArrayList<PedidoProduto> pedidoProdutos,Double total){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pedidoProdutos = pedidoProdutos;
        int i=0;
        for(PedidoProduto pd : pedidoProdutos){
            for(int j=0; j<pd.getQuantidade(); j++) {
                produtos.add(pd.getProduto());
            }
            i++;
        }
        this.total = total;
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
    public View getView(final int position,View arg1, ViewGroup arg2) {
        View v = inflater.inflate(R.layout.item_pedido, null);

        ((TextView)(v.findViewById(R.id.txtNome))).setText(produtos.get(position).getNome());
        ((TextView)(v.findViewById(R.id.txtValor))).setText(produtos.get(position).getValor().toString());

        return v;
    }
}
