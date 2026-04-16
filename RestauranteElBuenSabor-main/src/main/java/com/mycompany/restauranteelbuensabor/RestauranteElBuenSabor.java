package com.mycompany.restauranteelbuensabor;

import com.mycompany.restauranteelbuensabor.dao.ProductoDAO;
import com.mycompany.restauranteelbuensabor.model.Pedido;
import com.mycompany.restauranteelbuensabor.model.Producto;
import com.mycompany.restauranteelbuensabor.service.PedidoService;
import com.mycompany.restauranteelbuensabor.view.Factura;
import com.mycompany.restauranteelbuensabor.view.Menu;

public class RestauranteElBuenSabor {

    public static void main(String[] args) {
        ProductoDAO productoDAO = new ProductoDAO();
        Producto[] productos    = productoDAO.obtenerTodos();
        Pedido pedido           = new Pedido(productoDAO.totalProductos());
        PedidoService service   = new PedidoService(productos, pedido);
        Factura facturaView = new Factura(service);
        Menu menuView       = new Menu(service, facturaView);

        menuView.iniciar();
    }
}
