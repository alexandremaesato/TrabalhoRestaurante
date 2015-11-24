package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    EditText usuario;
    EditText senha;
    Usuario modelusuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void tostando(String msg){
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }

    public void login(View view){
        usuario = (EditText)findViewById(R.id.editTextUsuario);
        senha = (EditText)findViewById(R.id.editTextSenha);
        Toast.makeText(this, "Aguarde...",
                Toast.LENGTH_SHORT).show();
        new Thread(){
            public void run() {

                Url url = new Url();
                WebService ws = new WebService(url.getUrl()+"/UserValidator" );
                Map params = new HashMap();

                params.put("usuario", usuario.getText().toString());
                params.put("senha", senha.getText().toString());
                params.put("opcao", "login");




                try{
                    String response = ws.webGet("", params);
                    JSONObject json = new JSONObject(response);

                    JSONObject jsonUsuario = new JSONObject(json.getString("usuario")); //Pega o Json e faz um load apenas dos dados do Usuario em um novo Json
                    Usuario u = new Usuario();
                    u.jsonToUsuario(jsonUsuario); //Converte o json em usuario
                    Bundle b = new Bundle();
                    //final ProgressDialog mprogressDialog = ProgressDialog.show(MainActivity.this, "Aguarde", "Processando...");
                    if(u != null){
                        b.putString("id", String.valueOf(u.getIdUsuario()));
                        b.putString("nome", u.getNome());
                    }else {
                        b.putString("message", "Algo deu errado!!!");
                    }

                    Message msg = new Message();
                    msg.setData(b);

//                    handler.sendMessage(msg);
                    handler.sendMessageAtTime(msg,3000);


                }catch (JSONException e1){
                    Message msg = new Message();
                    Bundle b= new Bundle();
                    b.putString("msg", "erro");
                    msg.setData(b);
                    handler.sendMessageAtTime(msg,3000);
                    //e1.printStackTrace();
                }
            }
        }.start();
    }

    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){



            if( msg != null ) {
                String idUsuario = msg.getData().getString("id");
                String nome = msg.getData().getString("nome");
                //tostando(idUsuario);
                if(idUsuario != null) {
                    iniciaDashboard(idUsuario, nome);
                }else
                {
                    tostando("Usuario ou senha incorreto!");
                }
            }
        }
    };

    public void iniciaDashboard(String id, String nome) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, Dashboard.class);
        Bundle params = new Bundle();
        params.putString("id", id);
        params.putString("nome", nome);
        intent.putExtras(params);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
