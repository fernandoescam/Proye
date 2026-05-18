package modeloElemento;

import catalogo.Prioridad;
import modeloUsuario.Usuario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Elemento implements AccionesElemento {

    // Atributos
    private int id;
    private String titulo;
    private String descripcion;
    private Prioridad prioridad;
    private LocalDate fechaCreacion;
    private LocalDate fechaLimite;
    private Usuario usuario;
    private List<Usuario> colaboradores;
    private int cantidadColaboradores;

    // Constructor parametrizado: ahora protected para que subclases de otros paquetes puedan usarlo
    protected Elemento(int id, String titulo, String descripcion, int cantidadColaboradores,
                       Prioridad prioridad, LocalDate fechaCreacion, LocalDate fechaLimite, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.fechaCreacion = fechaCreacion;
        this.fechaLimite = fechaLimite;
        this.usuario = usuario;
        this.colaboradores = new ArrayList<>();
        this.cantidadColaboradores = cantidadColaboradores;
    }

    public Elemento() {
        this.colaboradores = new ArrayList<>();
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public int getCantidadColaboradores() { return cantidadColaboradores; }
    public void setCantidadColaboradores(int cantidadColaboradores) { this.cantidadColaboradores = cantidadColaboradores; }

    public List<Usuario> getColaboradores() { return colaboradores; }
    public void setColaboradores(List<Usuario> colaboradores) { this.colaboradores = colaboradores; }

    // Metodos heredados
    @Override
    public void crearElemento() {
        Scanner sc = new Scanner(System.in); // CORRECCIÓN: instancia local, no estática
        System.out.println("Creando nuevo Elemento.");
        System.out.print("Introduzca el Titulo del Elemento: ");
        setTitulo(sc.nextLine());
        System.out.print("Introduzca la Descripcion del Elemento: ");
        setDescripcion(sc.nextLine());

        // CORRECCIÓN: validación con try-catch para entrada no numérica
        int opcion = -1;
        while (opcion < 1 || opcion > 3) {
            System.out.println("Introduzca la Prioridad del Elemento (1=ALTA, 2=MEDIA, 3=BAJA): ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
                if (opcion < 1 || opcion > 3) System.out.println("Opcion no valida, intente de nuevo.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese solo un numero.");
            }
        }
        switch (opcion) {
            case 1 -> setPrioridad(Prioridad.ALTA);
            case 2 -> setPrioridad(Prioridad.MEDIA);
            default -> setPrioridad(Prioridad.BAJA);
        }

        System.out.println("Introduzca la fecha limite del Elemento (DD/MM/YYYY): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            try {
                setFechaLimite(LocalDate.parse(sc.nextLine().trim(), formatter));
                break;
            } catch (Exception e) {
                System.out.println("Formato invalido. Use DD/MM/YYYY.");
            }
        }
        System.out.println("Elemento creado correctamente.");
    }

    @Override
    public void imprimirElementos() {
        System.out.println("ID: " + id);
        System.out.println("Titulo: " + titulo);
        System.out.println("Descripcion: " + descripcion);
        System.out.println("Prioridad: " + prioridad);
        System.out.println("Fecha Creacion: " + fechaCreacion);
        System.out.println("Fecha Limite: " + fechaLimite);

        // CORRECCIÓN: se separó el creador de los colaboradores y se protege contra null
        if (usuario != null) {
            System.out.println("Usuario creador: " + usuario.getNombreCompleto());
        }
        System.out.println("Cantidad de Colaboradores: " + cantidadColaboradores);
        if (colaboradores != null && !colaboradores.isEmpty()) {
            for (Usuario colab : colaboradores) {
                System.out.println("  Colaborador: " + colab.getNombreCompleto());
            }
        }
    }
}
