package org.ticketmaster.ticketmaster.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.ticketmaster.ticketmaster.MainApp;
import org.ticketmaster.ticketmaster.model.Evento;
import org.ticketmaster.ticketmaster.repository.CompraRepository;
import org.ticketmaster.ticketmaster.util.GeneradorPDF;
import org.ticketmaster.ticketmaster.util.WhatsAppService;

import java.io.File;
import java.util.List;

public class CompraController {
    @FXML private Label lblNombre, lblPrecio;
    @FXML private ComboBox<String> cmbHorario, cmbPago;
    @FXML private Spinner<Integer> spnCantidad;
    @FXML private Label lblUsuario;
    @FXML private TextField txtTelefono;

    private MainApp mainApp;
    private Evento evento;
    private CompraRepository repo = new CompraRepository();

    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp; }
    public void inicializarDatos(String nombreCompleto) {
        if (lblUsuario != null) lblUsuario.setText("Hola, " + nombreCompleto);
    }

    public void initData(Evento evento) {
        this.evento = evento;
        lblNombre.setText(evento.getNombre());
        lblPrecio.setText("$" + evento.getPrecio());

        cmbHorario.getItems().addAll(evento.getHorarios().split("\n"));
        spnCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        cmbPago.getItems().addAll("Tarjeta Visa", "MasterCard", "PayPal", "Efectivo OXXO");
    }

    @FXML
    private void handlePagar() {
        if (cmbHorario.getValue() == null || cmbPago.getValue() == null || txtTelefono.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Llena todos los campos, incluyendo tu WhatsApp.", Alert.AlertType.ERROR);
            return;
        }

        String usuarioActual = mainApp.getNombreUsuarioActual();
        int cantidadBoletos = spnCantidad.getValue();
        String metodoSeleccionado = cmbPago.getValue();
        double precioEvento = evento.getPrecio();

        // 1. Guardamos en la Base de Datos
        List<String> codigos = repo.procesarCompra(usuarioActual, evento.getIdEvento(), cantidadBoletos, metodoSeleccionado, precioEvento);

        // 2. Si hay códigos, la compra fue exitosa
        if (codigos != null) {
            StringBuilder codigosFormat = new StringBuilder();
            for (String c : codigos) {
                codigosFormat.append("▶ ").append(c).append("\n");
            }

            double totalCompra = cantidadBoletos * precioEvento;
            String telefonoUsuario = txtTelefono.getText().trim();

            // --- SERVICIO 1: WHATSAPP ---
            WhatsAppService.enviarConfirmacion(
                    telefonoUsuario, usuarioActual, evento.getTipoEvento(), evento.getNombre(),
                    evento.getUbicacion(), cmbHorario.getValue(), cantidadBoletos,
                    metodoSeleccionado, totalCompra, codigosFormat.toString()
            );

            // --- SERVICIO 2: SELECTOR DE ARCHIVOS PARA EL PDF ---
            // Configuramos la ventana de guardado
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Boleto PDF");
            // Filtramos para que solo pueda guardar en formato .pdf
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf"));
            // Le sugerimos un nombre automático
            fileChooser.setInitialFileName("Boleto_" + usuarioActual.replace(" ", "_") + ".pdf");

            // Obtenemos la ventana actual para mostrar el cuadro de diálogo sobre ella
            Window stage = cmbHorario.getScene().getWindow();
            File archivoElegido = fileChooser.showSaveDialog(stage);

            // Si el usuario eligió una ruta y le dio a "Guardar"
            if (archivoElegido != null) {
                // Generamos el PDF pasándole la ruta que eligió el usuario (archivoElegido.getAbsolutePath())
                GeneradorPDF.generarBoleto(
                        archivoElegido.getAbsolutePath(), // ¡NUEVO PARÁMETRO!
                        usuarioActual, evento.getTipoEvento(), evento.getNombre(),
                        evento.getUbicacion(), cmbHorario.getValue(), cantidadBoletos,
                        metodoSeleccionado, totalCompra, codigosFormat.toString()
                );

                mostrarAlerta("¡Éxito!", "Compra realizada exitosamente.\n\n🎟️ Tus códigos: " + codigos + "\n📲 Te hemos enviado un WhatsApp de confirmación.\n📄 Tu boleto ha sido guardado donde elegiste.", Alert.AlertType.INFORMATION);
            } else {
                // Si el usuario le dio a "Cancelar" en la ventana de guardado
                mostrarAlerta("¡Éxito!", "Compra realizada exitosamente.\n\n🎟️ Tus códigos: " + codigos + "\n📲 Te hemos enviado un WhatsApp de confirmación.\n⚠️ Decidiste no descargar el PDF, pero tu compra está segura.", Alert.AlertType.INFORMATION);
            }

            // Volvemos al menú principal
            mainApp.mostrarDashboard(usuarioActual);
        } else {
            mostrarAlerta("Error", "No hay boletos suficientes", Alert.AlertType.WARNING);
        }
    }

    @FXML private void handleCancelar() { mainApp.mostrarDashboard(mainApp.getNombreUsuarioActual()); }
    @FXML private void handleLogout() { if (mainApp != null) mainApp.mostrarPantallaLogin(); }

    private void mostrarAlerta(String t, String c, Alert.AlertType type) {
        Alert a = new Alert(type); a.setTitle(t); a.setContentText(c); a.showAndWait();
    }
}