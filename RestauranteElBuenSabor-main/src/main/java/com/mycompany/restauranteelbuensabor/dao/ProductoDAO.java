package com.mycompany.restauranteelbuensabor.dao;

import com.mycompany.restauranteelbuensabor.model.Producto;

public class ProductoDAO {

    //Definir Productos
    private static final Producto[] productos = {
            new Producto("Bandeja Paisa",       32000),
            new Producto("Sancocho de Gallina", 28000),
            new Producto("Arepa con Huevo",      8000),
            new Producto("Jugo Natural",          7000),
            new Producto("Gaseosa",               4500),
            new Producto("Cerveza Poker",         6000),
            new Producto("Agua Panela",           3500),
            new Producto("Arroz con Pollo",      25000)
    };

    public Producto[] obtenerTodos() {
        return productos;
    }

    public int totalProductos() {
        return productos.length;
    }
}
