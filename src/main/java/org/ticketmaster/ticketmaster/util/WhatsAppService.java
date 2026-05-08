package org.ticketmaster.ticketmaster.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class WhatsAppService {


    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";


    public static final String TWILIO_WHATSAPP_NUMBER = "whatsapp:+19784195882";

    public static void enviarConfirmacion(String telefonoDestino, String nombreUsuario, String tipoEvento,
                                          String nombreEvento, String ubicacion, String fechaHorario,
                                          int cantidad, String metodoPago, double total, String codigosBoletos) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


        String mensajeTexto = String.format(
                "🎟️ *¡Hola %s, tu compra fue exitosa!*\n\n" +
                        "📌 *Evento:* %s (%s)\n" +
                        "📍 *Lugar:* %s\n" +
                        "📅 *Horario:* %s\n" +
                        "🎫 *Boletos:* %d\n" +
                        "💳 *Método de Pago:* %s\n" +
                        "💰 *Total pagado:* $%.2f\n\n" +
                        "🔑 *Tus códigos de acceso (QR/Confirmación):*\n%s\n\n" +
                        "¡TicketMaster Demo te desea que disfrutes tu evento!",
                nombreUsuario, nombreEvento, tipoEvento, ubicacion, fechaHorario, cantidad, metodoPago, total, codigosBoletos
        );

        try {
            Message message = Message.creator(
                            new PhoneNumber("whatsapp:" + telefonoDestino),
                            new PhoneNumber(TWILIO_WHATSAPP_NUMBER),
                            mensajeTexto)
                    .create();

            System.out.println("✅ WhatsApp enviado correctamente. ID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("❌ Error enviando WhatsApp: " + e.getMessage());
        }
    }
}