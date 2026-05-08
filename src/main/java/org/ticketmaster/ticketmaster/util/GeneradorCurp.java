package org.ticketmaster.ticketmaster.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GeneradorCurp {

    public static String generarCurpModificada(String nombre, String paterno, String materno,
                                               LocalDate fechaNac, String sexo, String estado) {
        StringBuilder curp = new StringBuilder();

        // 1. Primeras 4 letras (Aprox: 2 del paterno, 1 materno, 1 nombre)
        curp.append(paterno.substring(0, Math.min(2, paterno.length())).toUpperCase());
        curp.append(materno.isEmpty() ? "X" : materno.substring(0, 1).toUpperCase());
        curp.append(nombre.substring(0, 1).toUpperCase());

        // 2. Fecha de nacimiento (6 números: YYMMDD)
        String fechaStr = fechaNac.format(DateTimeFormatter.ofPattern("yyMMdd"));
        curp.append(fechaStr);

        // 3. Sexo (1 letra: H o M)
        curp.append(sexo.equals("Hombre") ? "H" : "M");

        // 4. Estado (2 letras, tomamos las primeras 2 como simulador)
        String estadoCode = estado.substring(0, 2).toUpperCase();
        curp.append(estadoCode);

        // 5. Consonantes internas y homoclave (3 letras/números simulación)
        curp.append("XYZ"); // Simulando las consonantes internas

        // --- AQUÍ LLEVAMOS 16 CARACTERES. Agregamos 2 de homoclave para los 18 normales ---
        curp.append("A1"); // Homoclave simulada (18 caracteres total)

        // --- TU REGLA PERSONALIZADA (Caracteres 19 y 20) ---
        // 19: Letra inicial del Estado
        curp.append(estado.substring(0, 1).toUpperCase());

        // 20: Número Random (0-9)
        int numeroRandom = new Random().nextInt(10);
        curp.append(numeroRandom);

        // Asegurarnos de que no tenga espacios y sea todo mayúscula
        return curp.toString().replaceAll("\\s+", "").toUpperCase();
    }
}
