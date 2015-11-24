package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dashboard extends Activity {
    TextView tvLogin;
    String id;
    String nome;
    Button btnNovoPedido;
//    Button bConfirmaPedido;
    String iduser;
    Gson gson = new Gson();
    Pedido pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        tvLogin =(TextView)findViewById(R.id.tvLogin);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle parametros = intent.getExtras();
            tvLogin.setText("Cliente: "+ parametros.getString("nome"));
            id = parametros.getString("id");
            nome = parametros.getString("nome");
            iduser = parametros.getString("id");
        }

        new Thread(){
            public void run() {
            // WEB Service q pega o pedido
                Url url = new Url();
                WebService ws1 = new WebService(url.getUrl());
                Map params = new HashMap();
                params.put("usuarioid", iduser);
                params.put("opcao", "buscaPedido");

                String response = ws1.webGet("", params);

                try{

                    JSONObject json = new JSONObject(response);
                    String out = json.getString("pedido").toString();
                    Bundle b = new Bundle();

                    pd = gson.fromJson(out, Pedido.class);

                    b.putString("message", "Good Boy!");

                    Message msg = new Message();
                    msg.setData(b);

                    handler.sendMessage(msg);

                }catch (JSONException e1){
                    e1.printStackTrace();
                }
            }
        }.start();

        btnNovoPedido = (Button)findViewById(R.id.bNovoPedido);
        btnNovoPedido.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(Dashboard.this, EfetuarPedido.class);
                Bundle params = new Bundle();
                params.putString("usuarioid",id);
                params.putString("pedidoid", String.valueOf(pd.getPedidoid()));
                it.putExtras(params);
                startActivity(it);
            }
        });

//        bConfirmaPedido = (Button)findViewById(R.id.bConfirmaPedido);
//        bConfirmaPedido.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(Dashboard.this, EfetuarPedido.class);
//                Bundle params = new Bundle();
//                params.putString("id", id);
//                params.putString("nome", nome);
//                params.putString("pedido",String.valueOf(pd.getPedidoid()));
//                it.putExtras(params);
//                startActivity(it);
//            }
//        });
    }

    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){

            if( msg == null ) {
//                tostando("OK");
                tostando("Nao foi possivel se conectar com o Banco de Dados");
            }
        };
    };

    public void tostando(String msg){
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
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

    public void pagamento(View view){
        Intent intent =  new Intent(this, Pagamento.class);
        Bundle params = new Bundle();
        params.putString("id",id);
        params.putString("nome",nome);
        intent.putExtras(params);
        startActivity(intent);
    }
}
