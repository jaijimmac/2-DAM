package Controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Entity.Criatura;
import Entity.Habitat;
import Entity.TipoCriatura;
import Service.CriaturaService;

public class CriaturaController {

	private static final Logger logger = LogManager.getLogger(CriaturaController.class);
	private static final String RUTA = "src/main/resources/criaturasfantasticas.csv";

	private final CriaturaService service = new CriaturaService(RUTA);

	public static void main(String[] args) {
		CriaturaController con = new CriaturaController();

		con.probarFindAll();
		con.probarAddCriatura();
		con.probarFindById();
		con.probarObtenerCriaturaDeTipo();
		con.probarExportarJSON();
	}

	
	private void probarFindAll() {
		titulo("findAll");

		Collection<Criatura> listado = service.findAll();
		logger.info("Número de criaturas cargadas: " + listado.size());
		//listado.forEach(System.out::println);
	}

	private void probarAddCriatura() {
		titulo("addCriatura");

		Habitat habitat = new Habitat("Moderado", "prueba");
		Criatura nueva = new Criatura("prueba", TipoCriatura.BESTIA, true, 89, 89, habitat);

		Criatura anadida = service.addCriatura(nueva);
		logger.info("Añadida: " + anadida);

		Criatura duplicada = service.addCriatura(nueva);
		logger.info("Duplicada (esperado null): " + duplicada);

		logger.info("Nula (esperado null): " + service.addCriatura(null));

		Criatura sinNombre = new Criatura("  ", TipoCriatura.SER, false, 10, 10, habitat);
		logger.info("Sin nombre (esperado null): " + service.addCriatura(sinNombre));

		logger.info("Total tras añadir: " + service.findAll().size());
	}

	private void probarFindById() {
		titulo("findById");

		mostrar("Dementor", service.findById("Dementor"));

		mostrar("PRUEBA", service.findById("PRUEBA"));

		mostrar("NoExiste", service.findById("NoExiste"));

		mostrar("null", service.findById(null));
		mostrar("\" \"", service.findById(" "));
	}

	private void probarObtenerCriaturaDeTipo() {
		titulo("obtenerCriaturaDeTipo");

		for (TipoCriatura tipo : TipoCriatura.values()) {
			List<Criatura> deTipo = service.obtenerCriaturaDeTipo(tipo);
			logger.info(tipo + ": " + deTipo.size() + " criaturas");
		}

		logger.info("Tipo null (esperado 0): " + service.obtenerCriaturaDeTipo(null).size());
	}


	private void probarExportarJSON() {
		titulo("exportarJSON");

		for (TipoCriatura tipo : TipoCriatura.values()) {
			service.exportarJSON(service.obtenerCriaturaDeTipo(tipo), tipo.name());
			logger.info("Exportado " + tipo.name() + ".json");
		}

		service.exportarJSON(List.of(), "VACIO");
		service.exportarJSON(null, "NULO");
		service.exportarJSON(service.findAll(), " ");
		service.exportarJSON(service.findAll(), null);
	}

	
	private static void titulo(String metodo) {
		logger.info("========== Probando " + metodo + " ==========");
	}

	private static void mostrar(String buscado, Optional<Criatura> resultado) {
		logger.info("Buscar " + buscado + " -> "
				+ resultado.map(Criatura::toString).orElse("no encontrada"));
	}
}