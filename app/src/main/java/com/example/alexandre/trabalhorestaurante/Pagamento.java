package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pagamento extends Activity {

    private List<Produto> produtos = new ArrayList<Produto>();
    private PagamentoAdapter pagamentoAdapter;
    private Long id;
    TextView tvNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        tvNome = (TextView) findViewById(R.id.tvNome);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle parametros = intent.getExtras();
            tvNome.setText("Cliente: " + parametros.getString("nome"));
            id = Long.parseLong(parametros.getString("id"));
        }
    }

    public void lerDadosWebservice() {
        new Thread() {
            public void run() {

                Url url = new Url();
                WebService ws = new WebService(url.getUrl());
                Map params = new HashMap();
                params.put("opcao", "getPedido");
                params.put("id", id);
                String response = ws.webGet("", params);
                try {
                    JSONObject json = new JSONObject(response);


                }catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }.start();

    }

//    public void lerDados() {
//        produtos.clear();
//        Cursor cursor = db.retornaTodosArtigos();
//        if (cursor.moveToFirst())
//            do {
//                Artigo a = new Artigo();
//                a.id = cursor.getInt(cursor.getColumnIndex((BancoDeDados.KEY_ID)));
//                a.nome = cursor.getString(cursor.getColumnIndex(BancoDeDados.KEY_NOME));
//                a.revista = cursor.getString(cursor.getColumnIndex(BancoDeDados.KEY_REVISTA));
//                a.edicao = cursor.getString(cursor.getColumnIndex(BancoDeDados.KEY_EDICAO));
//                a.status = cursor.getInt(cursor.getColumnIndex(BancoDeDados.KEY_STATUS));
//                a.pago = cursor.getInt(cursor.getColumnIndex(BancoDeDados.KEY_PAGO));
//                artigos.add(a);
//            } while (cursor.moveToNext());
//        if (artigos.size() > 0) {
//            if (artigosAdapter == null) {
//                artigosAdapter = new ArtigosAdapter(this, artigos) {
//                    @Override
//                    public void edita(Artigo artigo) {
//                        Intent intent = new Intent(getApplicationContext(), NovoEdicaoActivity.class);
//                        intent.putExtra("artigo", artigo);
//                        //Descobrir se o user pressionou salvar ou cancelou a acao
//                        startActivityForResult(intent, REQUEST_EDICAO);
//                    }
//
//                    @Override
//                    public void deleta(Artigo artigo) {
//                        db.abrir();
//                        db.apagaArtigo(artigo.id);
//                        db.fechar();
//                        lerDados();
//                    }
//                };
//                list.setAdapter(artigosAdapter);
//            } else
//                artigosAdapter.novosDados(artigos);
//        }
//    }
//
//
//}

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

        return super.onOptionsItemSelected(item);
    }
}
