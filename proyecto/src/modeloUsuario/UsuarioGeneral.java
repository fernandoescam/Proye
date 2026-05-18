package modeloUsuario;

import modeloElemento.Elemento;
import java.util.Scanner;

public class UsuarioGeneral extends Usuario {

    // Atributos
    private boolean activarSuscripcion = false;
    final private int limiteElementosTareas = 8;
    final private int limiteElementosRecordatorios = 4;
    private int contadorElementoTareas = 0;
    private int contadorElementoRecordatorios = 0;

    // Constructores
    public UsuarioGeneral(String nombreCompleto, int edad, String email, String password,
                          int cantidadTareas, int cantidadRecordatorios,
                          boolean activarSuscripcion, int contadorElementoTareas,
                          int contadorElementoRecordatorios) {
        super(nombreCompleto, edad, email, password, cantidadTareas, cantidadRecordatorios);
        this.activarSuscripcion = activarSuscripcion;
        this.contadorElementoTareas = contadorElementoTareas;
        this.contadorElementoRecordatorios = contadorElementoRecordatorios;
    }

    public UsuarioGeneral(boolean activarSuscripcion, int contadorElementoTareas,
                          int contadorElementoRecordatorios) {
        this.activarSuscripcion = activarSuscripcion;
        this.contadorElementoTareas = contadorElementoTareas;
        this.contadorElementoRecordatorios = contadorElementoRecordatorios;
    }

    public UsuarioGeneral() { super(); }

    // Getters y Setters
    public boolean isActivarSuscripcion() { return activarSuscripcion; }
    public void setActivarSuscripcion(boolean activarSuscripcion) { this.activarSuscripcion = activarSuscripcion; }

    public int getContadorElementoTareas() { return contadorElementoTareas; }
    public void setContadorElementoTareas(int contadorElementoTareas) { this.contadorElementoTareas = contadorElementoTareas; }

    public int getContadorElementoRecordatorios() { return contadorElementoRecordatorios; }
    public void setContadorElementoRecordatorios(int contadorElementoRecordatorios) { this.contadorElementoRecordatorios = contadorElementoRecordatorios; }

    // Metodos propios
    public boolean conteoTarea() {
        if (contadorElementoTareas < limiteElementosTareas) {
            contadorElementoTareas++;
            System.out.println("Tarea agregada. (" + contadorElementoTareas + "/" + limiteElementosTareas + ")");
            return true;
        } else {
            System.out.println("Limite de tareas alcanzado: " + limiteElementosTareas);
            return false;
        }
    }

    public boolean conteoRecordatorio() {
        if (contadorElementoRecordatorios < limiteElementosRecordatorios) {
            contadorElementoRecordatorios++;
            System.out.println("Recordatorio agregado. (" + contadorElementoRecordatorios + "/" + limiteElementosRecordatorios + ")");
            return true;
        } else {
            System.out.println("Limite de recordatorios alcanzado: " + limiteElementosRecordatorios);
            return false;
        }
    }

    // Metodos heredados
    @Override
    public void verificarUsuario() {
        super.verificarUsuario();
    }

    @Override
    public void crearElemento(Elemento elemento) {
        // CORRECCIÓN: delega al contador antes de agregar, respetando el límite
        super.crearElemento(elemento);
    }

    @Override
    public void imprimirUsuario() {
        super.imprimirUsuario();
        System.out.println("\nEn tu version actual no gozas de los beneficios completos.");
        System.out.println("Para ello debes comprar el servicio Premium.");
    }

    @Override
    public void modoSuscripcion() {
        System.out.print("Desea activar la suscripcion? (Si/No): ");
        Scanner sc = new Scanner(System.in);
        String respuesta = sc.nextLine();
        if (respuesta.equalsIgnoreCase("Si")) {
            // CORRECCIÓN: en lugar de crear un objeto descartado, se actualiza el estado del usuario actual
            setActivarSuscripcion(true);
            setAccesoCompleto(true);
            System.out.println("\nLa suscripcion esta activada.");
            System.out.println("No posees limites para la creacion de Tareas, Recordatorios y Compartidos.\n");
        } else {
            setAccesoCompleto(false);
            System.out.println("La suscripcion se mantiene sin cambios.\n");
        }
    }
}
