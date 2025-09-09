import java.util.Scanner;

public class Clinica {
    // Declaración de Atributos
    private String[][] medicos;
    private int j; //Contador de indice para cantidad de medicos
    private String[][] pacientes;
    private int k; //Contador de indice para cantidad de pacientes
    private String[][] atenciones;
    private double[] costos;
    private int x;  //Contador de indice para la cantidad de atenciones
    private double totalFacturado;

    // Constructor
    public Clinica() {
        this.medicos = new String[100][9];
        this.pacientes = new String[100][9];
        this.atenciones = new String[100][5];
        this.costos = new double[100];
        this.j = 0;
        this.k = 0;
        this.x = 0;
        this.totalFacturado = 0.0;
    }

    // Método para registrar médicos
    public void registrarMedico() {
        Scanner scanner = new Scanner(System.in);
        if (j >= medicos.length) {
            System.out.println("No se pueden registrar más médicos.");
            return;
        }

        System.out.println("Ingrese la identificación del médico:");
        medicos[j][0] = scanner.nextLine();
        System.out.println("Ingrese el nombre del médico:");
        medicos[j][1] = scanner.nextLine();
        System.out.println("Ingrese el apellido del médico:");
        medicos[j][2] = scanner.nextLine();
        System.out.println("Ingrese la dirección del médico:");
        medicos[j][3] = scanner.nextLine();
        System.out.println("Ingrese el teléfono del médico:");
        medicos[j][4] = scanner.nextLine();
        System.out.println("Ingrese el correo del médico:");
        medicos[j][5] = scanner.nextLine();
        System.out.println("Ingrese la especialidad del médico:");
        medicos[j][6] = scanner.nextLine();

        // Validación de sexo
        String sexo;
        do {
            System.out.println("Ingrese el sexo del médico (M/F):");
            sexo = scanner.nextLine().toUpperCase();
            if (!sexo.equals("M") && !sexo.equals("F")) {
                System.out.println("Sexo inválido. Intente nuevamente.");
            }
        } while (!sexo.equals("M") && !sexo.equals("F"));
        medicos[j][7] = sexo;

        System.out.println("Ingrese la fecha de nacimiento del médico (Día/Mes/Año):");
        medicos[j][8] = scanner.nextLine();

        j = j + 1;
        System.out.println("-----Médico registrado exitosamente.-----");
    }

    // Método para registrar pacientes
    public void registrarPaciente() {
        Scanner scanner = new Scanner(System.in);
        if (k >= pacientes.length) {
            System.out.println("No se pueden registrar más pacientes.");
            return;
        }

        System.out.println("Ingrese la identificación del paciente:");
        pacientes[k][0] = scanner.nextLine();
        System.out.println("Ingrese el nombre del paciente:");
        pacientes[k][1] = scanner.nextLine();
        System.out.println("Ingrese el apellido del paciente:");
        pacientes[k][2] = scanner.nextLine();
        System.out.println("Ingrese la dirección del paciente:");
        pacientes[k][3] = scanner.nextLine();
        System.out.println("Ingrese el teléfono del paciente:");
        pacientes[k][4] = scanner.nextLine();
        System.out.println("Ingrese el correo del paciente:");
        pacientes[k][5] = scanner.nextLine();
        System.out.println("Ingrese las alergias del paciente:");
        pacientes[k][6] = scanner.nextLine();

        // Validación de sexo
        String sexo;
        do {
            System.out.println("Ingrese el sexo del paciente (M/F):");
            sexo = scanner.nextLine().toUpperCase();
            if (!sexo.equals("M") && !sexo.equals("F")) {
                System.out.println("Sexo inválido. Intente nuevamente.");
            }
        } while (!sexo.equals("M") && !sexo.equals("F"));
        pacientes[k][7] = sexo;

        System.out.println("Ingrese la fecha de nacimiento del paciente (Día/Mes/Año):");
        pacientes[k][8] = scanner.nextLine();

        k = k + 1;
        System.out.println("-----Paciente registrado exitosamente.------");
    }

    // Método para registrar atención
    public void registrarAtencion() {
        Scanner scanner = new Scanner(System.in);
        if (x >= atenciones.length) {
            System.out.println("No se pueden registrar más atenciones.");
            return;
        }

        // Pedir fecha de ingreso
        System.out.println("Ingrese la fecha de consulta (Día/Mes/Año):");
        atenciones[x][0] = scanner.nextLine();

        // Listar y seleccionar paciente
        System.out.println("Seleccione el paciente (ID y nombre):");
        for (int i = 0; i < k; i++) {
            System.out.println((i + 1) + ". " + pacientes[i][0] + " - " + pacientes[i][1] + " " + pacientes[i][2]);
        }
        int pacienteIndex = scanner.nextInt() - 1;
        atenciones[x][4] = pacientes[pacienteIndex][1] + " " + pacientes[pacienteIndex][2]; // Nombre y apellido
        atenciones[x][3] = pacientes[pacienteIndex][0]; // ID del paciente
        scanner.nextLine(); // Limpiar buffer

        // Seleccionar motivo
        int motivo;
        do {
            System.out.println("Seleccione el motivo de la atención:");
            System.out.println("1. Consulta General");
            System.out.println("2. Inyectables");
            System.out.println("3. Laboratorios");
            motivo = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (motivo) {
                case 1:
                    atenciones[x][1] = "Consulta General";
                    break;
                case 2:
                    atenciones[x][1] = "Inyectables";
                    break;
                case 3:
                    atenciones[x][1] = "Laboratorios";
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (motivo < 1 || motivo > 3);

        // Listar y seleccionar médico
        System.out.println("Seleccione el médico que atendió (ID y nombre):");
        for (int i = 0; i < j; i++) {
            System.out.println((i + 1) + ". " + medicos[i][0] + " - " + medicos[i][1] + " " + medicos[i][2]);
        }
        int medicoIndex = scanner.nextInt() - 1;
        atenciones[x][2] = medicos[medicoIndex][0]; // ID del médico

        // Registrar el costo del servicio
        System.out.println("Ingrese el costo del servicio:");
        costos[x] = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer

        totalFacturado += costos[x];
        x = x + 1;
        System.out.println("-----Atención registrada exitosamente.-----");
    }

    // Método para mostrar detalles de atenciones
    public void detalleAtencionesPorDia() {
        System.out.println("Detalle de los pacientes atendidos en el día:");
        for (int i = 0; i < x; i++) {
            System.out.println("Nombre del paciente: " + atenciones[i][4] +
                    ", Paciente ID: " + atenciones[i][3] +
                    ", Tipo de Servicio: " + atenciones[i][1] +
                    ", Médico ID: " + atenciones[i][2] +
                    ", Fecha de Ingreso: " + atenciones[i][0]);
        }
    }

    public void detalleAtencionesPorServicio() {
        Scanner scanner = new Scanner(System.in);
        int motivo;
    
        do {
            System.out.println("Seleccione el motivo de la atención:");
            System.out.println("1. Consulta General");
            System.out.println("2. Inyectables");
            System.out.println("3. Laboratorios");
            motivo = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
    
            String servicio = "";
            switch (motivo) {
                case 1:
                    servicio = "Consulta General";
                    break;
                case 2:
                    servicio = "Inyectables";
                    break;
                case 3:
                    servicio = "Laboratorios";
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
    
            if (!servicio.equals("")) {
                System.out.println("Atenciones para el servicio: " + servicio);
                for (int i = 0; i < x; i++) {
                    if (atenciones[i][1].equalsIgnoreCase(servicio)) {
                        // Buscar los detalles completos del paciente
                        for (int j = 0; j < k; j++) {
                            if (pacientes[j][0].equals(atenciones[i][3])) {
                                System.out.println("Fecha: " + atenciones[i][0] +
                                        ", Médico ID: " + atenciones[i][2] +
                                        ", Paciente ID: " + pacientes[j][0] +
                                        ", Nombre: " + pacientes[j][1] +
                                        ", Apellido: " + pacientes[j][2] +
                                        ", Dirección: " + pacientes[j][3] +
                                        ", Teléfono: " + pacientes[j][4] +
                                        ", Correo: " + pacientes[j][5] +
                                        ", Alergias: " + pacientes[j][6] +
                                        ", Sexo: " + pacientes[j][7] +
                                        ", Fecha de Nacimiento: " + pacientes[j][8]);
                            }
                        }
                    }
                }
            }
        } while (motivo < 1 || motivo > 3);
    }

    public void detalleAtencionesPorMedico(String medicoId) {
        System.out.println("Atenciones realizadas por el médico ID: " + medicoId);
        for (int i = 0; i < x; i++) {
            if (atenciones[i][2].equalsIgnoreCase(medicoId)) {
                // Buscar los detalles completos del paciente
                for (int j = 0; j < k; j++) {
                    if (pacientes[j][0].equals(atenciones[i][3])) {
                        System.out.println("Fecha: " + atenciones[i][0] +
                                ", Servicio: " + atenciones[i][1] +
                                ", Paciente ID: " + pacientes[j][0] +
                                ", Nombre: " + pacientes[j][1] +
                                ", Apellido: " + pacientes[j][2] +
                                ", Dirección: " + pacientes[j][3] +
                                ", Teléfono: " + pacientes[j][4] +
                                ", Correo: " + pacientes[j][5] +
                                ", Alergias: " + pacientes[j][6] +
                                ", Sexo: " + pacientes[j][7] +
                                ", Fecha de Nacimiento: " + pacientes[j][8]);
                    }
                }
            }
        }
    }

    // Método para calcular el total cobrado por cada tipo de servicio
    public void totalCobradoPorTipo() {
        double consultaGeneral = 0;
        double inyectables = 0;
        double laboratorios = 0;

        for (int i = 0; i < x; i++) {
            if (atenciones[i][1].equalsIgnoreCase("Consulta General")) {
                consultaGeneral += costos[i];
            } else if (atenciones[i][1].equalsIgnoreCase("Inyectables")) {
                inyectables += costos[i];
            } else if (atenciones[i][1].equalsIgnoreCase("Laboratorios")) {
                laboratorios += costos[i];
            }
        }

        System.out.printf("Total cobrado por Consulta General: $%.2f%n", consultaGeneral);
        System.out.printf("Total cobrado por Inyectables: $%.2f%n", inyectables);
        System.out.printf("Total cobrado por Laboratorios: $%.2f%n", laboratorios);
    }

    // Método para mostrar la distribución porcentual de ingresos por servicio
    public void distribucionPorcentualServicios() {
        double[] ingresosPorServicio = new double[3]; // [0] -> General, [1] -> Inyectables, [2] -> Laboratorios

        for (int i = 0; i < x; i++) {
            if (atenciones[i][1].equalsIgnoreCase("Consulta General")) {
                ingresosPorServicio[0] += costos[i];
            } else if (atenciones[i][1].equalsIgnoreCase("Inyectables")) {
                ingresosPorServicio[1] += costos[i];
            } else if (atenciones[i][1].equalsIgnoreCase("Laboratorios")) {
                ingresosPorServicio[2] += costos[i];
            }
        }

        System.out.println("Distribución porcentual de ingresos por servicio:");
        System.out.printf("Consulta General: %.2f%%%n", (ingresosPorServicio[0] / totalFacturado) * 100);
        System.out.printf("Inyectables: %.2f%%%n", (ingresosPorServicio[1] / totalFacturado) * 100);
        System.out.printf("Laboratorios: %.2f%%%n", (ingresosPorServicio[2] / totalFacturado) * 100);
    }

    // Método main para ejecutar el programa
    public static void main(String[] args) {
        Clinica clinica = new Clinica();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Menú de Opciones:");
            System.out.println("1. Registrar Médico");
            System.out.println("2. Registrar Paciente");
            System.out.println("3. Registrar Atención");
            System.out.println("4. Detalle de los pacientes que se atendieron en el día por tipo de servicio");
            System.out.println("5. Mostrar detalle de atenciones por servicio");
            System.out.println("6. Mostrar detalle de atenciones por médico");
            System.out.println("7. Mostrar distribución porcentual de ingresos por servicio");
            System.out.println("8. Mostrar total cobrado por tipo de servicio");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    clinica.registrarMedico();
                    break;
                case 2:
                    clinica.registrarPaciente();
                    break;
                case 3:
                    clinica.registrarAtencion();
                    break;
                case 4:
                    clinica.detalleAtencionesPorDia();
                    break;
                case 5:
                    clinica.detalleAtencionesPorServicio();
                    break;
                case 6:
                    System.out.println("Ingrese el ID del médico:");
                    String medicoId = scanner.nextLine();
                    clinica.detalleAtencionesPorMedico(medicoId);
                    break;
                case 7:
                    clinica.distribucionPorcentualServicios();
                    break;
                case 8:
                    clinica.totalCobradoPorTipo();
                    break;
                case 9:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 9);
    }
}
