package com.universidad.mvc.service;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

public final class I18nUtils {
    private I18nUtils() {
    }

    public static Locale locale(HttpSession session) {
        Object locale = session != null ? session.getAttribute("locale") : null;
        if (locale instanceof Locale) {
            return (Locale) locale;
        }
        return Locale.ENGLISH;
    }

    public static ResourceBundle bundle(HttpSession session) {
        return ResourceBundle.getBundle("messages", locale(session));
    }
}