package org.ticketmaster.ticketmaster.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.ticketmaster.ticketmaster.MainApp;
import org.ticketmaster.ticketmaster.model.Usuario;
import org.ticketmaster.ticketmaster.repository.UsuarioRepository;
import org.ticketmaster.ticketmaster.util.Validador;

public class RegistroController {

    // Variables conectadas al nuevo FXML
    @FXML private TextField txtNombre;
    @FXML private TextField txtPaterno;
    @FXML private TextField txtMaterno;
    @FXML private TextField txtUsername;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private Label lblMensaje;

    private MainApp mainApp;
    private UsuarioRepository usuarioRepository;

    // Ya no necesitamos el método initialize() porque quitamos los ComboBox

    public RegistroController() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleRegistrar() {
        String nombre = txtNombre.getText().trim();
        String paterno = txtPaterno.getText().trim();
        String materno = txtMaterno.getText().trim();
        String username = txtUsername.getText().trim();
        String correo = txtCorreo.getText().trim();
        String password = txtPassword.getText();
        String confirm = txtConfirmPassword.getText();

        // 1. Validar que no haya campos vacíos
        if (nombre.isEmpty() || paterno.isEmpty() || username.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Por favor, llena todos los campos obligatorios.");
            return;
        }

        // 2. Validar contraseñas
        if (!password.equals(confirm)) {
            lblMensaje.setText("Las contraseñas no coinciden.");
            return;
        }

        // 3. Unir los apellidos (si el materno está vacío, no pone espacio extra)
        String apellidoCompleto = paterno + (materno.isEmpty() ? "" : " " + materno);

        // 4. Crear el usuario con los 5 datos
        Usuario nuevoUsuario = new Usuario(username, correo, password, nombre, apellidoCompleto);

        // 5. Guardar en base de datos
        boolean exito = usuarioRepository.registrarUsuario(nuevoUsuario);

        if (exito) {
            Validador.mostrarAlerta("Éxito", "¡Cuenta creada correctamente!", Alert.AlertType.INFORMATION);
            if (mainApp != null) {
                mainApp.mostrarPantallaLogin();
            }
        } else {
            lblMensaje.setText("Error: El usuario o correo ya están registrados.");
        }
    }

    @FXML
    private void handleVolver() {
        if (mainApp != null) {
            mainApp.mostrarPantallaLogin();
        }
    }
}