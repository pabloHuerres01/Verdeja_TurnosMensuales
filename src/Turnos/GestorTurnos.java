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
        int indiceEmpleado = 0;

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("CONSULTOR");
        for (int i = 1; i <= turnosTotales; i++) {
            csvBuilder.append("," + String.format("%02d-%02d", i, 2));
        }
        csvBuilder.append("\n");

        for (Empleado empleado : empleados) {
            csvBuilder.append(empleado.getNombre());
            for (int i = 1; i <= turnosTotales; i++) {
                csvBuilder.append(",");
            }
            csvBuilder.append("\n");
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
                        if (diasTrabajados[indiceEmpleado] >= 6 || (diasTrabajados[indiceEmpleado] >= 3 && Math.random() > 0.7)) {
                            diasTrabajados[indiceEmpleado] = 0;
                            diasDescanso[indiceEmpleado] = 2;
                        } else {
                            dia.asignarEmpleadoATurno(empleado, turno);
                            diasTrabajados[indiceEmpleado]++;
                            asignados++;

                            int rowIndex = empleados.indexOf(empleado);
                            int columnIndex = i; // Índice de columna para este día

                            String[] rows = csvBuilder.toString().split("\n");
                            String[] columns = rows[rowIndex + 1].split(",");

                            if (columnIndex < columns.length) {
                                String turnoInicial = turno.substring(0, 1); // M, T o N
                                columns[columnIndex] = turnoInicial;
                                rows[rowIndex + 1] = String.join(",", columns);
                                csvBuilder = new StringBuilder(String.join("\n", rows));
                            }
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

        try (FileWriter writer = new FileWriter("turnos.csv")) {
            writer.write(csvBuilder.toString());
            System.out.println("Archivo CSV generado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mes;
    }
}