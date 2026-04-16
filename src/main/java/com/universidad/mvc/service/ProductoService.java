package com.universidad.mvc.service;

import com.universidad.mvc.model.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductoService {
    private final List<Producto> productos = new ArrayList<>();
    private final AtomicInteger secuencia = new AtomicInteger(3);

    public ProductoService() {
        productos.add(new Producto(1, "Laptop", "Tecnologia", 899.99, 12));
        productos.add(new Producto(2, "Mouse", "Accesorios", 19.99, 35));
        productos.add(new Producto(3, "Monitor", "Tecnologia", 179.50, 8));
    }

    public synchronized List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    public synchronized Optional<Producto> buscarPorId(int id) {
        return productos.stream().filter(producto -> producto.getId() == id).findFirst();
    }

    public synchronized void guardar(Producto producto) {
        producto.setId(secuencia.incrementAndGet());
        productos.add(producto);
    }

    public synchronized boolean actualizar(Producto producto) {
        Optional<Producto> existente = buscarPorId(producto.getId());
        if (existente.isEmpty()) {
            return false;
        }

        Producto actual = existente.get();
        actual.setNombre(producto.getNombre());
        actual.setCategoria(producto.getCategoria());
        actual.setPrecio(producto.getPrecio());
        actual.setStock(producto.getStock());
        return true;
    }

    public synchronized boolean eliminar(int id) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}