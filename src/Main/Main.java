package Main;

import java.util.List;
import java.util.Scanner;

import Empleados.Empleado;
import Meses.Dia;
import Turnos.GestorTurnos;
import Turnos.Turno;

public class Main {
	public Scanner t = new Scanner (System.in);
	
	public Main ()
	{
		System.out.print("Ingrese el número de días del mes: ");
        int diasDelMes = t.nextInt();

		GestorTurnos gestor = new GestorTurnos();
		List<Empleado> empleados = gestor.generarEmpleados(); // Generar empleados con nombres predefinidos
        List<Dia> mes = gestor.generarTurnos(empleados, diasDelMes); // Asignar turnos para el mes
        gestor.mostrarTurnos(mes); // Mostrar turnos por pantalla
        gestor.guardarEnCSV(mes); // Guardar turnos en un archivo CSV
	    
	}
	
	
	

	public static void main(String[] args) {
		Main main = new Main();

	}
	
	

}
