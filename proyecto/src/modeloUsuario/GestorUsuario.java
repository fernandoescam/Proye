package modeloUsuario;

import java.time.LocalDate;

/**
 * GestorUsuario: maneja la conversión entre UsuarioGeneral y UsuarioPremium.
 * Resuelve el problema original donde modoSuscripcion() creaba objetos descartados.
 * Ahora el cambio de tipo se maneja aquí, copiando todos los datos del usuario actual.
 */
public class GestorUsuario {

    /**
     * Convierte un UsuarioGeneral a UsuarioPremium copiando todos sus datos.
     */
    public static UsuarioPremium convertirAPremium(UsuarioGeneral general) {
        UsuarioPremium premium = new UsuarioPremium(
                general.getNombreCompleto(),
                general.getEdad(),
                general.getEmail(),
                general.getPassword(),
                0, 0,
                4.99f,
                true,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );
        // Copiar elementos existentes
        premium.setElemento(general.getElemento());
        premium.setAccesoCompleto(true);
        return premium;
    }

    /**
     * Convierte un UsuarioPremium a UsuarioGeneral copiando todos sus datos.
     */
    public static UsuarioGeneral convertirAGeneral(UsuarioPremium premium) {
        UsuarioGeneral general = new UsuarioGeneral(
                premium.getNombreCompleto(),
                premium.getEdad(),
                premium.getEmail(),
                premium.getPassword(),
                0, 0,
                false, 0, 0
        );
        // Copiar elementos existentes
        general.setElemento(premium.getElemento());
        general.setAccesoCompleto(false);
        return general;
    }
}
