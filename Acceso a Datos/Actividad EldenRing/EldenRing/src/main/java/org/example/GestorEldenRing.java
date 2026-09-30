package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Entity.Encuentros;
import org.example.Entity.SinLuz;
import org.example.Service.EncuentroService;
import org.example.Service.SinLuzService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GestorEldenRing implements Comparable<Encuentros> {

    private static final Logger logger = LogManager.getLogger(GestorEldenRing.class);

    private final SinLuzService sinLuzService = new SinLuzService();
    private final EncuentroService encuentroService = new EncuentroService(sinLuzService);

    public static void main(String[] args) {

        GestorEldenRing gestor = new GestorEldenRing();

        List<SinLuz> sinLuzes = List.of(
                new SinLuz("SinLuz3"),
                new SinLuz("SinLuz2"),
                new SinLuz("SinLuz1")
        );

        sinLuzes.forEach(gestor.sinLuzService::agregarSinLuz);

        logger.debug("+----------------------+");
        logger.debug("|      SIN LUZ         |");
        logger.debug("+----------------------+");

        gestor.sinLuzService.getListaSinLuz().forEach(sinLuz ->
                logger.debug(String.format("| %-20s |", sinLuz))
        );

        logger.debug("+----------------------+");
        logger.debug("+----------------------+");

        // PRUEBA DE BUSCADOR DE SINLUZ

        gestor.sinLuzService.obtenerSinLuz(1L);
        gestor.sinLuzService.obtenerSinLuz(5L);


        // AÑADIMOS ENCUEENTROS

        List<String> listaNombres1 = new ArrayList<>();
        listaNombres1.add("Pedro");
        listaNombres1.add("Maria");
        listaNombres1.add("Juan");

        List<String> listaNombres2 = new ArrayList<>();
        listaNombres2.add("Pepe");
        listaNombres2.add("Salma");
        listaNombres2.add("Lucas");

        List<String> listaNombres3 = new ArrayList<>();
        listaNombres3.add("Sandra");
        listaNombres3.add("Paula");
        listaNombres3.add("Claudia");

        List<String> listaNombres4 = new ArrayList<>();
        listaNombres4.add("Alberto");
        listaNombres4.add("Laura");
        listaNombres4.add("Diego");

        List<String> listaNombres5 = new ArrayList<>();
        listaNombres5.add("Carlos");
        listaNombres5.add("Elena");
        listaNombres5.add("Mario");

        List<String> listaNombres6 = new ArrayList<>();
        listaNombres6.add("Daniel");
        listaNombres6.add("Lucia");
        listaNombres6.add("Javier");

        List<String> listaNombres7 = new ArrayList<>();
        listaNombres7.add("Antonio");
        listaNombres7.add("Sofia");
        listaNombres7.add("Raul");


        Encuentros enc1 = new Encuentros("Encuentro 1", LocalDate.now(), 5, listaNombres1);
        Encuentros enc2 = new Encuentros("Encuentro 2", LocalDate.now(), 1, listaNombres2);
        Encuentros enc3 = new Encuentros("Encuentro 3", LocalDate.now(), 10, listaNombres3);
        Encuentros enc4 = new Encuentros("Encuentro 4", LocalDate.now(), 7, listaNombres4);
        Encuentros enc5 = new Encuentros("Encuentro 5", LocalDate.now(), 3, listaNombres5);
        Encuentros enc6 = new Encuentros("Encuentro 6", LocalDate.now(), 8, listaNombres6);
        Encuentros enc7 = new Encuentros("Encuentro 7", LocalDate.now(), 2, listaNombres7);

        gestor.encuentroService.agregarEncuentro(5L, enc1);
        gestor.encuentroService.agregarEncuentro(1L, enc2);
        gestor.encuentroService.agregarEncuentro(2L, enc3);
        gestor.encuentroService.agregarEncuentro(2L, enc4);
        gestor.encuentroService.agregarEncuentro(3L, enc5);
        gestor.encuentroService.agregarEncuentro(3L, enc6);
        gestor.encuentroService.agregarEncuentro(5L, enc7);

        logger.debug("+----------------------+");
        logger.debug("|      ENCUENTROS        |");
        logger.debug("+----------------------+");

        gestor.encuentroService.listadoEncuentros().forEach(encuentros ->
                logger.debug(String.format("| %-20s |", encuentros))
        );

        logger.debug("+----------------------+");
        logger.debug("+----------------------+");


        logger.debug("+----------------------+");
        logger.debug("|      SIN LUZ         |");
        logger.debug("+----------------------+");

        gestor.sinLuzService.getListaSinLuz().forEach(sinLuz ->
                logger.debug(String.format("| %-20s |", sinLuz))
        );

        logger.debug("+----------------------+");
        logger.debug("+----------------------+");


        //PRUEBA DE ENCUEENTRO REPETIDO //

        gestor.encuentroService.agregarEncuentro(1L, enc2);
        gestor.encuentroService.agregarEncuentro(1L, enc2);
        gestor.encuentroService.agregarEncuentro(1L, enc2);
        gestor.encuentroService.agregarEncuentro(1L, enc2);
        gestor.encuentroService.agregarEncuentro(1L, enc2);
        gestor.encuentroService.agregarEncuentro(1L, enc2);
    }

    @Override
    public int compareTo(Encuentros o) {
        return 0;
    }
}