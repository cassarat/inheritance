//Main - el menu y las validaciones
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnEscena {
    private static List<Equipo> inventario = new ArrayList<>();
    private static double ingresosTotales = 0.0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarDatosPrueba();
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== SISTEMA DE ALQUILER EN ESCENA ===");
            System.out.println("1. Registrar nuevo equipo");
            System.out.println("2. Consultar inventario (Reporte general)");
            System.out.println("3. Cotizar alquiler");
            System.out.println("4. Confirmar alquiler");
            System.out.println("5. Registrar devolución");
            System.out.println("6. Salir");
            
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1: registrarEquipo(); break;
                case 2: generarReporte(); break;
                case 3: cotizarAlquiler(); break;
                case 4: confirmarAlquiler(); break;
                case 5: registrarDevolucion(); break;
                case 6: salir = true; System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }

    private static void inicializarDatosPrueba() {
        inventario.add(new Proyector("P01", "Epson", "X100", 150.0, 3000, false));
        inventario.add(new Proyector("P02", "Sony", "WIFI200", 200.0, 4000, true));
        inventario.add(new CamaraVideo("C01", "Canon", "EOS R", 250.0, 1080));
        inventario.add(new CamaraVideo("C02", "Sony", "Alpha 7", 300.0, 2160));
        inventario.add(new EquipoSonido("S01", "JBL", "PartyBox", 100.0, 0.8));
        inventario.add(new EquipoSonido("S02", "Bose", "S1 Pro", 200.0, 1.5));
    }

    private static void registrarEquipo() {
        System.out.println("\n--- REGISTRAR EQUIPO ---");
        System.out.println("1. Proyector | 2. Cámara de Video | 3. Equipo de Sonido");
        int tipo = leerEntero("Seleccione el tipo: ");
        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo inválido.");
            return;
        }

        System.out.print("Ingrese código de inventario: ");
        String codigo = scanner.nextLine().trim();
        if (codigo.isEmpty() || buscarEquipo(codigo) != null) {
            System.out.println("Error: El código no puede estar vacío ni repetido.");
            return;
        }

        System.out.print("Ingrese marca: ");
        String marca = scanner.nextLine().trim();
        System.out.print("Ingrese modelo: ");
        String modelo = scanner.nextLine().trim();
        
        double tarifa = leerDouble("Ingrese tarifa diaria (Q): ");
        if (tarifa <= 0) {
            System.out.println("Error: La tarifa debe ser mayor a cero.");
            return;
        }

        switch (tipo) {
            case 1:
                int lum = leerEntero("Luminosidad (lúmenes): ");
                if (lum <= 0) { System.out.println("Error: Debe ser mayor a cero."); return; }
                System.out.print("¿Es inalámbrico? (si/no): ");
                boolean inalambrico = scanner.nextLine().trim().equalsIgnoreCase("si");
                inventario.add(new Proyector(codigo, marca, modelo, tarifa, lum, inalambrico));
                break;
            case 2:
                int res = leerEntero("Resolución (píxeles verticales): ");
                if (res <= 0) { System.out.println("Error: Debe ser mayor a cero."); return; }
                inventario.add(new CamaraVideo(codigo, marca, modelo, tarifa, res));
                break;
            case 3:
                double pot = leerDouble("Potencia (kW): ");
                if (pot <= 0) { System.out.println("Error: Debe ser mayor a cero."); return; }
                inventario.add(new EquipoSonido(codigo, marca, modelo, tarifa, pot));
                break;
        }
        System.out.println("Equipo registrado con éxito.");
    }

    private static void cotizarAlquiler() {
        System.out.println("\n--- COTIZAR ALQUILER ---");
        System.out.print("Ingrese código del equipo: ");
        String codigo = scanner.nextLine().trim();
        Equipo eq = buscarEquipo(codigo);
        
        if (eq == null) {
            System.out.println("Error: Equipo no encontrado.");
            return;
        }

        int dias = leerEntero("Ingrese cantidad de días: ");
        if (dias <= 0) {
            System.out.println("Error: Los días deben ser mayores a cero.");
            return;
        }

        System.out.println("\nDetalles de cotización:");
        System.out.println(eq.toString());
        System.out.printf("Costo total por %d días: Q%.2f\n", dias, eq.calcularCosto(dias));
    }

    private static void confirmarAlquiler() {
        System.out.println("\n--- CONFIRMAR ALQUILER ---");
        System.out.print("Ingrese código del equipo: ");
        String codigo = scanner.nextLine().trim();
        Equipo eq = buscarEquipo(codigo);

        if (eq == null) {
            System.out.println("Error: Equipo no encontrado.");
            return;
        }
        if (!eq.isDisponible()) {
            System.out.println("Error: El equipo está actualmente alquilado.");
            return;
        }

        int dias = leerEntero("Ingrese cantidad de días: ");
        if (dias <= 0) {
            System.out.println("Error: Los días deben ser mayores a cero.");
            return;
        }

        double total = eq.calcularCosto(dias);
        System.out.println(eq.toString());
        System.out.printf("Total a cobrar: Q%.2f\n", total);
        System.out.print("¿Confirmar alquiler? (si/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("si")) {
            eq.setDisponible(false);
            ingresosTotales += total;
            System.out.println("Alquiler confirmado. Equipo marcado como ocupado.");
        } else {
            System.out.println("Alquiler cancelado. Ningún cambio realizado.");
        }
    }

    private static void registrarDevolucion() {
        System.out.println("\n--- REGISTRAR DEVOLUCIÓN ---");
        System.out.print("Ingrese código del equipo: ");
        String codigo = scanner.nextLine().trim();
        Equipo eq = buscarEquipo(codigo);

        if (eq == null) {
            System.out.println("Error: Equipo no encontrado.");
            return;
        }
        if (eq.isDisponible()) {
            System.out.println("Error: Este equipo ya se encuentra disponible en inventario.");
            return;
        }

        eq.setDisponible(true);
        System.out.println("Devolución registrada con éxito. Equipo disponible.");
    }

    private static void generarReporte() {
        System.out.println("\n--- REPORTE GENERAL DE INVENTARIO ---");
        int projTotal = 0, projDisp = 0, camTotal = 0, camDisp = 0, sonTotal = 0, sonDisp = 0;

        for (Equipo eq : inventario) {
            System.out.println(eq.toString());
            if (eq instanceof Proyector) {
                projTotal++;
                if (eq.isDisponible()) projDisp++;
            } else if (eq instanceof CamaraVideo) {
                camTotal++;
                if (eq.isDisponible()) camDisp++;
            } else if (eq instanceof EquipoSonido) {
                sonTotal++;
                if (eq.isDisponible()) sonDisp++;
            }
        }

        System.out.println("\nResumen por categoría:");
        System.out.printf("Proyectores: %d total, %d disponibles, %d alquilados\n", projTotal, projDisp, projTotal - projDisp);
        System.out.printf("Cámaras: %d total, %d disponibles, %d alquiladas\n", camTotal, camDisp, camTotal - camDisp);
        System.out.printf("Sonido: %d total, %d disponibles, %d alquilados\n", sonTotal, sonDisp, sonTotal - sonDisp);
        System.out.printf("\nINGRESOS ACUMULADOS: Q%.2f\n", ingresosTotales);
    }

    // Métodos de ayuda
    private static Equipo buscarEquipo(String codigo) {
        for (Equipo eq : inventario) {
            if (eq.getCodigo().equalsIgnoreCase(codigo)) {
                return eq;
            }
        }
        return null;
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número decimal válido.");
            }
        }
    }
}
