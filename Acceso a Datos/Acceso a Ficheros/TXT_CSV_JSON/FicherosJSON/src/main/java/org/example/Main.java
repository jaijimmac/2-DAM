package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ClientInfoStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String rutaSalida = "src/main/resources/Salida.json";
    public static final String rutaEntrada = "src/main/resources/Empleados.json";

    static void main(String[] args) throws FileNotFoundException {
        Main m = new Main();
        Gson gson = new Gson();

        // Empleado e1 = gson.fromJson(new FileReader(rutaEntrada), Empleado.class);

        Empleado[] empleados = gson.fromJson(new FileReader(rutaEntrada), Empleado[].class);
        List<Empleado> listadoEmpleados = Arrays.asList(empleados);

        // List<Empleado> empleados = new ArrayList<>();
        // empleados.add(e1);

        m.empleadoAJSON(listadoEmpleados, rutaSalida);
    }

    public void empleadoAJSON(List<Empleado> empleados, String ruta) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls() // Escribe si hay nulls, si no, pasa
                .setDateFormat("dd/MM/yyyy") // Esto es un Date no un LocalDate
                .registerTypeAdapter(LocalDate.class, fecha("dd/MM/yyyy", LocalDate::from))
                .registerTypeAdapter(LocalDateTime.class, fecha("dd/MM/yyyy HH:mm:ss", LocalDateTime::from))
                .create();

        String json = gson.toJson(empleados);
        FileWriter fichero = null;
        try {
            fichero = new FileWriter(ruta, true);
            fichero.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fichero != null) {
                try {
                    fichero.close();
                } catch (IOException e) {
                    System.out.println("Error al escribir empleado");
                }
            }
        }
    }
}