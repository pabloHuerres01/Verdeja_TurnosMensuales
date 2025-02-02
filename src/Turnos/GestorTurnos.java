package Turnos;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
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
    
    /**
     * Metodo base para generar los turnos 
     * @param empleados
     * @param turnosTotales
     * @return
     */
    public static List<Dia> generarTurnos(List<Empleado> empleados, int turnosTotales) {
        List<Dia> mes = new ArrayList<>();
        int[] diasTrabajados = new int[empleados.size()];
        int[] diasDescanso = new int[empleados.size()];
        int[] ultimaNoche = new int[empleados.size()]; // Almacena el último día trabajado en turno noche
        int indiceEmpleado = 0;

        // Inicializar el encabezado de la matriz
        StringBuilder matriz = new StringBuilder();
        matriz.append("Nombre\t");
        for (int i = 1; i <= turnosTotales; i++) {
            matriz.append("DIA" + i + "\t");
        }
        matriz.append("\n");

        // Inicializar filas con los nombres de los empleados
        String[][] turnosMatrix = new String[empleados.size()][turnosTotales + 1];
        for (int i = 0; i < empleados.size(); i++) {
            turnosMatrix[i][0] = empleados.get(i).getNombre();
            for (int j = 1; j <= turnosTotales; j++) {
                turnosMatrix[i][j] = ""; // Espacio vacío para los turnos
            }
        }

        for (int i = 1; i <= turnosTotales; i++) {
            System.out.println("Generando día " + i);
            Dia dia = new Dia(i);

            for (String turno : List.of("Mañana", "Tarde", "Noche")) {
                int trabajadoresNecesarios = turno.equals("Mañana") ? 6 : (turno.equals("Tarde") ? 7 : 2);
                int asignados = 0;
                int intentos = 0;

                while (asignados < trabajadoresNecesarios && intentos < empleados.size() * 2) {
                    Empleado empleado = empleados.get(indiceEmpleado);

                    if (diasDescanso[indiceEmpleado] > 0) {
                        diasDescanso[indiceEmpleado]--;
                    } else {
                        boolean puedeTrabajarNoche = turno.equals("Noche") ? (ultimaNoche[indiceEmpleado] == 0 || (i - ultimaNoche[indiceEmpleado] > 5)) : true;

                        if (!puedeTrabajarNoche) {
                            intentos++;
                            indiceEmpleado = (indiceEmpleado + 1) % empleados.size();
                            continue;
                        }

                        if (diasTrabajados[indiceEmpleado] >= 6 || (diasTrabajados[indiceEmpleado] >= 3 && Math.random() > 0.7)) {
                            diasTrabajados[indiceEmpleado] = 0;
                            diasDescanso[indiceEmpleado] = 2;
                        } else {
                            dia.asignarEmpleadoATurno(empleado, turno);
                            diasTrabajados[indiceEmpleado]++;
                            asignados++;

                            if (turno.equals("Noche")) {
                                ultimaNoche[indiceEmpleado] = i;
                            }

                            // Actualizar la matriz
                            int rowIndex = empleados.indexOf(empleado);
                            String turnoInicial = turno.substring(0, 1); // M, T o N
                            turnosMatrix[rowIndex][i] = turnoInicial;
                        }
                    }

                    indiceEmpleado = (indiceEmpleado + 1) % empleados.size();
                    intentos++;
                }

                if (asignados < trabajadoresNecesarios) {
                    System.out.println("Advertencia: No se pudieron asignar suficientes empleados para el turno " + turno + " en el día " + i);
                }
            }

            mes.add(dia);
        }

        // Exportar la matriz a un archivo de texto
        try (FileWriter writer = new FileWriter("turnos.txt")) {
            // Escribir encabezados
            writer.write("Nombre\t");
            for (int i = 1; i <= turnosTotales; i++) {
                writer.write("DIA" + i + "\t");
            }
            writer.write("\n");

            // Escribir filas con los datos
            for (String[] row : turnosMatrix) {
                for (String cell : row) {
                    writer.write((cell == null ? "" : cell) + "\t");
                }
                writer.write("\n");
            }

            System.out.println("Archivo de texto generado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mes;
    }
}