package org.ticketmaster.ticketmaster.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.ticketmaster.ticketmaster.MainApp;

public class CuentaController {
    @FXML private Label lblUsuario;
    @FXML private VBox vboxDetalles;
    private MainApp mainApp;
    private String nombreUsuario;

    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp; }
    public void inicializarDatos(String nombreCompleto) {
        this.nombreUsuario = nombreCompleto;
        if (lblUsuario != null) lblUsuario.setText("Hola, " + nombreCompleto);
    }

    @FXML
    private void handlePerfil() {
        vboxDetalles.getChildren().clear();
        vboxDetalles.getChildren().add(crearTarjetaDetalle("👤 Información del Usuario",
                "Nombre registrado: " + (nombreUsuario != null ? nombreUsuario : "Usuario") +
                        "\nEstado de la cuenta: Activa\nMembresía: Básica"));
    }

    @FXML
    private void handleBoletos() {
        vboxDetalles.getChildren().clear();
        vboxDetalles.getChildren().add(crearTarjetaDetalle("🎭 Teatro Metropólitan", "Obra: Mentiras El Musical\nCantidad: 2 Boletos\nEstado: Enviados al correo"));
        vboxDetalles.getChildren().add(crearTarjetaDetalle("🎬 Cinépolis", "Película: Dune Parte Dos (VIP)\nCantidad: 1 Boleto\nEstado: Enviados al correo"));
        vboxDetalles.getChildren().add(crearTarjetaDetalle("🏛️ Museo del Louvre", "Exhibición: Entrada General\nCantidad: 1 Boleto\nEstado: Enviados al correo"));
    }

    @FXML
    private void handlePagos() {
        vboxDetalles.getChildren().clear();
        vboxDetalles.getChildren().add(crearTarjetaDetalle("💳 Tarjeta Visa", "Terminada en **** 4567\nPredeterminada"));
        vboxDetalles.getChildren().add(crearTarjetaDetalle("💳 Tarjeta MasterCard", "Terminada en **** 8901"));
        vboxDetalles.getChildren().add(crearTarjetaDetalle("➕ Agregar nuevo método", "Serás redirigido a la pasarela de pago seguro."));
    }

    private VBox crearTarjetaDetalle(String titulo, String contenido) {
        VBox tarjeta = new VBox(8);
        tarjeta.getStyleClass().add("tarjeta-evento");
        Label lblTitulo = new Label(titulo); lblTitulo.getStyleClass().add("titulo-evento");
        Label lblContenido = new Label(contenido); lblContenido.getStyleClass().add("horario-evento");
        tarjeta.getChildren().addAll(lblTitulo, lblContenido);
        return tarjeta;
    }

    @FXML private void handleVolver() { if (mainApp != null) mainApp.mostrarDashboard(mainApp.getNombreUsuarioActual()); }
    @FXML private void handleLogout() { if (mainApp != null) mainApp.mostrarPantallaLogin(); }
}