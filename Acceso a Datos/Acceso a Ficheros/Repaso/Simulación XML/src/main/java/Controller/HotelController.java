package Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Entity.Cliente;
import Entity.Reserva;
import Entity.TipoHabitacion;
import Service.ClienteService;

public class HotelController {
	
	public static final String RUTA = "src/main/resources/clientes_hotel.xml";
	public static final ClienteService service = new ClienteService(RUTA);

	public static void main(String[] args) {
		
		Cliente c1 = new Cliente("Ana López García", 30, "ana@correo.com");
		Cliente c2 = new Cliente("Luis Martín Pérez", 45, "luis@correo.com");
		Cliente c3 = new Cliente("Marta Ruiz Gómez", 28, "marta@correo.com");
       
        Reserva r1 = new Reserva(TipoHabitacion.DOBLE, LocalDate.of(2025, 5, 1), 3, 90);
        Reserva r2 = new Reserva(TipoHabitacion.FAMILIAR, LocalDate.of(2025, 6, 1), 3, 90);
        Reserva r3 = new Reserva(TipoHabitacion.DOBLE, LocalDate.of(2025, 5, 30), 3, 90);
        
        List<Reserva> rs1 = new ArrayList<Reserva>();
        rs1.add(r1);
        rs1.add(r2);
        
        List<Reserva> rs2 = new ArrayList<Reserva>();
        rs2.add(r3);

        service.addCliente(c1, rs1);
        service.addCliente(c2, rs2);
        service.addCliente(c3, null);
        
        service.ordenarPorReservasYNombre().forEach(System.out::println);
        //System.out.println(service.importeTotal(c3));
        //System.out.println("CLIENTE MAYOR GASTO " + service.clienteMayorGasto());
        //System.out.println(service.clientesConEntradaEnMes(5, 2025).size());
        
        Map<TipoHabitacion, Long> listado = service.contarReservasPorTipo();
        
        for (Map.Entry<TipoHabitacion, Long> entrada : listado.entrySet()) {
		    System.out.println(entrada.getKey() + " -> " + entrada.getValue());
		}
	}
}
