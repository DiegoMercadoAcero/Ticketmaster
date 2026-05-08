package org.ticketmaster.ticketmaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ticketmaster.ticketmaster.model.Evento;
import org.ticketmaster.ticketmaster.repository.UsuarioRepository;
import org.ticketmaster.ticketmaster.view.*;


import java.io.IOException;

public class

MainApp extends Application {

    private Stage primaryStage;
    private UsuarioRepository repositorio = new UsuarioRepository();

    private LoginController loginController;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Ticketmaster Demo");
        mostrarPantallaLogin();
        this.primaryStage.show();
    }

    public void mostrarPantallaLogin() {
        try {
            // 1. Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Login.fxml"));
            Scene scene = new Scene(loader.load());

            // 2. Obtener el controlador y pasarle los datos (MainApp y Repo)
            loginController = loader.getController();
            loginController.setContext(this, repositorio);

            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarPantallaRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Registro.fxml"));
            Scene scene = new Scene(loader.load());

            org.ticketmaster.ticketmaster.view.RegistroController controller = loader.getController();


            controller.setMainApp(this);

            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarDashboard(String nombreUsuario) {
        this.nombreUsuarioActual = nombreUsuario; // <-- Guardamos el nombre en la memoria
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            DashboardController controller = loader.getController();
            controller.setMainApp(this);
            controller.inicializarDatos(nombreUsuario);
            primaryStage.setTitle("Dashboard Principal");
            primaryStage.setScene(scene);
        } catch (IOException e) { e.printStackTrace(); }
    }


    public void mostrarPantallaTeatro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Teatro.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            TeatroController controller = loader.getController();
            controller.setMainApp(this);
            controller.inicializarDatos(this.nombreUsuarioActual); // <--- LÍNEA NUEVA
            primaryStage.setTitle("Cartelera de Teatro");
            primaryStage.setScene(scene);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void mostrarPantallaMuseo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Museo.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            MuseoController controller = loader.getController();
            controller.setMainApp(this);
            controller.inicializarDatos(this.nombreUsuarioActual); // <--- LÍNEA NUEVA
            primaryStage.setTitle("Exhibiciones de Museos");
            primaryStage.setScene(scene);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void mostrarPantallaCine() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Cine.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            CineController controller = loader.getController();
            controller.setMainApp(this);
            controller.inicializarDatos(this.nombreUsuarioActual); // <--- LÍNEA NUEVA
            primaryStage.setTitle("Cartelera de Cine");
            primaryStage.setScene(scene);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void mostrarPantallaCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Cuenta.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            CuentaController controller = loader.getController();
            controller.setMainApp(this);

            // Le pasamos el nombre a la barra superior
            controller.inicializarDatos(this.nombreUsuarioActual);

            primaryStage.setTitle("Mi Cuenta");
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void mostrarPantallaCompra(Evento eventoSeleccionado) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/Compra.fxml"));
            Parent root = loader.load();

            // Le pasamos los datos al controlador de compra
            CompraController controller = loader.getController();
            controller.setMainApp(this);
            controller.initData(eventoSeleccionado); // ¡Aquí le pasamos el evento!
            controller.inicializarDatos(this.nombreUsuarioActual);

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("TicketMaster - Proceso de Compra");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarPantallaLoginConUsuario(String usuarioPredefinido) {
        mostrarPantallaLogin(); // Primero cargas la pantalla normal

        if (loginController != null) {
            loginController.setUsuario(usuarioPredefinido);
        }
    }

    private String nombreUsuarioActual; // <-- Nueva variable para recordar tu nombre

    public String getNombreUsuarioActual() {
        return nombreUsuarioActual;
    }

    public static void main(String[] args) {
        launch();
    }
}