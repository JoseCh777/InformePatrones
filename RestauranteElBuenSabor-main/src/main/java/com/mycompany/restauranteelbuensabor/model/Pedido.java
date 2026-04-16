package com.mycompany.restauranteelbuensabor.model;

public class Pedido {

    private int numeroMesa;
    private int[] cantidades;
    private boolean activo;

    //Constructor
    public Pedido(int totalProductos) {
        this.cantidades = new int[totalProductos];
        this.activo = false;
        this.numeroMesa = 0;
    }

    //setters y Getters
    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public int[] getCantidades() {
        return cantidades;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    public void agregarProducto(int indice, int cantidad) {
        cantidades[indice] += cantidad;
    }

    public void reiniciar() {
        for (int i = 0; i < cantidades.length; i++) {
            cantidades[i] = 0;
        }
        numeroMesa = 0;
        activo = false;
    }

    public boolean tieneProductos() {
        for (int cantidad : cantidades) {
            if (cantidad > 0) return true;
        }
        return false;
    }
}
