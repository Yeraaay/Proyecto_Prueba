package com.example.proyectotatto;

import java.util.List;

public class Pedido {
    private int numeroPedido;
    private List<String> nombresTatuajes;

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public List<String> getNombresTatuajes() {
        return nombresTatuajes;
    }

    public void setNombresTatuajes(List<String> nombresTatuajes) {
        this.nombresTatuajes = nombresTatuajes;
    }

    public Pedido(int numeroPedido, List<String> nombresTatuajes) {
        this.numeroPedido = numeroPedido;
        this.nombresTatuajes = nombresTatuajes;
    }

    public Pedido() {

    }
}