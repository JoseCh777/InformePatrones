package com.mycompany.restauranteelbuensabor.service;

import com.mycompany.restauranteelbuensabor.model.Pedido;
import com.mycompany.restauranteelbuensabor.model.Producto;

public class PedidoService {

    private static final double TASA_IVA       = 0.19;
    private static final double TASA_PROPINA   = 0.10;
    private static final double TASA_DESCUENTO = 0.05;
    private static final int    MINIMO_PRODUCTOS_DESCUENTO = 3;
    private static final double MINIMO_SUBTOTAL_PROPINA    = 50000;

    private final Producto[] productos;
    private final Pedido pedido;
    private int numeroFactura;

    //Constructores
    public PedidoService(Producto[] productos, Pedido pedido) {
        this.productos = productos;
        this.pedido = pedido;
        this.numeroFactura = 1;
    }

    public void agregarProducto(int indice, int cantidad, int numeroMesa) {
        if (!pedido.isActivo()) {
            pedido.setNumeroMesa(numeroMesa);
            pedido.setActivo(true);
        }
        pedido.agregarProducto(indice, cantidad);
    }


    //CALCULOS
    //____________________________________________________________________
    public double calcularSubtotal() {
        double subtotal = 0;
        int[] cantidades = pedido.getCantidades();
        for (int i = 0; i < productos.length; i++) {
            subtotal += productos[i].getPrecio() * cantidades[i];
        }
        return subtotal;
    }

    private int contarTiposProducto() {
        int contador = 0;
        for (int cantidad : pedido.getCantidades()) {
            if (cantidad > 0) contador++;
        }
        return contador;
    }

    public double calcularTotal() {
        double subtotal = calcularSubtotal();
        int tiposProducto = contarTiposProducto();

        if (tiposProducto > MINIMO_PRODUCTOS_DESCUENTO) {
            subtotal = subtotal - (subtotal * TASA_DESCUENTO);
        }

        double iva = subtotal * TASA_IVA;
        double total = subtotal + iva;

        if (subtotal > MINIMO_SUBTOTAL_PROPINA) {
            total = total + (total * TASA_PROPINA);
        }

        return total;
    }

    public double calcularIVA() {
        double subtotal = calcularSubtotalConDescuento();
        return subtotal * TASA_IVA;
    }

    public double calcularPropina() {
        double subtotal = calcularSubtotalConDescuento();
        if (subtotal <= MINIMO_SUBTOTAL_PROPINA) return 0;
        double totalConIva = subtotal + (subtotal * TASA_IVA);
        return totalConIva * TASA_PROPINA;
    }

    public double calcularSubtotalConDescuento() {
        double subtotal = calcularSubtotal();
        if (contarTiposProducto() > MINIMO_PRODUCTOS_DESCUENTO) {
            subtotal = subtotal - (subtotal * TASA_DESCUENTO);
        }
        return subtotal;
    }

    //_______________________________________________________---

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void cerrarFactura() {
        numeroFactura++;
        pedido.reiniciar();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Producto[] getProductos() {
        return productos;
    }
}
