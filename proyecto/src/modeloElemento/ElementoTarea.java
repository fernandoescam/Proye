package modeloElemento;

import catalogo.Estado;
import catalogo.Prioridad;
import modeloUsuario.Usuario;
import java.time.LocalDate;
import java.util.Scanner;

public class ElementoTarea extends Elemento {

    // Atributos
    private Estado estado;

    // Constructor parametrizado
    public ElementoTarea(int id, String titulo, String descripcion, int cantidadColaboradores,
                         Prioridad prioridad, Estado estado, LocalDate fechaCreacion,
                         LocalDate fechaLimite, Usuario usuario) {
        super(id, titulo, descripcion, cantidadColaboradores, prioridad, fechaCreacion, fechaLimite, usuario);
        this.estado = estado;
    }

    public ElementoTarea() {
        super();
        this.estado = Estado.PENDIENTE; // Estado por defecto al crear vacío
    }

    // Getters y Setters
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    // Metodos heredados
    @Override
    public void crearElemento() {
        super.crearElemento();
        // CORRECCIÓN: ahora pide el Estado al usuario en lugar de dejarlo en null
        Scanner sc = new Scanner(System.in);
        int opcion = -1;
        while (opcion < 1 || opcion > 3) {
            System.out.println("Introduzca el Estado (1=PENDIENTE, 2=EN_PROGRESO, 3=CANCELADA): ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
                if (opcion < 1 || opcion > 3) System.out.println("Opcion no valida, intente de nuevo.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese solo un numero.");
            }
        }
        switch (opcion) {
            case 2 -> setEstado(Estado.EN_PROGRESO);
            case 3 -> setEstado(Estado.CANCELADA);
            default -> setEstado(Estado.PENDIENTE);
        }
    }

    @Override
    public void imprimirElementos() {
        super.imprimirElementos();
        System.out.println("Estado: " + estado);
    }
}
