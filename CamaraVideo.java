//Aplica el cargo único de Q75 si la resolución supera los 1080 píxeles 
public class CamaraVideo extends Equipo {
    private int resolucion;

    public CamaraVideo(String codigo, String marca, String modelo, double tarifaDiaria, int resolucion) {
        super(codigo, marca, modelo, tarifaDiaria);
        this.resolucion = resolucion;
    }

    @Override
    public double calcularCosto(int dias) {
        double costo = tarifaDiaria * dias;
        if (resolucion > 1080) {
            costo += 75; // Cargo único por todo el alquiler
        }
        return costo;
    }

    @Override
    public String toString() {
        return "CÁMARA -> " + super.toString() + " | Resolución: " + resolucion + "p";
    }
}

