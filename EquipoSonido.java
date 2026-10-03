//Aplica el cobro adicional de Q100 por cada kilovatio de potencia por cada día de alquiler
public class EquipoSonido extends Equipo {
    private double potencia;

    public EquipoSonido(String codigo, String marca, String modelo, double tarifaDiaria, double potencia) {
        super(codigo, marca, modelo, tarifaDiaria);
        this.potencia = potencia;
    }

    @Override
    public double calcularCosto(int dias) {
        double costo = tarifaDiaria * dias;
        double recargo = 100 * potencia * dias;
        return costo + recargo;
    }

    @Override
    public String toString() {
        return "SONIDO -> " + super.toString() + " | Potencia: " + potencia + " kW";
    }
}
