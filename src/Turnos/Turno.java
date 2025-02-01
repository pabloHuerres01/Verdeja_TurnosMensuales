package Turnos;

import java.util.ArrayList;
import java.util.List;

import Empleados.Empleado;

public class Turno {
    private String nombre;
    private String horaEntrada;
    private String horaSalida;
    private List<Empleado> empleados;
    private static final int MAX_MANANA = 6;
    private static final int MAX_TARDE = 7;
    private static final int MAX_NOCHE = 2;

    public Turno(String nombre, String horaEntrada, String horaSalida) {
        this.nombre = nombre;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.empleados = new ArrayList<>();
    }

    public boolean agregarEmpleado(Empleado empleado) {
        if ((nombre.equals("Mañana") && empleados.size() < MAX_MANANA) ||
            (nombre.equals("Tarde") && empleados.size() < MAX_TARDE) ||
            (nombre.equals("Noche") && empleados.size() < MAX_NOCHE)) {
            empleados.add(empleado);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return nombre + " (" + horaEntrada + " - " + horaSalida + ") -> " + empleados;
    }
}