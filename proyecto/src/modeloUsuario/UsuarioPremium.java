package modeloUsuario;

import modeloElemento.Elemento;
import java.time.LocalDate;
import java.util.Scanner;

public class UsuarioPremium extends Usuario {

    // Atributos
    private float pagarSuscripcion = 4.99f;
    private boolean compartirElemento = true;
    private LocalDate fechaSuscripcion;
    private LocalDate fechaLimiteSuscripcion;

    // Constructores
    public UsuarioPremium(String nombreCompleto, int edad, String email, String password,
                          int cantidadTareas, int cantidadRecordatorios, float pagarSuscripcion,
                          boolean compartirElemento, LocalDate fechaSuscripcion,
                          LocalDate fechaLimiteSuscripcion) {
        super(nombreCompleto, edad, email, password, cantidadTareas, cantidadRecordatorios);
        this.pagarSuscripcion = pagarSuscripcion;
        this.compartirElemento = compartirElemento;
        this.fechaSuscripcion = fechaSuscripcion;
        this.fechaLimiteSuscripcion = fechaLimiteSuscripcion;
    }

    public UsuarioPremium(float pagarSuscripcion, boolean compartirElemento,
                          LocalDate fechaSuscripcion, LocalDate fechaLimiteSuscripcion) {
        this.pagarSuscripcion = pagarSuscripcion;
        this.compartirElemento = compartirElemento;
        this.fechaSuscripcion = fechaSuscripcion;
        this.fechaLimiteSuscripcion = fechaLimiteSuscripcion;
    }

    public UsuarioPremium() { super(); }

    // Getters y Setters
    public float getPagarSuscripcion() { return pagarSuscripcion; }
    public void setPagarSuscripcion(float pagarSuscripcion) { this.pagarSuscripcion = pagarSuscripcion; }

    public boolean isCompartirElemento() { return compartirElemento; }
    // CORRECCIÓN: setter ahora asigna el valor correctamente
    public void setCompartirElemento(boolean compartirElemento) { this.compartirElemento = compartirElemento; }

    public LocalDate getFechaSuscripcion() { return fechaSuscripcion; }
    public void setFechaSuscripcion(LocalDate fechaSuscripcion) { this.fechaSuscripcion = fechaSuscripcion; }

    public LocalDate getFechaLimiteSuscripcion() { return fechaLimiteSuscripcion; }
    public void setFechaLimiteSuscripcion(LocalDate fechaLimiteSuscripcion) { this.fechaLimiteSuscripcion = fechaLimiteSuscripcion; }

    // Metodos propios
    public float pagarSuscripcion() {
        System.out.println(" ");
        System.out.println("Para mantener los beneficios debe pagar la suscripcion.");
        System.out.println("El precio a pagar es de: " + getPagarSuscripcion()
                + " antes de la fecha limite: " + getFechaLimiteSuscripcion());
        System.out.println("Suscripcion pagada correctamente.");
        System.out.println(" ");
        return pagarSuscripcion;
    }

    // Metodos heredados
    @Override
    public void verificarUsuario() {
        super.verificarUsuario();
    }

    @Override
    public void crearElemento(Elemento elemento) {
        // Usuario Premium sin limite: delega directamente
        super.crearElemento(elemento);
    }

    @Override
    public void imprimirUsuario() {
        super.imprimirUsuario();
        System.out.println("Usted posee un servicio Premium.");
        System.out.println("Suscripcion actual: $" + getPagarSuscripcion());
        System.out.println("Fecha de Suscripcion: " + getFechaSuscripcion());
        System.out.println("Fecha Limite: " + getFechaLimiteSuscripcion());
    }

    @Override
    public void modoSuscripcion() {
        System.out.print("Desea desactivar la suscripcion? (Si/No): ");
        Scanner sc = new Scanner(System.in);
        String respuesta = sc.nextLine();
        if (respuesta.equalsIgnoreCase("Si")) {
            // CORRECCIÓN: en lugar de crear un objeto descartado, se actualiza el estado actual
            setAccesoCompleto(false);
            setCompartirElemento(false);
            System.out.println("\nLa suscripcion ha sido desactivada.");
            System.out.println("Ahora posees un limite en la creacion de Elementos.\n");
        } else {
            System.out.println("\nLa suscripcion sigue activa.");
            System.out.println("No posees limites para la creacion de Tareas, Recordatorios y Compartidos.\n");
        }
    }
}
