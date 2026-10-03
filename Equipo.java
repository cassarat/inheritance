// en esta clase define los atributos comunes y el método abstracto para aplicar polimorfismo
public abstract class Equipo {
    protected String codigo;
    protected String marca;
    protected String modelo;
    protected double tarifaDiaria;
    protected boolean disponible;

    public Equipo(String codigo, String marca, String modelo, double tarifaDiaria) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.tarifaDiaria = tarifaDiaria;
        this.disponible = true; // Todo equipo recien ingresado queda disponible
    }

    // metood abstracto que cada categoria tiene que override 
    public abstract double calcularCosto(int dias);

    public String getCodigo() {
        return codigo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        String estado = disponible ? "Disponible" : "Alquilado";
        return String.format("[%s] %s %s | Tarifa diaria: Q%.2f | Estado: %s", 
                codigo, marca, modelo, tarifaDiaria, estado);
    }
}
