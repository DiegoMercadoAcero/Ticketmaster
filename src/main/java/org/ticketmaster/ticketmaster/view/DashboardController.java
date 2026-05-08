package org.ticketmaster.ticketmaster.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.ticketmaster.ticketmaster.MainApp;

public class DashboardController {

    // Cambiamos lblUsuario por lblBienvenida para que coincida con el FXML
    @FXML private Label lblUsuario;
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void inicializarDatos(String nombreCompleto) {
        if (lblUsuario != null) lblUsuario.setText("Hola, " + nombreCompleto);
    }

    // --- MÉTODOS ALINEADOS CON EL NUEVO FXML ---

    @FXML
    private void handleIrTeatro() {
        if (mainApp != null) {
            mainApp.mostrarPantallaTeatro();
        }
    }

    @FXML
    private void handleIrMuseo() {
        if (mainApp != null) {
            mainApp.mostrarPantallaMuseo();
        }
    }

    @FXML
    private void handleIrCine() {
        if (mainApp != null) {
            mainApp.mostrarPantallaCine();
        }
    }

    @FXML
    private void handleIrCuenta() {
        if (mainApp != null) {
            mainApp.mostrarPantallaCuenta();
        }
    }

    @FXML
    private void handleLogout() {
        if (mainApp != null) {
            mainApp.mostrarPantallaLogin(); // Te regresa al inicio de sesión
        }
    }
}