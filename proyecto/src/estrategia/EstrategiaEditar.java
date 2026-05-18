package estrategia;

import modeloElemento.Elemento;
import catalogo.Prioridad;
import java.util.List;
import java.util.Scanner;

// CORRECCIÓN: se eliminó Runnable ya que hacer join() inmediato anula cualquier beneficio de concurrencia.
// La lógica ahora es directa y más clara.
public class EstrategiaEditar implements EstrategiaElemento {

    private final Elemento elemento;

    // Constructor parametrizado
    public EstrategiaEditar(Elemento elemento) {
        this.elemento = elemento;
    }

    private void editar() {
        // CORRECCIÓN: Scanner local en lugar de static compartido (evita condiciones de carrera)
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("ESTRATEGIA EDITAR ELEMENTO");
            System.out.println("Elemento a editar: " + elemento.getTitulo());
            System.out.println("Ingrese que opcion desea editar: ");
            System.out.println("1. Editar titulo del elemento");
            System.out.println("2. Editar descripcion del elemento");
            System.out.println("3. Editar prioridad del elemento");
            System.out.print("Opcion seleccionada: ");

            int opcion = Integer.parseInt(sc.nextLine().trim());

            if (opcion == 1) {
                System.out.print("Nuevo titulo: ");
                String nuevoTitulo = sc.nextLine().trim();
                if (nuevoTitulo.isEmpty()) {
                    throw new IllegalArgumentException("El titulo no puede estar vacio");
                }
                elemento.setTitulo(nuevoTitulo);
                System.out.println("El titulo fue modificado correctamente.");

            } else if (opcion == 2) {
                System.out.print("Nueva descripcion: ");
                String nuevaDescripcion = sc.nextLine().trim();
                elemento.setDescripcion(nuevaDescripcion);
                System.out.println("La descripcion fue modificada correctamente.");

            } else if (opcion == 3) {
                System.out.println("Nueva prioridad: 1=ALTA, 2=MEDIA, 3=BAJA");
                System.out.print("Ingrese la opcion: ");
                int prioridad = Integer.parseInt(sc.nextLine().trim());
                if (prioridad == 1) {
                    elemento.setPrioridad(Prioridad.ALTA);
                } else if (prioridad == 2) {
                    elemento.setPrioridad(Prioridad.MEDIA);
                } else {
                    elemento.setPrioridad(Prioridad.BAJA);
                }
                System.out.println("La prioridad fue modificada correctamente.");

            } else {
                throw new IllegalArgumentException("La opcion ingresada no es valida");
            }
            System.out.println("La edicion del elemento fue finalizada con exito.");

        } catch (NumberFormatException e) {
            System.out.println("El valor ingresado no es un numero valido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            // CORRECCIÓN: espacio añadido entre mensaje y detalle del error
            System.out.println("Ocurrio un error inesperado: " + e.getMessage());
        }
    }

    // CORRECCIÓN: ejecutar llama directamente a editar() sin hilo innecesario.
    // El parámetro 'elemento' recibido se ignora porque la instancia ya tiene el suyo;
    // se usa this.elemento para consistencia.
    @Override
    public void ejecutar(List<Elemento> elementos, Elemento elemento) {
        editar();
    }

    @Override
    public String getNombreEstrategia() {
        return "EDITAR";
    }
}
