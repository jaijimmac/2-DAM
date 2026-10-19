package org.example.Entity;

import org.example.ENUMS.ValorComparacion;
import org.example.EXCEPTION.GestionFicherosException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class DiffFolder {

    private File archivo1;

    private File archivo2;


    public DiffFolder(File a1, File a2) {
        setFolders(a1, a2);
    }

    public void setFolders(File a1, File a2) {
        if (!a1.isDirectory() || !a1.exists()) {
            throw new GestionFicherosException("El dierectorio " + a1 + " no existe o la ruta es incorrecta.");
        } else if (a2.isDirectory() && a2.exists()) {
            throw new GestionFicherosException("El dierectorio " + a2 + " no existe o la ruta es incorrecta.");
        } else if (a1.listFiles() == null || a2.listFiles() == null) {
            throw new GestionFicherosException("Uno de los directorios esta vacio.");
        }

        this.archivo1 = a1;
        this.archivo2 = a2;
    }

    public File getArchivo1() {

        return archivo1;
    }

    public File getArchivo2() {

        return archivo2;
    }

    public Collection<ResultadoComparacion> comparame() {
        Collection<ResultadoComparacion> result = new ArrayList<>();

        File[] lista1 = Objects.requireNonNull(archivo1.listFiles());
        File[] lista2 = Objects.requireNonNull(archivo2.listFiles());

        for (File f1 : lista1) {
            boolean encontrado = false;
            for (File f2 : lista2) {
                if (f1.getName().equals(f2.getName())) {
                    encontrado = true;
                    if (f1.lastModified() == f2.lastModified()) {
                        result.add(new ResultadoComparacion(f1.getName(), ValorComparacion.IGUALES));
                    } else if (f1.lastModified() > f2.lastModified()) {
                        result.add(new ResultadoComparacion(f1.getName(), ValorComparacion.MENOS_NUEVO_EN_2));
                    } else {
                        result.add(new ResultadoComparacion(f1.getName(), ValorComparacion.MENOS_NUEVO_EN_1));
                    }
                    break;
                }
            }
            if (!encontrado) {
                result.add(new ResultadoComparacion(f1.getName(), ValorComparacion.FALTA_EN_2));
            }
        }

        for (File f2 : lista2) {
            boolean encontrado = Arrays.stream(lista1)
                    .anyMatch(f1 -> f1.getName().equals(f2.getName()));
            if (!encontrado) {
                result.add(new ResultadoComparacion(f2.getName(), ValorComparacion.FALTA_EN_1));
            }
        }

        return result;
    }
}

