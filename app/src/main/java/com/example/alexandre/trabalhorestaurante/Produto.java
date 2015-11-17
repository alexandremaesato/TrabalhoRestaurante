package com.example.alexandre.trabalhorestaurante;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**

 * Created by Guilherme on 15/11/2015.
 */
public class Produto {

    private int produtoid;
    private String nome;
    private Double valor;
    private Integer foto;

    public int getProdutoid() {
        return produtoid;
    }

    public void setProdutoid(int produtoid) {
        this.produtoid = produtoid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getFoto() {
        return foto;
    }

    public void setFoto(Integer foto) {
        this.foto = foto;
    }

    public String imagemEncode(Bitmap imagemProduto){

        Bitmap bm = imagemProduto;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encodedImage;
    }

    public Bitmap imagemDecode(String imagemProduto){

        byte [] encodeByte=Base64.decode(imagemProduto,Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }
}
