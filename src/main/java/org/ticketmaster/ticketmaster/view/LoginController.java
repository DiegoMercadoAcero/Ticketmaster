package org.ticketmaster.ticketmaster.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.ticketmaster.ticketmaster.MainApp;
import org.ticketmaster.ticketmaster.model.Usuario; // <-- Importante tener esto
import org.ticketmaster.ticketmaster.repository.UsuarioRepository;
import org.ticketmaster.ticketmaster.util.Validador;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMensaje;

    private MainApp mainApp;
    private UsuarioRepository repositorio;

    public void setContext(MainApp mainApp, UsuarioRepository repositorio) {
        this.mainApp = mainApp;
        this.repositorio = repositorio;
    }

    @FXML
    public void initialize() {
        // Convierte el texto del usuario a mayúsculas automáticamente mientras escribe
        txtUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            txtUsuario.setText(newValue.toUpperCase());
        });
    }

    @FXML
    private void handleLogin() {
        String u = txtUsuario.getText();
        String p = txtPassword.getText();

        if (repositorio.validarCredenciales(u, p)) {
            // LOGIN EXITOSO
            Validador.mostrarAlerta("¡Bienvenido!", "Inicio de sesión exitoso.", Alert.AlertType.INFORMATION);

            // --- ESTE ES EL CAMBIO CLAVE ---
            // 1. Obtenemos el objeto Usuario completo usando la CURP (u)
            Usuario usuarioLogueado = repositorio.obtenerUsuario(u);

            // 2. Extraemos su nombre y apellido y los unimos
            String nombreAMostrar = usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellido();

            // 3. Mandamos el nombre completo al Dashboard en lugar de la CURP 'u'
            mainApp.mostrarDashboard(nombreAMostrar);
            // ---------------------------------

        } else {
            // LOGIN FALLIDO
            Validador.mostrarAlerta("Acceso Denegado", "Usuario o contraseña incorrectos.", Alert.AlertType.ERROR);

            // Opcional: También ponerlo en el label rojo
            lblMensaje.setText("Credenciales incorrectas.");
        }
    }

    public void setUsuario(String usuarioGenerado) {
        System.out.println("3. LoginController recibió la instrucción con: " + usuarioGenerado);

        if (txtUsuario != null) {
            txtUsuario.setText(usuarioGenerado);
            txtPassword.requestFocus();
            System.out.println("4. ÉXITO. El texto se pegó en el campo txtUsuario.");
        } else {
            System.out.println("¡ERROR! 4. txtUsuario es NULL. Falla en el FXML o el @FXML.");
        }
    }

    @FXML
    private void handleIrRegistro() {
        mainApp.mostrarPantallaRegistro();
    }
}