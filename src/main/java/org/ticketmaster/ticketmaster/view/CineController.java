package org.ticketmaster.ticketmaster.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.ticketmaster.ticketmaster.MainApp;
import org.ticketmaster.ticketmaster.model.Evento;
import org.ticketmaster.ticketmaster.repository.EventoRepository;
import org.ticketmaster.ticketmaster.util.Validador;

import java.util.List;

public class CineController {
    @FXML private Label lblUsuario;
    @FXML private VBox vboxEventos;
    private MainApp mainApp;
    private String cineSeleccionado = null;

    private EventoRepository eventoRepository = new EventoRepository();

    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp; }

    public void inicializarDatos(String nombreCompleto) {
        if (lblUsuario != null) lblUsuario.setText("Hola, " + nombreCompleto);
    }

    // --- BOTONES DE LA INTERFAZ ---
    @FXML private void handleCinemark() { cargarCartelera("Cinemark"); }
    @FXML private void handleCinepolis() { cargarCartelera("Cinépolis"); }
    @FXML private void handleCinemex() { cargarCartelera("Cinemex"); }
    @FXML private void handleAmc() { cargarCartelera("AMC"); }

    // --- LÓGICA DE BASE DE DATOS ---
    private void cargarCartelera(String nombreCine) {
        cineSeleccionado = nombreCine;
        vboxEventos.getChildren().clear();

        // Buscamos pasándole el lugar y el tipo "Cine"
        List<Evento> cartelera = eventoRepository.obtenerEventos(nombreCine, "Cine");

        if (cartelera.isEmpty()) {
            vboxEventos.getChildren().add(new Label("No hay películas programadas aquí."));
            return;
        }

        for (Evento evento : cartelera) {
            vboxEventos.getChildren().add(crearTarjetaEvento(evento));
        }
    }

    // Fíjate que ahora recibe el objeto "Evento" completo, no solo los textos
    private VBox crearTarjetaEvento(Evento evento) {
        VBox tarjeta = new VBox(8);
        tarjeta.getStyleClass().add("tarjeta-evento");

        Label lblTitulo = new Label(evento.getNombre());
        lblTitulo.getStyleClass().add("titulo-evento");

        Label lblHorarios = new Label(evento.getHorarios());
        lblHorarios.getStyleClass().add("horario-evento");

        // ¡NUEVO BOTÓN DE COMPRA!
        javafx.scene.control.Button btnComprar = new javafx.scene.control.Button("Comprar Boletos");
        btnComprar.setStyle("-fx-background-color: #3b71a8; -fx-text-fill: white; -fx-font-weight: bold;");

        // Al darle clic, viajamos a la nueva pantalla pasándole este evento específico
        btnComprar.setOnAction(e -> {
            if (mainApp != null) {
                mainApp.mostrarPantallaCompra(evento);
            }
        });

        tarjeta.getChildren().addAll(lblTitulo, lblHorarios, btnComprar);
        return tarjeta;
    }

    @FXML private void handleVolver() { if (mainApp != null) mainApp.mostrarDashboard(mainApp.getNombreUsuarioActual()); }
    @FXML private void handleLogout() { if (mainApp != null) mainApp.mostrarPantallaLogin(); }
}