package modeloUsuario;

import modeloElemento.Elemento;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Usuario implements AccionesUsuario {

    // Atributos
    private String nombreCompleto;
    private int edad;
    private String email;
    private String password;
    private boolean accesoCompleto;
    private LocalDate fechaActual;
    private List<Elemento> elemento;

    // Constructor parametrizado
    public Usuario(String nombreCompleto, int edad, String email, String password,
                   int cantidadTareas, int cantidadRecordatorios) {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.email = email;
        this.password = password;
        this.accesoCompleto = false;
        this.fechaActual = LocalDate.now();
        this.elemento = new ArrayList<>();
    }

    public Usuario() {
        this.elemento = new ArrayList<>();
        this.fechaActual = LocalDate.now();
    }

    // Getters y Setters
    public String getNombreCompleto() { return nombreCompleto; }
    // CORRECCIÓN: el setter ahora asigna el parámetro s correctamente
    public void setNombreCompleto(String s) { this.nombreCompleto = s; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean getAccesoCompleto() { return accesoCompleto; }
    public void setAccesoCompleto(boolean accesoCompleto) { this.accesoCompleto = accesoCompleto; }

    public LocalDate getFechaActual() { return fechaActual; }
    public void setFechaActual(LocalDate fechaActual) { this.fechaActual = fechaActual; }

    public List<Elemento> getElemento() { return elemento; }
    public void setElemento(List<Elemento> elemento) { this.elemento = elemento; }

    // Metodos propios
    public void listarElementos() {
        for (Elemento e : this.elemento) {
            e.imprimirElementos();
        }
    }

    public void eliminarElemento(Elemento elemento) {
        this.elemento.remove(elemento);
    }

    public void compartirElemento(List<Usuario> usuarioList) {
        Scanner sc = new Scanner(System.in);
        Elemento elementoEncontrado = null;
        Usuario usuarioEncontrado = null;

        System.out.print("Introduzca el nombre del Elemento: ");
        String nombreElemento = sc.nextLine();

        for (Elemento e : this.elemento) {
            if (e.getTitulo().equalsIgnoreCase(nombreElemento)) {
                elementoEncontrado = e;
                break;
            }
        }
        if (elementoEncontrado == null) {
            System.out.println("El Elemento no existe.");
            return;
        }

        System.out.print("Introduzca el Email del Usuario a enviar el elemento: ");
        String emailUsuario = sc.nextLine();

        for (Usuario u : usuarioList) {
            if (u.getEmail().equalsIgnoreCase(emailUsuario)) {
                usuarioEncontrado = u;
                break;
            }
        }
        if (usuarioEncontrado == null) {
            System.out.println("El Usuario no existe.");
            return;
        }

        usuarioEncontrado.getElemento().add(elementoEncontrado);
        elementoEncontrado.getColaboradores().add(usuarioEncontrado);
        elementoEncontrado.setCantidadColaboradores(elementoEncontrado.getCantidadColaboradores() + 1);
        System.out.println("El Elemento fue compartido exitosamente con " + usuarioEncontrado.getNombreCompleto() + ".");
    }

    // Metodos heredados
    @Override
    public void verificarUsuario() {
        System.out.println("\nPor favor verifique su usuario.");
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Ingrese su Nombre completo: ");
            setNombreCompleto(sc.nextLine());
            System.out.print("Ingrese su Email: ");
            setEmail(sc.nextLine());

            // CORRECCIÓN: se guarda la password ingresada en variable separada antes de comparar
            System.out.print("Ingrese su Password: ");
            String passwordIngresada = sc.nextLine();
            if (passwordIngresada.equals(getPassword())) {
                System.out.println("El usuario se encuentra verificado.");
            } else {
                System.out.println("El usuario NO se encuentra verificado. Password incorrecto.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error durante la verificacion.");
        }
    }

    @Override
    public void crearElemento(Elemento elemento) {
        this.elemento.add(elemento);
    }

    @Override
    public void imprimirUsuario() {
        System.out.println(" ");
        // CORRECCIÓN: typos corregidos y se usa elemento.size() en lugar de "c"
        System.out.println("El nombre del Usuario es: " + nombreCompleto
                + " quien posee el correo electrónico: " + email);
        System.out.println("La cantidad de elementos que posee el Usuario es: " + elemento.size());
    }
}
