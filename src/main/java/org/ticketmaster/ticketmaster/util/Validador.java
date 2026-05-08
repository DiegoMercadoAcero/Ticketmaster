package org.ticketmaster.ticketmaster.util;

import javafx.scene.control.Alert;
import java.util.regex.Pattern;

public class Validador {


    private static final String REGEX_USUARIO_CURP = "^[A-Z]{4}[0-9]{6}[A-Z]{1}[A-Z]{2}[A-Z0-9]{3}[A-Z]{2}[0-9]{2}$";

    // Regex Contraseña (se mantiene igual)
    private static final String REGEX_PASSWORD_PATRON = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8}$";

    public static boolean esUsuarioValido(String usuario) {
        // La propia Regex ya valida que no haya minúsculas, ni espacios,
        // ni caracteres especiales, y que mida exactamente 20.
        return Pattern.matches(REGEX_USUARIO_CURP, usuario);
    }

    public static boolean esPasswordValido(String password) {
        // La Regex ahora hace todo el trabajo (longitud y contenido)
        return Pattern.matches(REGEX_PASSWORD_PATRON, password);
    }

    public static void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}