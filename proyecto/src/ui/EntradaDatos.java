package ui;

import catalogo.Estado;
import modeloElemento.ElementoTarea;
import modeloElemento.ElementoRecordatorio;
import modeloElemento.Elemento;
import modeloUsuario.Usuario;
import modeloUsuario.UsuarioGeneral;
import modeloUsuario.UsuarioPremium;
import modeloUsuario.GestorUsuario;
import estrategia.EstrategiaEditar;
import estrategia.CompartirHilo;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EntradaDatos {

    private final Scanner sc = new Scanner(System.in);

    // Usuario con sesion activa actualmente
    private Usuario usuarioActivo = null;

    // Todos los usuarios registrados en el sistema
    private final List<Usuario> todosLosUsuarios = new ArrayList<>();
    private int nextId = 1;
    private static final String LIN = "─────────────────────────────────────";

    // ── ARRANQUE ──────────────────────────────────────────────
    public void iniciar() {
        System.out.println("  BIENVENIDO AL SISTEMA: ");
        System.out.println("  RECORDATORIO DE TAREAS ");
        System.out.println(LIN);

        boolean salir = false;
        while (!salir) {
            System.out.println("\n  [ 1 ] Iniciar sesion");
            System.out.println("  [ 2 ] Registrar nuevo usuario");
            System.out.println("  [ 3 ] Salir");
            System.out.print("  -> ");
            switch (leerInt()) {
                case 1  -> iniciarSesion();
                case 2  -> registrar();
                case 3  -> { System.out.println("  Hasta luego!"); salir = true; }
                default -> System.out.println("  Opcion no valida.");
            }
        }
        sc.close();
    }

    // ── ACCESO ────────────────────────────────────────────────
    private void iniciarSesion() {
        if (todosLosUsuarios.isEmpty()) {
            System.out.println("  No hay usuarios registrados. Primero debes registrarte.");
            return;
        }

        System.out.println("\n INICIO DE SESION \n" + LIN);
        System.out.print("  Correo          : "); String email    = sc.nextLine().trim();
        System.out.print("  Contrasena      : "); String password = sc.nextLine().trim();

        // Buscar usuario por correo en la lista global
        Usuario encontrado = null;
        for (Usuario u : todosLosUsuarios) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)) {
                encontrado = u;
                break;
            }
        }

        if (encontrado != null) {
            usuarioActivo = encontrado;
            String tipo = (usuarioActivo instanceof UsuarioPremium) ? "PREMIUM ★" : "General";
            System.out.println("  Hola, " + usuarioActivo.getNombreCompleto() + "! [" + tipo + "] Se bienvenido nuevamente. ");
            menuPrincipal();
        } else {
            System.out.println("ERROR:       El Correo o contrasena son incorrectos.");
            System.out.println(" Por favor, vuelva a intentarlo... ");
        }
    }

    private void registrar() {
        System.out.println("\n  NUEVO USUARIO\n" + LIN);

        //Solicita el ingreso del Nombre, Edad, Correo y Contrasena para registrarlo
        System.out.print("  Nombre    : ");
        String nombre = sc.nextLine().trim();
        while (nombre.isEmpty()) {
            System.out.println("  El nombre no puede estar vacio.");
            System.out.print("  Nombre    : ");
            nombre = sc.nextLine().trim();
        }

        int edad = 0;
        while (true) {
            System.out.print("  Edad      : ");
            try {
                edad = sc.nextInt(); sc.nextLine();
                if (edad <= 0) System.out.println("  La edad debe ser positiva.");
                else break;
            } catch (InputMismatchException e) {
                System.out.println("  Ingresa solo numeros."); sc.nextLine();
            }
        }

        System.out.print("  Correo    : ");
        String email = sc.nextLine().trim();
        while (email.isEmpty() || !email.contains("@")) {
            System.out.println("  Ingresa un correo valido.");
            System.out.print("  Correo    : ");
            email = sc.nextLine().trim();
        }
        // Verificar que el correo no esté ya registrado
        String emailFinal = email;
        boolean yaExiste = todosLosUsuarios.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(emailFinal));
        if (yaExiste) {
            System.out.println("  Ese correo ya esta registrado.");
            return;
        }

        System.out.print("  Contrasena: ");
        String password = sc.nextLine().trim();
        while (password.isEmpty()) {
            System.out.println("  La contrasena no puede estar vacia.");
            System.out.print("  Contrasena: ");
            password = sc.nextLine().trim();
        }

        UsuarioGeneral nuevo = new UsuarioGeneral(nombre, edad, email, password, 0, 0, false, 0, 0);
        todosLosUsuarios.add(nuevo);

        System.out.println("  Usuario registrado: " + nombre + " (" + email + ") [General]");
        System.out.println("  Ya puede iniciar sesion desde el menu principal.");
    }

    // ── MENU PRINCIPAL ────────────────────────────────────────
    //Es el cuerpo del menu de Interaccion por medio de leerInt().
    private void menuPrincipal() {
        boolean salir = false;
        while (!salir) {
            String tipoCuenta = (usuarioActivo instanceof UsuarioPremium) ? "PREMIUM ★" : "General";
            System.out.println("\n" + LIN);
            System.out.println("  Hola, Bienvenido Nuevamente!!!");
            System.out.print(" " + usuarioActivo.getNombreCompleto() + " [" + tipoCuenta + "] " );
            System.out.println("\n  MENU \n");
            System.out.println(LIN);
            System.out.println("  [ 1  ] Agregar tarea");
            System.out.println("  [ 2  ] Agregar recordatorio");
            System.out.println("  [ 3  ] Ver pendientes");
            System.out.println("  [ 4  ] Completar tarea");
            System.out.println("  [ 5  ] Eliminar elemento");
            System.out.println("  [ 6  ] Ver todos los elementos");
            System.out.println("  [ 7  ] Editar elemento");
            System.out.println("  [ 8  ] Compartir elemento");
            System.out.println("  [ 9  ] Cambiar suscripcion");
            System.out.println("  [ 10 ] Informacion de usuario");
            System.out.println("  [ 11 ] Cerrar sesion");
            System.out.println(LIN);
            System.out.print("  -> ");

            switch (leerInt()) {
                case 1  -> agregarTarea();
                case 2  -> agregarRecordatorio();
                case 3  -> verPendientes();
                case 4  -> completar();
                case 5  -> eliminar();
                case 6  -> verTodos();
                case 7  -> editarElemento();
                case 8  -> compartirElemento();
                case 9  -> cambiarSuscripcion();
                case 10 -> usuarioActivo.imprimirUsuario();
                case 11 -> {
                    System.out.println("  La sesion fue cerrada exitosamente. Hasta luego, " + usuarioActivo.getNombreCompleto() + "!");
                    usuarioActivo = null;
                    salir = true;
                }
                default -> System.out.println("  Opcion no valida.");
            }
        }
    }

    // ── AGREGAR ───────────────────────────────────────────────
    //Permite agregar una Tarea por medio de ElementoTarea
    private void agregarTarea() {
        if (usuarioActivo instanceof UsuarioGeneral general) {
            if (!general.conteoTarea()) return;
        }
        System.out.println("\n  AGREGAR NUEVA TAREA \n");
        ElementoTarea tarea = new ElementoTarea();
        tarea.setId(nextId++);
        tarea.setFechaCreacion(LocalDate.now());
        tarea.setUsuario(usuarioActivo);
        tarea.crearElemento();
        agregarALista(tarea);
        System.out.println("  Tarea guardada exitosamente.");
    }

    //Permite agregar un recordatorio por medio de ElementoRecordatorio
    private void agregarRecordatorio() {
        if (usuarioActivo instanceof UsuarioGeneral general) {
            if (!general.conteoRecordatorio()) return;
        }
        System.out.println("\n  AGREGAR NUEVO RECORDATORIO \n");
        ElementoRecordatorio rec = new ElementoRecordatorio();
        rec.setId(nextId++);
        rec.setFechaCreacion(LocalDate.now());
        rec.setUsuario(usuarioActivo);
        rec.crearElemento();
        agregarALista(rec);
        rec.activarAlerta();
        System.out.println("  Recordatorio guardado exitosamente.");
    }

    // ── VER / COMPLETAR / ELIMINAR / EDITAR ──────────────────
    //Muestra todos los elementos no terminados.
    private void verPendientes() {
        System.out.println("\n  PENDIENTES \n" + LIN);
        List<Elemento> lista = usuarioActivo.getElemento();
        if (lista == null || lista.isEmpty()) { System.out.println("  Sin elementos."); return; }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean hay = false;
        for (int i = 0; i < lista.size(); i++) {
            Elemento e = lista.get(i);
            if (e instanceof ElementoTarea t
                    && t.getEstado() != Estado.COMPLETADO
                    && t.getEstado() != Estado.CANCELADA) {
                System.out.printf("  [%d] TAREA        | %s | %s | %s%n",
                        i + 1, t.getTitulo(), t.getEstado(), t.getPrioridad());
                hay = true;
            } else if (e instanceof ElementoRecordatorio r) {
                System.out.printf("  [%d] RECORDATORIO | %s | vence %s%n",
                        i + 1, r.getTitulo(),
                        r.getFechaLimite() != null ? r.getFechaLimite().format(fmt) : "sin fecha");
                hay = true;
            }
        }
        if (!hay) System.out.println("  Sin pendientes.");
    }

    //Permite cambiar el estado de un elemento Tarea
    private void completar() {
        if (!listar()) return;
        System.out.print("  Numero de Elemento a completar (0 cancela): ");
        int idx = leerInt() - 1;
        List<Elemento> lista = usuarioActivo.getElemento();
        if (idx < 0 || idx >= lista.size()) { System.out.println("  Cancelado."); return; }

        Elemento e = lista.get(idx);
        if (e instanceof ElementoTarea t) {
            if (t.getEstado() == Estado.COMPLETADO) { System.out.println("  Ya estaba completada."); return; }
            t.setEstado(Estado.COMPLETADO);
            System.out.println("  Completada: " + t.getTitulo());
        } else {
            System.out.println("  Solo se pueden completar tareas.");
        }
    }

    //Permite el eliminar un elemento
    private void eliminar() {
        if (!listar()) return;
        System.out.print("  Numero de ID del Elemento a eliminar (0 cancela): ");
        int idx = leerInt() - 1;
        List<Elemento> lista = usuarioActivo.getElemento();
        if (idx < 0 || idx >= lista.size()) { System.out.println("  Cancelado."); return; }

        while (true) {
            System.out.print(" El Elemento sera eliminado. \n Desea continuar? (s/n): ");
            String resp = sc.nextLine().trim().toLowerCase();
            if (resp.equals("s"))      { System.out.println("  Eliminado: " + lista.remove(idx).getTitulo()); break; }
            else if (resp.equals("n")) { System.out.println("  Cancelado."); break; }
            else                       { System.out.println("  Respuesta invalida. Escribe s o n."); }
        }
    }

    //Permite el editar un elemento
    private void editarElemento() {
        if (!listar()) return;
        System.out.print("  Numero de ID del Elemento a editar (0 cancela): ");
        int idx = leerInt() - 1;
        List<Elemento> lista = usuarioActivo.getElemento();
        if (idx < 0 || idx >= lista.size()) { System.out.println("  Cancelado."); return; }
        Elemento e = lista.get(idx);
        new EstrategiaEditar(e).ejecutar(lista, e);
    }

    //Muestra todos los elementos actuales
    private void verTodos() {
        System.out.println("\n  TODOS LOS ELEMENTOS\n" + LIN);
        List<Elemento> lista = usuarioActivo.getElemento();
        if (lista == null || lista.isEmpty()) { System.out.println("  Sin elementos."); return; }
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("%n  [%d] ", i + 1);
            lista.get(i).imprimirElementos();
        }
    }

    // ── COMPARTIR CON MULTIHILO ───────────────────────────────

    private void compartirElemento() {
        // Mostrar solo elementos propios del usuario activo
        List<Elemento> lista = usuarioActivo.getElemento();
        List<Elemento> propios = new ArrayList<>();
        if (lista != null) {
            for (Elemento e : lista) {
                if (e.getUsuario() != null
                        && e.getUsuario().getEmail().equalsIgnoreCase(usuarioActivo.getEmail())) {
                    propios.add(e);
                }
            }
        }

        if (propios.isEmpty()) {
            System.out.println("  No tienes elementos propios para compartir.");
            return;
        }

        System.out.println("\n  COMPARTIR ELEMENTO\n" + LIN);
        for (int i = 0; i < propios.size(); i++) {
            System.out.printf("  [%d] %s%n", i + 1, propios.get(i).getTitulo());
        }
        System.out.print("  Selecciona el ID del Eemento a compartir (0 cancela): ");
        int idx = leerInt() - 1;
        if (idx < 0 || idx >= propios.size()) { System.out.println("  Cancelado."); return; }
        Elemento elementoACompartir = propios.get(idx);

        // Usuarios disponibles distintos al activo
        List<Usuario> otrosUsuarios = new ArrayList<>();
        for (Usuario u : todosLosUsuarios) {
            if (!u.getEmail().equalsIgnoreCase(usuarioActivo.getEmail())) {
                otrosUsuarios.add(u);
            }
        }

        if (otrosUsuarios.isEmpty()) {
            System.out.println("  No hay otros usuarios registrados.");
            System.out.println("  Vuelve al menu principal y registra otro usuario primero.");
            return;
        }

        // Mostrar usuarios disponibles
        System.out.println("  Usuarios disponibles:");
        for (int i = 0; i < otrosUsuarios.size(); i++) {
            System.out.printf("  [%d] %s (%s)%n",
                    i + 1,
                    otrosUsuarios.get(i).getNombreCompleto(),
                    otrosUsuarios.get(i).getEmail());
        }

        // Seleccionar con cuántos compartir (uno o varios = multihilo)
        System.out.print("  Con cuantos usuarios compartir? (1-" + otrosUsuarios.size() + "): ");
        int cantidad = leerInt();
        if (cantidad < 1 || cantidad > otrosUsuarios.size()) {
            System.out.println("  Cantidad invalida. Cancelado.");
            return;
        }

        // Un hilo por cada usuario destino, todos arrancan a la vez
        List<Thread> hilos = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            Thread hilo = new Thread(
                    new CompartirHilo(usuarioActivo, otrosUsuarios.get(i), elementoACompartir),
                    "Compartir-" + (i + 1)
            );
            hilos.add(hilo);
        }

        System.out.println("\n  Lanzando " + cantidad + " hilo(s) en paralelo...");
        for (Thread h : hilos) h.start();
        for (Thread h : hilos) {
            try { h.join(); }
            catch (InterruptedException e) { System.out.println("  Hilo interrumpido."); }
        }
        System.out.println("  Compartir finalizado.");
    }

    // ── CAMBIAR SUSCRIPCION ───────────────────────────────────
    //Cambia el tipo de suscripcion del Usuario.
    private void cambiarSuscripcion() {
        if (usuarioActivo instanceof UsuarioGeneral general) {
            System.out.println("\n  ACTIVAR PLAN PREMIUM \n");
            System.out.println("  Precio   : $4.99/mes");
            System.out.println("  Beneficio: Sin limites de tareas ni recordatorios.");
            System.out.print("  Desea confirmar la activacion? (s/n): ");
            if (sc.nextLine().trim().equalsIgnoreCase("s")) {
                UsuarioPremium premium = GestorUsuario.convertirAPremium(general);
                actualizarUsuarioGlobal(premium);
                usuarioActivo = premium;
                System.out.println("  Plan Premium activado! ★");
            } else {
                System.out.println("  Sin cambios.");
            }

        } else if (usuarioActivo instanceof UsuarioPremium premium) {
            System.out.println("\n  CANCELAR PLAN PREMIUM \n");
            System.out.println("  Volveras al plan General (limite: 8 tareas, 4 recordatorios).");
            System.out.print("  Confirmar cancelacion? (s/n): ");
            if (sc.nextLine().trim().equalsIgnoreCase("s")) {
                UsuarioGeneral general = GestorUsuario.convertirAGeneral(premium);
                actualizarUsuarioGlobal(general);
                usuarioActivo = general;
                System.out.println("  Plan Premium cancelado. Ahora eres usuario General.");
            } else {
                System.out.println("  Sin cambios.");
            }
        }
    }

    // ── HELPERS ───────────────────────────────────────────────
    //Busca a todos los usuarios registrados
    private void actualizarUsuarioGlobal(Usuario nuevoUsuario) {
        for (int i = 0; i < todosLosUsuarios.size(); i++) {
            if (todosLosUsuarios.get(i).getEmail().equalsIgnoreCase(nuevoUsuario.getEmail())) {
                todosLosUsuarios.set(i, nuevoUsuario);
                return;
            }
        }
    }

    //Agrega el elemento a una lista
    private void agregarALista(Elemento e) {
        List<Elemento> lista = usuarioActivo.getElemento();
        if (lista == null) lista = new ArrayList<>();
        lista.add(e);
        usuarioActivo.setElemento(lista);
    }

    //Ingresa los elementos nuevos a las listas del Usuario
    private boolean listar() {
        List<Elemento> lista = usuarioActivo.getElemento();
        if (lista == null || lista.isEmpty()) { System.out.println("  Sin elementos registrados."); return false; }
        for (int i = 0; i < lista.size(); i++) {
            Elemento e = lista.get(i);
            String tipo  = (e instanceof ElementoTarea) ? "T" : "R";
            String estado = (e instanceof ElementoTarea t) ? t.getEstado().toString() : "---";
            System.out.printf("  [%d] [%s] %s — %s%n", i + 1, tipo, e.getTitulo(), estado);
        }
        return true;
    }

    //Lee el valor de ingrose para el actuar del menu.
    private int leerInt() {
        int numero = -1;
        try { numero = sc.nextInt(); sc.nextLine(); }
        catch (InputMismatchException e) { sc.nextLine(); }
        return numero;
    }

//    private LocalDate leerFecha() {
//        while (true) {
//            try {
//                System.out.print(" La Fecha(dd/MM/yyyy) de registro es: ");
//                String[] p = sc.nextLine().trim().split("/");
//                LocalDate fecha = LocalDate.of(
//                        Integer.parseInt(p[2]), Integer.parseInt(p[1]), Integer.parseInt(p[0]));
//                if (fecha.isBefore(LocalDate.now())) System.out.println("  La fecha debe ser hoy o futura.");
//                else return fecha;
//            } catch (NumberFormatException e) { System.out.println("  Ingresa solo numeros."); }
//            catch (Exception e)              { System.out.println("  Formato invalido. Usa dd/MM/yyyy."); }
//        }
//    }
}
