package estrategia;

import modeloElemento.Elemento;
import java.util.List;

public class EstrategiaEliminar implements EstrategiaElemento {

    @Override
    public void ejecutar(List<Elemento> elementos, Elemento elemento) {
        System.out.println("ESTRATEGIA ELIMINAR ELEMENTO");
        System.out.println("Elemento a eliminar: " + elemento.getTitulo());

        // CORRECCIÓN: verificar si fue eliminado y avisar si no existía en la lista
        boolean eliminado = elementos.removeIf(e -> e.getId() == elemento.getId());
        if (!eliminado) {
            System.out.println("El elemento no fue encontrado en la lista.");
        } else {
            System.out.println("Elemento eliminado correctamente.");
        }
    }

    @Override
    public String getNombreEstrategia() {
        return "ELIMINAR";
    }
}
