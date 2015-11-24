package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmaPagamento extends Activity {

    String usuario;
    String produto;
    String produtonome;
    String pedido;
    String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_pagamento);

        TextView nomeproduto = (TextView)findViewById(R.id.nomeProduto);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuario     = extras.getString("usuarioid");
            produto     = extras.getString("produtoid");
            produtonome = extras.getString("produtonome");
            pedido      = extras.getString("pedidoid");

            nomeproduto.setText(produtonome);

        }
    }

    public void finalizarPedido(View view){
        EditText quantidade    = (EditText)findViewById(R.id.quantidade);
        TextView output        = (TextView)findViewById(R.id.nomeProduto);

        if(quantidade.length() == 0){
            Toast.makeText(this, "Forneça uma quantidade", Toast.LENGTH_LONG).show();
            return;
        }
//        quantity = Integer.parseInt(quantidade.getText().toString());
        quantity = quantidade.getText().toString();
        //serao passados para proxima tela produto, quantidade e POSSIVELMENTE pedido

        //webservice

        new Thread(){
            public void run() {

                Url url = new Url();
                WebService ws = new WebService(url.getUrl()+"/UserValidator");
                Map params = new HashMap();

                params.put("usuario", usuario);
                params.put("produto", produto);
                params.put("quantidade", quantity);
                params.put("pedido", pedido);
                params.put("opcao", "inserePedido");

                String response = ws.webGet("", params);


                try{

                    JSONObject json = new JSONObject(response);
                    String out = json.getString("pedidoid").toString();

                    Bundle b = new Bundle();
                    b.putString("message", out);

                    Message msg = new Message();
                    msg.setData(b);

                    //handler.sendMessage(msg);
                    handler.sendMessageAtTime(msg, 3000);
                    chamaTelaAnterior(out);

                }catch (JSONException e1){
                    e1.printStackTrace();
                }
            }
        }.start();
    }

    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            //TextView t = (TextView)findViewById(R.id.textViewStatus);
            String out = "";
            if (msg != null) {
                String mensgem = msg.toString();
                // chamaTelaAnterior(mensgem);
            }
        }
    };

    public void chamaTelaAnterior(String mensagem) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.w("UM LOGO", mensagem);
        Intent intentanterior = new Intent(ConfirmaPagamento.this, EfetuarPedido.class);
        intentanterior.putExtra("pedidoid", mensagem);
        intentanterior.putExtra("usuarioid", usuario);
        startActivity(intentanterior);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirma_pagamento, menu);
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
