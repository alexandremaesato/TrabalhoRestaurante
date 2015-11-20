package com.example.alexandre.trabalhorestaurante;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexandre on 16/11/2015.
 */
public class Usuario {
    private long idUsuario;
    private String nome;
    private String senha;

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario jsonToUsuario(JSONObject json) throws JSONException {
        HashMap map = new HashMap();
        this.setIdUsuario(json.getLong("idUsuario"));
        this.setNome(json.optString("nome"));
        return this;
    }

    public Bundle getParams(){
        Bundle params = new Bundle();
        params.putString("id", String.valueOf(this.getIdUsuario()));
        params.putString("nome", this.getNome());
        return params;
    }

    public Usuario getUsusarioFromParams(Bundle params){
        this.nome = params.getString("nome");
        this.idUsuario = Long.parseLong(params.getString("nome"));
        return this;
    }
}
