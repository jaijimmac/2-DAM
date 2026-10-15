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
        if (a1.isDirectory() && a1.exists()) {
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

        File ficheroMayor;
        File ficheroMenor;

        if (Objects.requireNonNull(this.archivo1.listFiles()).length > Objects.requireNonNull(this.archivo2.listFiles()).length) {
            ficheroMayor = archivo1;
            ficheroMenor = archivo2;
        } else {
            ficheroMayor = archivo2;
            ficheroMenor = archivo1;
        }

        File[] lista1 = ficheroMayor.listFiles();
        File[] lista2 = ficheroMenor.listFiles();

        ResultadoComparacion resultadoComparacion;
        for (File file : lista1) {
            for (File file2 : lista2) {
                if (file.getName().equals(file2.getName())) {
                    if (file.lastModified() == file2.lastModified()) {
                        resultadoComparacion = new ResultadoComparacion(file.getName(), ValorComparacion.IGUALES);
                        result.add(resultadoComparacion);
                    } else if (file.lastModified() > file2.lastModified()) {
                        resultadoComparacion = new ResultadoComparacion(file.getName(), ValorComparacion.MENOS_NUEVO_EN_2);
                        result.add(resultadoComparacion);
                    } else if (file.lastModified() < file2.lastModified()) {
                        resultadoComparacion = new ResultadoComparacion(file.getName(), ValorComparacion.MENOS_NUEVO_EN_1);
                        result.add(resultadoComparacion);
                    }
                }
                resultadoComparacion = new ResultadoComparacion(file.getName(), ValorComparacion.FALTA_EN_2);
                result.add(resultadoComparacion);
            }
        }

        for (File a:lista1){
            for (ResultadoComparacion resultadoComparacion1: result){
                if (!a.getName().equals(resultadoComparacion1.getNomFichero())){
                    resultadoComparacion = new ResultadoComparacion(a.getName(), ValorComparacion.FALTA_EN_1);
                    result.add(resultadoComparacion);
                }
            }
        }

        return result;
    }
}

