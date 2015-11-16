package com.example.alexandre.trabalhorestaurante;

/**
 * Created by Guilherme on 15/11/2015.
 */
public class Pedido_Produto {

    private int pedido_produto_id;
    private int pedidoid;
    private int produtoid;
    private int quantidade;

    public int getPedido_produto_id() {
        return pedido_produto_id;
    }

    public void setPedido_produto_id(int pedido_produto_id) {
        this.pedido_produto_id = pedido_produto_id;
    }

    public int getPedidoid() {
        return pedidoid;
    }

    public void setPedidoid(int pedidoid) {
        this.pedidoid = pedidoid;
    }

    public int getProdutoid() {
        return produtoid;
    }

    public void setProdutoid(int produtoid) {
        this.produtoid = produtoid;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
