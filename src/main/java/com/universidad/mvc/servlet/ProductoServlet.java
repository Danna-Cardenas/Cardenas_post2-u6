package com.universidad.mvc.servlet;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.service.I18nUtils;
import com.universidad.mvc.service.ProductoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {
    private final ProductoService service = new ProductoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!verificarSesion(req, resp)) {
            return;
        }

        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");

        if ("nuevo".equals(accion)) {
            prepararFormulario(req, new Producto(), "crear");
            forward(req, resp, "/WEB-INF/views/formulario.jsp");
            return;
        }

        if ("editar".equals(accion)) {
            int id = parseId(req.getParameter("id"));
            Optional<Producto> producto = service.buscarPorId(id);
            if (producto.isEmpty()) {
                setFlash(req, "product.notFound");
                resp.sendRedirect(req.getContextPath() + "/productos");
                return;
            }

            prepararFormulario(req, producto.get(), "editar");
            forward(req, resp, "/WEB-INF/views/formulario.jsp");
            return;
        }

        cargarListado(req);
        forward(req, resp, "/WEB-INF/views/lista.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!verificarSesion(req, resp)) {
            return;
        }

        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");

        if ("eliminar".equals(accion)) {
            eliminar(req, resp);
            return;
        }

        guardar(req, resp);
    }

    private void cargarListado(HttpServletRequest req) {
        req.setAttribute("productos", service.listar());
        String flash = consumeFlash(req);
        if (flash != null) {
            req.setAttribute("mensaje", flash);
        }
    }

    private void guardar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        String precioStr = req.getParameter("precio");
        String stockStr = req.getParameter("stock");
        String categoria = req.getParameter("categoria");
        String idStr = req.getParameter("id");

        Map<String, String> errores = new LinkedHashMap<>();
        ResourceBundle bundle = I18nUtils.bundle(req.getSession(false));

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", bundle.getString("product.name.required"));
        } else if (nombre.trim().length() > 100) {
            errores.put("nombre", bundle.getString("product.name.max"));
        }

        if (categoria == null || categoria.trim().isEmpty()) {
            errores.put("categoria", bundle.getString("product.category.required"));
        }

        double precio = 0;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                errores.put("precio", bundle.getString("product.price.negative"));
            }
        } catch (NumberFormatException e) {
            errores.put("precio", bundle.getString("product.price.invalid"));
        }

        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                errores.put("stock", bundle.getString("product.stock.negative"));
            }
        } catch (NumberFormatException e) {
            errores.put("stock", bundle.getString("product.stock.invalid"));
        }

        if (!errores.isEmpty()) {
            req.setAttribute("errores", errores);
            req.setAttribute("nombre", nombre);
            req.setAttribute("precio", precioStr);
            req.setAttribute("stock", stockStr);
            req.setAttribute("categoria", categoria);
            req.setAttribute("modo", idStr != null && !idStr.isBlank() ? "editar" : "crear");
            if (idStr != null && !idStr.isBlank()) {
                req.setAttribute("id", idStr);
            }
            forward(req, resp, "/WEB-INF/views/formulario.jsp");
            return;
        }

        int id = parseId(idStr);
        Producto producto = new Producto(id, nombre.trim(), categoria.trim(), precio, stock);
        HttpSession session = req.getSession(false);

        if (id > 0) {
            service.actualizar(producto);
            setFlash(session, bundle.getString("product.updated"));
        } else {
            service.guardar(producto);
            setFlash(session, bundle.getString("product.saved"));
        }

        resp.sendRedirect(req.getContextPath() + "/productos");
    }

    private void eliminar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = parseId(req.getParameter("id"));
        HttpSession session = req.getSession(false);
        ResourceBundle bundle = I18nUtils.bundle(session);

        if (service.eliminar(id)) {
            setFlash(session, bundle.getString("product.deleted"));
        } else {
            setFlash(session, bundle.getString("product.notFound"));
        }

        resp.sendRedirect(req.getContextPath() + "/productos");
    }

    private boolean verificarSesion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioActual") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    private void prepararFormulario(HttpServletRequest req, Producto producto, String modo) {
        req.setAttribute("producto", producto);
        req.setAttribute("modo", modo);
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String view) throws ServletException, IOException {
        req.getRequestDispatcher(view).forward(req, resp);
    }

    private int parseId(String value) {
        try {
            return value == null || value.isBlank() ? 0 : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void setFlash(HttpServletRequest req, String message) {
        HttpSession session = req.getSession(false);
        setFlash(session, message);
    }

    private void setFlash(HttpSession session, String message) {
        if (session != null) {
            session.setAttribute("flashMessage", message);
        }
    }

    private String consumeFlash(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }

        Object flash = session.getAttribute("flashMessage");
        if (flash == null) {
            return null;
        }

        session.removeAttribute("flashMessage");
        return flash.toString();
    }
}