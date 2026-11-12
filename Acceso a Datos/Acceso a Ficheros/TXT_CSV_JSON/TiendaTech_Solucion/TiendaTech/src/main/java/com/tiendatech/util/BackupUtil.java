package com.tiendatech.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class BackupUtil {

    private BackupUtil() {}

    private static final DateTimeFormatter SELLO = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    /** Copia el fichero a resources/backup/nombre_yyyyMMdd_HHmmss.ext antes de sobrescribirlo. */
    public static Path hacerCopia(Path fichero) throws IOException {
        if (!Files.exists(fichero)) {
            return null; // nada que copiar
        }
        Files.createDirectories(Rutas.BACKUP);
        String[] partes = separarNombre(fichero);
        String nombreCopia = partes[0] + "_" + LocalDateTime.now().format(SELLO) + partes[1];
        Path destino = Rutas.BACKUP.resolve(nombreCopia);
        return Files.copy(fichero, destino, StandardCopyOption.REPLACE_EXISTING);
    }

    /** Restaura el fichero con la copia más reciente. Devuelve la copia usada. */
    public static Optional<Path> restaurarUltimaCopia(Path fichero) throws IOException {
        if (!Files.exists(Rutas.BACKUP)) {
            return Optional.empty();
        }
        String[] partes = separarNombre(fichero);
        // nombre_yyyyMMdd_HHmmss.ext  (con un simple startsWith("productos_") también
        // entrarían las copias de productos_v2.csv)
        Pattern patron = Pattern.compile(Pattern.quote(partes[0]) + "_\\d{8}_\\d{6}" + Pattern.quote(partes[1]));
        Optional<Path> ultima;
        try (Stream<Path> copias = Files.list(Rutas.BACKUP)) {
            ultima = copias
                    .filter(p -> patron.matcher(p.getFileName().toString()).matches())
                    // el sello yyyyMMdd_HHmmss ordena bien alfabéticamente
                    .max((a, b) -> a.getFileName().toString().compareTo(b.getFileName().toString()));
        }
        if (ultima.isPresent()) {
            Files.copy(ultima.get(), fichero, StandardCopyOption.REPLACE_EXISTING);
        }
        return ultima;
    }

    /** "productos.csv" -> ["productos", ".csv"] */
    private static String[] separarNombre(Path fichero) {
        String nombre = fichero.getFileName().toString();
        int punto = nombre.lastIndexOf('.');
        return punto < 0
                ? new String[]{nombre, ""}
                : new String[]{nombre.substring(0, punto), nombre.substring(punto)};
    }
}
