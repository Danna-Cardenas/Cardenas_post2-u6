package com.universidad.mvc.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/idioma")
public class IdiomaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lang = req.getParameter("lang");
        if ("es".equals(lang) || "en".equals(lang)) {
            req.getSession(true).setAttribute("locale", Locale.forLanguageTag(lang));
        }

        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer != null ? referer : req.getContextPath() + "/productos");
    }
}