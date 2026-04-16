package com.universidad.mvc.servlet;

import com.universidad.mvc.model.Usuario;
import com.universidad.mvc.service.I18nUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Map<String, String> CREDS = new LinkedHashMap<>();

    static {
        CREDS.put("admin", "Admin123!");
        CREDS.put("viewer", "View456!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        ResourceBundle bundle = I18nUtils.bundle(req.getSession(true));

        if (username != null && CREDS.containsKey(username) && CREDS.get(username).equals(password)) {
            HttpSession session = req.getSession(true);
            String rol = "admin".equals(username) ? "ADMIN" : "VIEWER";
            session.setAttribute("usuarioActual", new Usuario(username, username + "@universidad.edu", rol));
            session.setMaxInactiveInterval(1800);
            resp.sendRedirect(req.getContextPath() + "/productos");
            return;
        }

        req.setAttribute("errorLogin", bundle.getString("login.error"));
        req.setAttribute("username", username);
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}