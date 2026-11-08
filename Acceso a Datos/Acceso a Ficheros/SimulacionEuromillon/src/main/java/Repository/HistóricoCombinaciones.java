package Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import Entity.Combinaciones;

public class HistóricoCombinaciones implements ICombinacionRepository{
	
	private List<Combinaciones> listadoCombinaciones;
	private File fichero;
	
	public HistóricoCombinaciones(String ruta) {
		if(ruta == null || ruta.isEmpty()){
	           throw  new IllegalArgumentException("La ruta es nula o esta vacía");
	        }
		this.fichero = new File(ruta);
		this.listadoCombinaciones = new ArrayList<>();
	}

	@Override
	public void leerCSV() {
		this.listadoCombinaciones.clear();
		Scanner in = null;
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d/MM/yyyy");
		
		try {
			FileReader ficheroLectura = new FileReader(this.fichero);
			in = new Scanner(ficheroLectura);
			in.nextLine();
			
			while(in.hasNextLine()) {
				String valor = in.nextLine();
				if (valor.isBlank()) continue;
				String[] estrellas = valor.split(",,");
				String[] valores = estrellas[0].split(",");
				
				String combGan = "";
				for(int i=1; i<valores.length; i++) {
					if(i == valores.length - 1) {
						combGan += valores[i];
					}else {
						combGan += valores[i] + ",";
					}
				}
				
				List<Integer> listaEstrellas = new ArrayList<Integer>();
				for(String e: estrellas[1].split(",")) {
					listaEstrellas.add(Integer.parseInt(e));
				}
				
				LocalDate fecha = LocalDate.parse(valores[0].trim(), fmt);
				Combinaciones com = new Combinaciones(fecha, combGan, listaEstrellas);
				
				this.listadoCombinaciones.add(com);
			}
		} catch (FileNotFoundException e) {
			System.err.println("No se encuentra el archivo o fichero: " + e);
		} catch (Exception e) {
			System.err.println("No se puede añadir una combinacion sin los campos" + e);
		}
		
	}
	
	@Override
	public List<Combinaciones> listar() {
		return this.listadoCombinaciones;
	}


	@Override
	public String calcularNumeroEstrellas() {

	    Map<Integer, Integer> conteoNumeros = new HashMap<>();
	    Map<Integer, Integer> conteoEstrellas = new HashMap<>();

	    for (Combinaciones c : this.listadoCombinaciones) {
	        for (String n : c.getCombinacionGanadora().trim().split("\\D+")) {
	            if (!n.isEmpty()) {
	                int num = Integer.parseInt(n);
	                conteoNumeros.put(num, conteoNumeros.getOrDefault(num, 0) + 1);
	            }
	        }

	        for (Integer e : c.getEstrellas()) {
	            conteoEstrellas.put(e, conteoEstrellas.getOrDefault(e, 0) + 1);
	        }
	    }

	    Map.Entry<Integer, Integer> numMax = maximo(conteoNumeros);
	    Map.Entry<Integer, Integer> estMax = maximo(conteoEstrellas);

	    return "Número más repetido: " + numMax.getKey() + " (" + numMax.getValue() + " veces)\n"
	         + "Estrella más repetida: " + estMax.getKey() + " (" + estMax.getValue() + " veces)";
	}
	
	@Override
	public String numeroCombinaciones() {

	    Map<String, Integer> conteo = contarCombinaciones();

	    StringBuilder sb = new StringBuilder("Veces que ha salido cada combinación:\n");
	    for (Map.Entry<String, Integer> e : conteo.entrySet()) {
	        sb.append("[-] ").append(e.getKey())
	          .append(": ").append(e.getValue()).append(" veces\n");
	    }
	    return sb.toString();
	}
	@Override
	public String combinacionFrecuente() {

	    Map.Entry<String, Integer> max = maximo(contarCombinaciones());

	    return "Combinación más frecuente: " + max.getKey() + " (" + max.getValue() + " veces)";
	}
	
	private Map<String, Integer> contarCombinaciones() {
	    Map<String, Integer> conteo = new HashMap<>();
	    for (Combinaciones c : this.listadoCombinaciones) {
	        String comb = c.getCombinacionGanadora().trim();
	        conteo.put(comb, conteo.getOrDefault(comb, 0) + 1);
	    }
	    return conteo;
	}

	private <K> Map.Entry<K, Integer> maximo(Map<K, Integer> mapa) {
	    Map.Entry<K, Integer> max = null;
	    for (Map.Entry<K, Integer> e : mapa.entrySet()) {
	        if (max == null || e.getValue() > max.getValue()) {
	            max = e;
	        }
	    }
	    return max;
	}
}
