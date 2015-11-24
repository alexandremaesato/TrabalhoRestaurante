package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EfetuarPedido extends Activity {
    ListView list;
    String[] nomeProduto = new String[6];
    Double[] valorProduto = new Double[6];
    Bitmap[] imagemProduto = new Bitmap[6];
    int[] produtoid = new int[6];
    String imagem;
    List<Produto> pds;
    Gson gson = new Gson();
    String usuarioid;
    String pedidoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efetuar_pedido);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle parametros = intent.getExtras();
            usuarioid = parametros.getString("usuarioid");
            pedidoid = parametros.getString("pedidoid");
        }

        new Thread() {
            public void run() {

                Url url = new Url();
                WebService ws = new WebService(url.getUrl()+"/UserValidator");
                Map params = new HashMap();

                params.put("opcao", "listaProdutos");

                String response = ws.webGet("", params);

                try{

                    JSONObject json = new JSONObject(response);
                    String out = json.getString("produtos").toString();

                    ProdutosList p = gson.fromJson(out, ProdutosList.class);
                    pds = p.getProdutos();

                    Bundle b = new Bundle();
                    b.putString("message", String.valueOf(json));

                    Message msg = new Message();
                    msg.setData(b);

                    handler.sendMessage(msg);

                }catch (JSONException e1){
                    e1.printStackTrace();
                }
            }
        }.start();
    }

    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            if( msg != null ) {
                int i = 0;

                for(Produto pd : pds){
                    nomeProduto[i] = pd.getNome();
                    valorProduto[i] = pd.getValor();
                    imagem = pd.getImagem();
                    imagemProduto[i] = pd.imagemDecode(imagem);
                    produtoid[i] = pd.getProdutoid();
                    i++;
                }


                ListCell adapter = new ListCell(EfetuarPedido.this, nomeProduto, imagemProduto, valorProduto);
                list = (ListView)findViewById(R.id.produtosList);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                        Intent intent =  new Intent(EfetuarPedido.this, ConfirmaPagamento.class);
                        Bundle params = new Bundle();

                            Produto prod = new Produto();
                            prod.setNome(nomeProduto[+arg2]);
                            prod.setValor(valorProduto[+arg2]);
                            prod.setImagem(prod.imagemEncode(imagemProduto[+arg2]));
                            prod.setProdutoid(produtoid[+arg2]);

                        params.putString("produtoid", String.valueOf(prod.getProdutoid()));
                        params.putString("produtonome", prod.getNome());
                        params.putString("usuarioid", usuarioid);
                        params.putString("pedidoid", pedidoid);
                        intent.putExtras(params);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_efetuar_pedido, menu);
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
