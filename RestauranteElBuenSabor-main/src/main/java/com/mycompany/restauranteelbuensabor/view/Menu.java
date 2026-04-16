package com.mycompany.restauranteelbuensabor.view;

import com.mycompany.restauranteelbuensabor.service.PedidoService;
import java.util.Scanner;

public class Menu {

    private static final String SEPARADOR = "========================================";

    private final PedidoService pedidoService;
    private final Factura facturaView;
    private final Scanner scanner;

    public Menu(PedidoService pedidoService, Factura facturaView) {
        this.pedidoService = pedidoService;
        this.facturaView = facturaView;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        imprimirBienvenida();
        boolean ejecutando = true;
        while (ejecutando) {
            imprimirOpciones();
            int opcion = scanner.nextInt();
            ejecutando = procesarOpcion(opcion);
        }
        scanner.close();
    }

    private void imprimirBienvenida() {
        System.out.println(SEPARADOR);
        System.out.println("    RESTAURANTE EL BUEN SABOR");
        System.out.println("    Calle 15 #8-32, Valledupar");
        System.out.println("    NIT: 900.123.456-7");
        System.out.println(SEPARADOR);
    }

    private void imprimirOpciones() {
        System.out.println("1. Ver carta");
        System.out.println("2. Agregar producto al pedido");
        System.out.println("3. Ver pedido actual");
        System.out.println("4. Generar factura");
        System.out.println("5. Nueva mesa");
        System.out.println("0. Salir");
        System.out.println(SEPARADOR);
        System.out.print("Seleccione una opcion: ");
    }

    private boolean procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> VerCarta();
            case 2 -> AgregarProducto();
            case 3 -> VerPedido();
            case 4 -> GenerarFactura();
            case 5 -> NuevaMesa();
            case 0 -> { System.out.println("Hasta luego!"); return false; }
            default -> System.out.println("Opcion no valida. Seleccione entre 0 y 5.");
        }
        return true;
    }


    //OPCIONES DEL MENU
    //______________________________________________________
    private void VerCarta() {
        facturaView.mostrarCarta();
        System.out.println();
    }

    private void AgregarProducto() {
        int totalProductos = pedidoService.getProductos().length;
        System.out.println("--- AGREGAR PRODUCTO ---");
        System.out.print("Numero de producto (1-" + totalProductos + "): ");
        int numero = scanner.nextInt();
        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();

        if (numero < 1 || numero > totalProductos) {
            System.out.println("Producto no existe. La carta tiene " + totalProductos + " productos.");
            System.out.println();
            return;
        }

        if (cantidad <= 0) {
            System.out.println("Cantidad invalida. Ingrese un valor positivo.");
            System.out.println();
            return;
        }

        int numeroMesa = pedidoService.getPedido().getNumeroMesa();
        if (!pedidoService.getPedido().isActivo()) {
            System.out.print("Ingrese numero de mesa: ");
            numeroMesa = scanner.nextInt();
            if (numeroMesa <= 0) {
                numeroMesa = 1;
            }
        }

        pedidoService.agregarProducto(numero - 1, cantidad, numeroMesa);
        System.out.println("Producto agregado al pedido.");
        System.out.println("  -> " + pedidoService.getProductos()[numero - 1].getNombre() + " x" + cantidad);
        System.out.println();
    }

    private void VerPedido() {
        System.out.println();
        if (pedidoService.getPedido().tieneProductos()) {
            facturaView.mostrarPedido();
        } else {
            System.out.println("No hay productos en el pedido actual.");
            System.out.println("Use la opcion 2 para agregar productos.");
        }
        System.out.println();
    }

    private void GenerarFactura() {
        System.out.println();
        if (!pedidoService.getPedido().tieneProductos()) {
            System.out.println("No se puede generar factura.");
            System.out.println("No hay productos en el pedido.");
            System.out.println("Use la opcion 2 para agregar productos primero.");
            System.out.println();
            return;
        }
        facturaView.imprimirFacturaCompleta();
        pedidoService.cerrarFactura();
        System.out.println();
    }

    private void NuevaMesa() {
        System.out.println();
        pedidoService.getPedido().reiniciar();
        System.out.println("Mesa reiniciada. Lista para nuevo cliente.");
        System.out.println();
    }
}
