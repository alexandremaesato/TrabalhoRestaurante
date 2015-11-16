package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
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
    String ip ="http://192.168.25.8:8080";
    EditText usuario;
    EditText senha;
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
                String url = ip+"/WebService/UserValidator";
                WebService ws = new WebService(url);
                Map params = new HashMap();

                params.put("usuario", usuario.getText().toString());

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
            }
        }.start();
    }

    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            //TextView t = (TextView)findViewById(R.id.textViewStatus);
            String out = (String)msg.getData().getString("message");
            tostando(out);
            if("Login Correto".equals(out)){
                iniciaDashboard();
            }
            //t.setText("Mensagem: " + out);
        };
    };

    public void iniciaDashboard() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, Dashboard.class));
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
