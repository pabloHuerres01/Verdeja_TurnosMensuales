package Meses;

import Empleados.Empleado;
import Turnos.Turno;

public class Dia {
    private int id;
    private Turno manana;
    private Turno tarde;
    private Turno noche;

    public Dia(int id) {
        this.id = id;
        this.manana = new Turno("Mañana", "06:00", "14:00");
        this.tarde = new Turno("Tarde", "14:00", "22:00");
        this.noche = new Turno("Noche", "22:00", "06:00");
    }

    public boolean asignarEmpleadoATurno(Empleado empleado, String turno) {
        return switch (turno) {
            case "Mañana" -> manana.agregarEmpleado(empleado);
            case "Tarde" -> tarde.agregarEmpleado(empleado);
            case "Noche" -> noche.agregarEmpleado(empleado);
            default -> false;
        };
    }

    @Override
    public String toString() {
        return "Día " + id + "\n" + manana + "\n" + tarde + "\n" + noche + "\n";
    }
}