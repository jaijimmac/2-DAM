package com.tiendatech.util;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Lee config.properties. Si falta el fichero o una clave, usa valores por defecto. */
public class Config {

    private final double iva;
    private final double descuentoVip;
    private final int umbralStockBajo;

    public Config(Path ruta) {
        Properties props = new Properties();
        if (Files.exists(ruta)) {
            try (Reader r = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
                props.load(r);
            } catch (IOException e) {
                System.err.println("No se pudo leer " + ruta + ", se usan valores por defecto");
            }
        }
        this.iva = Double.parseDouble(props.getProperty("iva", "0.21"));
        this.descuentoVip = Double.parseDouble(props.getProperty("descuento_vip", "0.10"));
        this.umbralStockBajo = Integer.parseInt(props.getProperty("umbral_stock_bajo", "5"));
    }

    public double getIva() { return iva; }
    public double getDescuentoVip() { return descuentoVip; }
    public int getUmbralStockBajo() { return umbralStockBajo; }
}
