package estrategia;

import modeloElemento.Elemento;
import java.util.List;

public interface EstrategiaElemento {
    // Modificadores public eliminados (redundantes en interfaces)
    void ejecutar(List<Elemento> elementos, Elemento elemento);
    String getNombreEstrategia();
}
