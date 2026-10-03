//Aplica el recargo de Q50 por día si tiene conectividad inalámbrica
public class Proyector extends Equipo {
    private int luminosidad;
    private boolean inalambrico;

    public Proyector(String codigo, String marca, String modelo, double tarifaDiaria, int luminosidad, boolean inalambrico) {
        super(codigo, marca, modelo, tarifaDiaria);
        this.luminosidad = luminosidad;
        this.inalambrico = inalambrico;
    }

    @Override
    public double calcularCosto(int dias) {
        double costo = tarifaDiaria * dias;
        if (inalambrico) {
            costo += (50 * dias);
        }
        return costo;
    }

    @Override
    public String toString() {
        String wifi = inalambrico ? "Sí" : "No";
        return "PROYECTOR -> " + super.toString() + " | " + luminosidad + " lúmenes | Inalámbrico: " + wifi;
    }
}
