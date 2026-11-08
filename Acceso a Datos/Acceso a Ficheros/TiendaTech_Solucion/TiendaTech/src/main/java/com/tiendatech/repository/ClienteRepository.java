package com.tiendatech.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tiendatech.exception.DatosInvalidosException;
import com.tiendatech.model.Cliente;
import com.tiendatech.model.Direccion;
import com.tiendatech.util.BackupUtil;
import com.tiendatech.util.FechaUtil;
import com.tiendatech.util.JsonUtil;
import com.tiendatech.util.JsonUtil.Fragmento;
import com.tiendatech.util.Validador;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repositorio de clientes. "Base de datos": fichero JSON.
 *
 * El fichero puede venir con errores DENTRO de un objeto:
 *   - de sintaxis (falta una coma o una comilla)       -> JsonUtil lo aísla
 *   - de tipo ("telefonos": "699..." en vez de lista,
 *     "direccion": "texto" en vez de objeto,
 *     "vip": "si" en vez de true/false...)             -> se comprueba campo a campo
 *   - de contenido (email inválido, fecha imposible...) -> Validador
 * En todos los casos se descarta SOLO ese cliente y se registra con logger.error.
 */
public class ClienteRepository {

    private static final Logger logger = LoggerFactory.getLogger(ClienteRepository.class);

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()        // para que "direccion": null se escriba
            .disableHtmlEscaping()
            .create();

    private final Path ruta;
    private final Map<String, Cliente> clientes = new LinkedHashMap<>();

    /* Mayor número de id visto en el fichero, incluidos los clientes descartados,
       para no generar un id que ya existe en el fichero aunque sea erróneo. */
    private int maxNumeroLeido = 0;

    public ClienteRepository(Path ruta) {
        this.ruta = ruta;
    }

    // ===================== LECTURA =====================

    public void cargar() throws IOException {
        clientes.clear();
        maxNumeroLeido = 0;

        String contenido = Files.readString(ruta, StandardCharsets.UTF_8).trim();
        if (!contenido.startsWith("[") || !contenido.endsWith("]")) {
            logger.warn("{} no empieza por '[' o no acaba en ']': se intentan leer los objetos igualmente",
                    ruta.getFileName());
        }

        for (Fragmento f : JsonUtil.extraerObjetos(contenido)) {
            String idAprox = f.idAproximado();
            if (idAprox != null) {
                maxNumeroLeido = Math.max(maxNumeroLeido, numeroDeId(idAprox));
            }

            // 1) Sintaxis: ¿el objeto es JSON válido?
            JsonObject o;
            try {
                o = JsonUtil.parsearEstricto(f.texto()).getAsJsonObject();
            } catch (JsonParseException e) {
                logger.error("Cliente {} descartado: JSON mal formado ({})", f.descripcion(), mensajeCorto(e));
                continue;
            }

            // 2) Tipos y contenido
            try {
                Cliente c = parsear(o);
                if (clientes.containsKey(c.getId())) {
                    logger.error("Cliente {} descartado: id duplicado", f.descripcion());
                    continue;
                }
                clientes.put(c.getId(), c);
            } catch (DatosInvalidosException e) {
                logger.error("Cliente {} descartado: {}", f.descripcion(), e.getMessage());
            } catch (RuntimeException e) {
                // Red de seguridad: un objeto raro nunca debe parar la carga del resto
                logger.error("Cliente {} descartado: estructura inesperada", f.descripcion(), e);
            }
        }
        logger.info("Cargados {} clientes de {}", clientes.size(), ruta.getFileName());
    }

    private Cliente parsear(JsonObject o) throws DatosInvalidosException {
        Cliente c = new Cliente();
        c.setId(texto(o, "id"));
        c.setNombre(texto(o, "nombre"));
        c.setApellidos(texto(o, "apellidos"));
        c.setEmail(texto(o, "email"));

        String fecha = texto(o, "fecha_alta");
        if (fecha != null) {
            try {
                c.setFechaAlta(FechaUtil.parsear(fecha));
            } catch (DateTimeParseException e) {
                throw new DatosInvalidosException("fecha de alta no válida (" + fecha + ")");
            }
        }

        c.setDireccion(leerDireccion(o.get("direccion")));
        c.setTelefonos(leerTelefonos(o.get("telefonos")));
        c.setVip(leerVip(o.get("vip")));

        Validador.validarCliente(c);
        return c;
    }

    /** direccion: puede faltar o ser null; si viene, TIENE que ser un objeto con calle y ciudad. */
    private Direccion leerDireccion(JsonElement e) throws DatosInvalidosException {
        if (e == null || e.isJsonNull()) {
            return null;
        }
        if (!e.isJsonObject()) {
            throw new DatosInvalidosException("'direccion' debe ser un objeto o null, no " + e);
        }
        JsonObject d = e.getAsJsonObject();
        Direccion dir = new Direccion(texto(d, "calle"), texto(d, "ciudad"), texto(d, "cp"));
        if (Validador.vacio(dir.getCalle()) || Validador.vacio(dir.getCiudad())) {
            throw new DatosInvalidosException("dirección incompleta (faltan calle o ciudad)");
        }
        return dir;
    }

    /** telefonos: puede faltar; si viene, TIENE que ser una lista de textos de 9 dígitos. */
    private List<String> leerTelefonos(JsonElement e) throws DatosInvalidosException {
        List<String> telefonos = new ArrayList<>();
        if (e == null || e.isJsonNull()) {
            return telefonos;
        }
        if (!e.isJsonArray()) {
            throw new DatosInvalidosException("'telefonos' debe ser una lista, no " + e);
        }
        for (JsonElement t : e.getAsJsonArray()) {
            if (!t.isJsonPrimitive()) {
                throw new DatosInvalidosException("teléfono con formato incorrecto (" + t + ")");
            }
            String tel = t.getAsString().trim();
            Validador.validarTelefono(tel);
            telefonos.add(tel);
        }
        return telefonos;
    }

    /** vip: puede faltar (false); si viene, TIENE que ser true o false (no "si", ni 1). */
    private boolean leerVip(JsonElement e) throws DatosInvalidosException {
        if (e == null || e.isJsonNull()) {
            return false;
        }
        if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isBoolean()) {
            return e.getAsBoolean();
        }
        throw new DatosInvalidosException("'vip' debe ser true o false, no " + e);
    }

    /**
     * Devuelve el texto del campo, o null si no existe o es null.
     * Si el campo es un objeto o una lista, es un error de tipo.
     * (getAsString() sobre un objeto lanzaría una excepción y pararía la carga.)
     */
    private static String texto(JsonObject o, String campo) throws DatosInvalidosException {
        JsonElement e = o.get(campo);
        if (e == null || e.isJsonNull()) {
            return null;
        }
        if (!e.isJsonPrimitive()) {
            throw new DatosInvalidosException("el campo '" + campo + "' debe ser un texto, no " + e);
        }
        return e.getAsString().trim();
    }

    private static String mensajeCorto(Exception e) {
        String m = e.getMessage() == null ? e.toString() : e.getMessage();
        int corte = m.indexOf(" See https");   // Gson añade un enlace de ayuda muy largo
        return corte > 0 ? m.substring(0, corte) : m;
    }

    // ===================== ESCRITURA =====================

    public void guardar() throws IOException {
        BackupUtil.hacerCopia(ruta);
        JsonArray array = new JsonArray();
        for (Cliente c : clientes.values()) {
            array.add(aJson(c));
        }
        Files.writeString(ruta, GSON.toJson(array), StandardCharsets.UTF_8);
    }

    private JsonObject aJson(Cliente c) {
        JsonObject o = new JsonObject();
        o.addProperty("id", c.getId());
        o.addProperty("nombre", c.getNombre());
        o.addProperty("apellidos", c.getApellidos());
        o.addProperty("email", c.getEmail());
        o.addProperty("fecha_alta", FechaUtil.formatear(c.getFechaAlta())); // otra vez dd/MM/yyyy

        if (c.getDireccion() == null) {
            o.add("direccion", JsonNull.INSTANCE);
        } else {
            JsonObject d = new JsonObject();
            d.addProperty("calle", c.getDireccion().getCalle());
            d.addProperty("ciudad", c.getDireccion().getCiudad());
            d.addProperty("cp", c.getDireccion().getCp());
            o.add("direccion", d);
        }

        JsonArray tels = new JsonArray();
        c.getTelefonos().forEach(tels::add);
        o.add("telefonos", tels);

        o.addProperty("vip", c.isVip());
        return o;
    }

    // ===================== CRUD EN MEMORIA =====================

    public List<Cliente> findAll() {
        return new ArrayList<>(clientes.values());
    }

    public Optional<Cliente> findById(String id) {
        return Optional.ofNullable(clientes.get(id));
    }

    public void save(Cliente c) {
        clientes.put(c.getId(), c);
        maxNumeroLeido = Math.max(maxNumeroLeido, numeroDeId(c.getId()));
    }

    /** Siguiente id libre según el mayor id que aparezca en el fichero. */
    public String siguienteId() {
        return String.format("C%03d", maxNumeroLeido + 1);
    }

    private static int numeroDeId(String id) {
        if (id == null || !id.matches("C\\d+")) {
            return 0;
        }
        return Integer.parseInt(id.substring(1));
    }
}
