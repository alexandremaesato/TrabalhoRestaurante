package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pagamento extends Activity {

    Double total = 0.0;
    private List<Produto> produtos;
    private ListView list;
    private PagamentoAdapter pagamentoAdapter;
    private Long id; // id do Usuario
    TextView tvNome;
    Gson gson = new Gson();
    String[] nomeProduto = new String[50];
    Double[] valorProduto = new Double[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        tvNome = (TextView) findViewById(R.id.tvNome);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(pagamentoAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle parametros = intent.getExtras();
            tvNome.setText("Cliente: " + parametros.getString("nome"));
            id = Long.parseLong(parametros.getString("id"));
            lerDadosWebservice();
        }
    }

    public void lerDadosWebservice() {
        new Thread() {
            public void run() {

                Url url = new Url();
                WebService ws = new WebService(url.getUrl()+"/PedidoControle");
                Map params = new HashMap();
                params.put("opcao", "getPedido");
                params.put("id", id.toString());
                String response = ws.webGet("", params);
                try {
                    JSONObject json = new JSONObject(response);
                    String out = json.getString("produtos").toString();

                    ProdutosList p = gson.fromJson(out, ProdutosList.class);
                    produtos = p.getProdutos();

                    Bundle b = new Bundle();
                    b.putString("message", String.valueOf(json));

                    Message msg = new Message();
                    msg.setData(b);

                    handler.sendMessage(msg);


                }catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }.start();

    }
    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){
//            String produtosJSON = "";
            if( msg != null ) {
//                produtosJSON = (String) msg.getData().getString("produtos");
                int i = 0;


                for(Produto pd : produtos){
                    nomeProduto[i] = pd.getNome();
                    valorProduto[i] = pd.getValor();
                    total = total + pd.getValor();
                    i++;
                }

                if(produtos.size() > 0){

                    pagamentoAdapter = new PagamentoAdapter(Pagamento.this ,produtos,total);
                    list.setAdapter(pagamentoAdapter);
                    list = (ListView)findViewById(R.id.produtosList);
                    ((TextView)(findViewById(R.id.tvTotal))).setText("Total: R$ " + String.format("%.2f",total));

                }
            }
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pagamento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.debito){
            tostando("Debito Selecionado");
            salvaPagamento("debito");
            finish();
        }
        if(id == R.id.dinheiro){
            tostando("Dinheiro Selecionado");
            salvaPagamento("dinheiro");
            finish();
        }
        if(id == R.id.credito){
            tostando("Credito Selecionado");
            salvaPagamento("credito");
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void tostando(String msg){
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }

    public void  salvaPagamento(final String forma){
        new Thread() {
            public void run() {
                Url url = new Url();
                WebService ws = new WebService(url.getUrl()+"/PedidoControle");
                Map params = new HashMap();
                params.put("opcao", "pagar");
                params.put("forma", forma);
                params.put("id", id.toString());
                String response = ws.webGet("", params);
                try {
                    JSONObject json = new JSONObject(response);
                    String out = json.getString("status").toString();

                    Bundle b = new Bundle();
                    b.putString("message", out);
                    Message msg = new Message();
                    msg.setData(b);
                    //resultHandler.sendMessage(msg);
                }catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }.start();

    }

    public Handler resultHandler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            String resultado = "";
            if( msg != null ) {
                resultado = (String) msg.getData().getString("status");

                if ("ok".equals(resultado)){
                    tostando("Gravou no banco de dados");
                    //FAZER FECHAR O ACTIVITY
                }else{
                    tostando("Algo deu errado");
                }
            }
        }
    };


}
