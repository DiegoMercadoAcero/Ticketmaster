module org.ticketmaster.ticketmaster {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.sql;
    requires jbcrypt;
    requires twilio;
    requires itextpdf;

    opens org.ticketmaster.ticketmaster to javafx.fxml;
    opens org.ticketmaster.ticketmaster.view to javafx.fxml;
    exports org.ticketmaster.ticketmaster;
}