package com.mycompany.restauranteelbuensabor.view;

import com.mycompany.restauranteelbuensabor.model.Producto;
import com.mycompany.restauranteelbuensabor.service.PedidoService;

public class Factura {

    private static final String SEPARADOR_DOBLE = "========================================";
    private static final String SEPARADOR_SIMPLE = "----------------------------------------";

    private final PedidoService pedidoService;

    public Factura(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public void mostrarCarta() {
        Producto[] productos = pedidoService.getProductos();
        System.out.println(SEPARADOR_DOBLE);
        System.out.println("    RESTAURANTE EL BUEN SABOR");
        System.out.println("    --- NUESTRA CARTA ---");
        System.out.println(SEPARADOR_DOBLE);
        for (int i = 0; i < productos.length; i++) {
            System.out.printf("%d. %-22s $%,.0f%n",
                    (i + 1), productos[i].getNombre(), productos[i].getPrecio());
        }
        System.out.println(SEPARADOR_DOBLE);
    }

    public void mostrarPedido() {
        Producto[] productos = pedidoService.getProductos();
        int[] cantidades = pedidoService.getPedido().getCantidades();

        System.out.println("--- PEDIDO ACTUAL ---");
        for (int i = 0; i < productos.length; i++) {
            if (cantidades[i] > 0) {
                double subtotalLinea = productos[i].getPrecio() * cantidades[i];
                System.out.printf("%-20s x%-6d $%,.0f%n",
                        productos[i].getNombre(), cantidades[i], subtotalLinea);
            }
        }
        System.out.println(SEPARADOR_SIMPLE.substring(0, 20));
        System.out.printf("%-27s $%,.0f%n", "Subtotal:", pedidoService.calcularSubtotal());
    }

    private void imprimirEncabezado() {
        System.out.println(SEPARADOR_DOBLE);
        System.out.println("    RESTAURANTE EL BUEN SABOR");
        System.out.println("    Calle 15 #8-32, Valledupar");
        System.out.println("    NIT: 900.123.456-7");
        System.out.println(SEPARADOR_DOBLE);
    }

    public void imprimirFacturaCompleta() {
        Producto[] productos = pedidoService.getProductos();
        int[] cantidades = pedidoService.getPedido().getCantidades();
        double subtotal = pedidoService.calcularSubtotalConDescuento();
        double iva      = pedidoService.calcularIVA();
        double propina  = pedidoService.calcularPropina();
        double total    = pedidoService.calcularTotal();

        imprimirEncabezado();
        System.out.printf("FACTURA No. %03d%n", pedidoService.getNumeroFactura());
        System.out.println(SEPARADOR_SIMPLE);

        for (int i = 0; i < productos.length; i++) {
            if (cantidades[i] > 0) {
                System.out.printf("%-20s x%-6d $%,.0f%n",
                        productos[i].getNombre(), cantidades[i],
                        productos[i].getPrecio() * cantidades[i]);
            }
        }

        System.out.println(SEPARADOR_SIMPLE);
        System.out.printf("%-27s $%,.0f%n", "Subtotal:", subtotal);
        System.out.printf("%-27s $%,.0f%n", "IVA (19%):", iva);

        if (propina > 0) {
            System.out.printf("%-27s $%,.0f%n", "Propina (10%):", propina);
        }

        System.out.println(SEPARADOR_SIMPLE);
        System.out.printf("%-27s $%,.0f%n", "TOTAL:", total);
        System.out.println(SEPARADOR_DOBLE);
        System.out.println("Gracias por su visita!");
        System.out.println("El Buen Sabor - Valledupar");
        System.out.println(SEPARADOR_DOBLE);
    }


}
