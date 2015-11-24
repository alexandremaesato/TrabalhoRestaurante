package com.example.alexandre.trabalhorestaurante;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guilherme on 15/11/2015.
 */
public class Pedido {

    private int pedidoid;
    private int usuarioid;
    private String status;
    private String formapgto;
    private ArrayList<PedidoProduto> pedidoProdutos;

    public int getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(int usuarioid) {
        this.usuarioid = usuarioid;
    }

    public int getPedidoid() {
        return pedidoid;
    }

    public void setPedidoid(int pedidoid) {
        this.pedidoid = pedidoid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormapgto() {
        return formapgto;
    }

    public void setFormapgto(String formapgto) {
        this.formapgto = formapgto;
    }
    public ArrayList<PedidoProduto> getPedidoProdutos() {
        return pedidoProdutos;
    }

    public void setPedidoProdutos(ArrayList<PedidoProduto> pedidoProdutos) {
        this.pedidoProdutos = pedidoProdutos;
    }

}
