package org.ticketmaster.ticketmaster.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;

public class GeneradorPDF {

    // AGREGAMOS 'String rutaDestino' AL PRINCIPIO DE LOS PARÁMETROS
    public static void generarBoleto(String rutaDestino, String nombreUsuario, String tipoEvento, String nombreEvento,
                                     String ubicacion, String fechaHorario, int cantidad,
                                     String metodoPago, double total, String codigosBoletos) {

        // BORRAMOS la línea que inventaba el nombre (String rutaDestino = ...) porque ahora viene por parámetro

        try {
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaDestino));
            documento.open();

            // Título
            Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.BLUE);
            Paragraph titulo = new Paragraph("TICKETMASTER - TUS BOLETOS", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph(" ")); // Espacio

            // Datos de la compra
            Font fuenteNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Font fuenteNegrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            documento.add(new Paragraph("¡Hola " + nombreUsuario + ", tu compra fue exitosa!", fuenteNegrita));
            documento.add(new Paragraph(" "));

            documento.add(new Paragraph("Detalles del Evento:", fuenteNegrita));
            documento.add(new Paragraph("Evento: " + nombreEvento + " (" + tipoEvento + ")", fuenteNormal));
            documento.add(new Paragraph("Lugar: " + ubicacion, fuenteNormal));
            documento.add(new Paragraph("Horario: " + fechaHorario, fuenteNormal));
            documento.add(new Paragraph(" "));

            documento.add(new Paragraph("Detalles de Pago:", fuenteNegrita));
            documento.add(new Paragraph("Boletos adquiridos: " + cantidad, fuenteNormal));
            documento.add(new Paragraph("Método de Pago: " + metodoPago, fuenteNormal));
            documento.add(new Paragraph(String.format("Total Pagado: $%.2f", total), fuenteNormal));
            documento.add(new Paragraph(" "));

            documento.add(new Paragraph("CÓDIGOS DE ACCESO ÚNICOS:", fuenteNegrita));
            documento.add(new Paragraph(codigosBoletos, fuenteNormal));
            documento.add(new Paragraph(" "));

            // Generación del Código QR
            String datosQR = "Titular: " + nombreUsuario + "\n" +
                    "Evento: " + nombreEvento + "\n" +
                    "Fecha/Hora: " + fechaHorario + "\n" +
                    "Boletos: " + cantidad + "\n" +
                    "Accesos:\n" + codigosBoletos;

            BarcodeQRCode qr = new BarcodeQRCode(datosQR, 200, 200, null);
            Image imagenQR = qr.getImage();
            imagenQR.setAlignment(Element.ALIGN_CENTER);
            documento.add(imagenQR);

            documento.close();
            System.out.println("PDF con QR guardado en: " + rutaDestino);

        } catch (Exception e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
        }
    }
}