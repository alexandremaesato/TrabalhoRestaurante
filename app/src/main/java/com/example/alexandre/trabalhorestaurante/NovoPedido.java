package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NovoPedido extends Activity {

    ListView list;
    String[] nomeProduto;
    Integer[] valorProduto;
    Integer[] imageProduto;
    List<Produto> produtos = new ArrayList<Produto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_pedido);

// DOING
// Pegar produto no banco de dados criar um laço para setar cada atributo em um array
        Url url = new Url();
        WebService ws = new WebService(url.getUrl());
        Map params = new HashMap();

        params.put("opcao", "listaProdutos");

        String response = ws.webGet("", params);

        try{

            JSONObject json = new JSONObject(response);
            String out = json.getString("message").toString();

            Bundle b = new Bundle();
            b.putString("message", out);

            Message msg = new Message();
            msg.setData(b);

            //handler.sendMessage(msg);
            handler.sendMessageAtTime(msg, 3000);


        }catch (JSONException e1){
            e1.printStackTrace();
        }

    };

    public void tostando(String msg){
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }

    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            //TextView t = (TextView)findViewById(R.id.textViewStatus);
            String out = "";
            if( msg != null ) {
                out = (String) msg.getData().getString("produtos");
            }
            tostando(out);

        };
    };

    public void produtosList(){
        ListCell adapter = new ListCell(NovoPedido.this, nomeProduto, imageProduto, valorProduto);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(NovoPedido.this, "Clicou na " + nomeProduto[+arg2],
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_novo_pedido, menu);
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
