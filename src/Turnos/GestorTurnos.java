package Turnos;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Empleados.Empleado;
import Meses.Dia;

public class GestorTurnos {
    public static List<Empleado> generarEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        System.out.println("usuario creado");
        empleados.add(new Empleado("Juan"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("María"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Pedro"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Lucía"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Carlos"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Ana"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("José"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Laura"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Luis"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Carmen"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Miguel"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Isabel"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Fernando"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Beatriz"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Alberto"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Elena"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Sofía"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Andrés"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Raquel"));
        System.out.println("usuario creado");
        empleados.add(new Empleado("Pablo"));

        return empleados;
    }

    public static List<Dia> generarTurnos(List<Empleado> empleados, int turnosTotales) {
        List<Dia> mes = new ArrayList<>();
        int[] diasTrabajados = new int[empleados.size()]; // Controla los días trabajados de cada empleado
        int[] diasDescanso = new int[empleados.size()];  // Controla los días de descanso de cada empleado
        int indiceEmpleado = 0;

        for (int i = 1; i <= turnosTotales; i++) {
            System.out.println("Generando día " + i);
            Dia dia = new Dia(i);

            for (String turno : List.of("Mañana", "Tarde", "Noche")) {
                int trabajadoresNecesarios = turno.equals("Mañana") ? 6 : (turno.equals("Tarde") ? 7 : 2);
                int asignados = 0;
                int intentos = 0; // Para evitar ciclos infinitos si no hay suficientes empleados disponibles

                while (asignados < trabajadoresNecesarios && intentos < empleados.size() * 2) {
                    Empleado empleado = empleados.get(indiceEmpleado);

                    if (diasDescanso[indiceEmpleado] > 0) {
                        // Reducir el contador de días de descanso si el empleado está descansando
                        diasDescanso[indiceEmpleado]--;
                    } else if (diasTrabajados[indiceEmpleado] < 6) {
                        // Asignar al turno si el empleado no supera el límite de días trabajados consecutivos
                        dia.asignarEmpleadoATurno(empleado, turno);
                        diasTrabajados[indiceEmpleado]++;
                        asignados++;
                    } else {
                        // Iniciar el descanso si el empleado ha trabajado 6 días consecutivos
                        diasTrabajados[indiceEmpleado] = 0;
                        diasDescanso[indiceEmpleado] = 2; // 2 días de descanso
                    }

                    // Avanzar al siguiente empleado en la lista
                    indiceEmpleado = (indiceEmpleado + 1) % empleados.size();
                    intentos++;
                }

                // Si no se asignaron suficientes empleados, emitir una advertencia
                if (asignados < trabajadoresNecesarios) {
                    System.out.println("Advertencia: No se pudieron asignar suficientes empleados para el turno " + turno + " en el día " + i);
                }
            }

            mes.add(dia);
        }

        return mes;
    }
   

    public static void mostrarTurnos(List<Dia> mes) {
        for (Dia dia : mes) {
            System.out.println(dia);
        }
    }

    public static void guardarEnCSV(List<Dia> mes) {
        try (FileWriter writer = new FileWriter("turnos.csv")) {
            writer.append("Día,Mañana,Tarde,Noche\n");
            for (Dia dia : mes) {
                writer.append(dia.toString().replaceAll("\\n", "\n"));
            }
            System.out.println("Archivo CSV generado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar CSV: " + e.getMessage());
        }
    }
}