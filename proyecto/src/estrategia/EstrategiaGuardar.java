package estrategia;

import modeloElemento.Elemento;
import java.util.List;

public class EstrategiaGuardar implements EstrategiaElemento {

    @Override
    public void ejecutar(List<Elemento> elementos, Elemento elemento) {
        System.out.println("ESTRATEGIA GUARDAR ELEMENTO");
        System.out.println("Elemento a guardar: " + elemento.getTitulo());

        // CORRECCIÓN: verificar duplicados por id antes de agregar
        boolean yaExiste = elementos.stream().anyMatch(e -> e.getId() == elemento.getId());
        if (yaExiste) {
            System.out.println("El elemento ya existe en la lista.");
        } else {
            elementos.add(elemento);
            System.out.println("Elemento guardado correctamente.");
        }
    }

    @Override
    public String getNombreEstrategia() {
        return "GUARDAR";
    }
}
