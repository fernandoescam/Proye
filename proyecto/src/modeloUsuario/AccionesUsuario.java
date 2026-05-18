package modeloUsuario;

import modeloElemento.Elemento;

public interface AccionesUsuario {
    // Modificadores public eliminados (redundantes en interfaces)
    void crearElemento(Elemento elemento);
    void modoSuscripcion();
    void verificarUsuario();
    void imprimirUsuario();
}
