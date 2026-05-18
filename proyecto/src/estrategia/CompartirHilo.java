package estrategia;

import modeloElemento.Elemento;
import modeloUsuario.Usuario;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Hilo que comparte un elemento con un usuario destino de forma segura
 * usando ReentrantLock para evitar condiciones de carrera cuando
 * múltiples hilos intentan compartir al mismo tiempo.
 */
public class CompartirHilo implements Runnable {

    // Lock compartido entre todos los hilos de compartir
    private static final ReentrantLock lock = new ReentrantLock();

    private final Usuario usuarioOrigen;
    private final Usuario usuarioDestino;
    private final Elemento elemento;

    public CompartirHilo(Usuario usuarioOrigen, Usuario usuarioDestino, Elemento elemento) {
        this.usuarioOrigen = usuarioOrigen;
        this.usuarioDestino = usuarioDestino;
        this.elemento = elemento;
    }

    @Override
    public void run() {
        String hiloNombre = Thread.currentThread().getName();
        System.out.println("  [Hilo: " + hiloNombre + "] Intentando compartir '" + elemento.getTitulo() + "'...");

        lock.lock();
        try {
            // Verificar que el elemento no haya sido ya compartido con este usuario
            boolean yaCompartido = elemento.getColaboradores().stream()
                    .anyMatch(u -> u.getEmail().equalsIgnoreCase(usuarioDestino.getEmail()));

            if (yaCompartido) {
                System.out.println("  [Hilo: " + hiloNombre + "] El elemento ya fue compartido con "
                        + usuarioDestino.getNombreCompleto() + ".");
                return;
            }

            // Verificar que el destino no sea el mismo dueño
            if (elemento.getUsuario() != null &&
                elemento.getUsuario().getEmail().equalsIgnoreCase(usuarioDestino.getEmail())) {
                System.out.println("  [Hilo: " + hiloNombre + "] No puedes compartir un elemento contigo mismo.");
                return;
            }

            // Compartir de forma segura
            usuarioDestino.getElemento().add(elemento);
            elemento.getColaboradores().add(usuarioDestino);
            elemento.setCantidadColaboradores(elemento.getCantidadColaboradores() + 1);

            System.out.println("  [Hilo: " + hiloNombre + "] Elemento '" + elemento.getTitulo()
                    + "' compartido exitosamente con " + usuarioDestino.getNombreCompleto() + ".");

        } finally {
            lock.unlock();
        }
    }
}
