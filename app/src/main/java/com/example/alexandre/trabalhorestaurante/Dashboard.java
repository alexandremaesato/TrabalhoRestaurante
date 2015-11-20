package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Dashboard extends Activity {
    TextView tvLogin;
    String id;
    String nome;
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
        }
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
