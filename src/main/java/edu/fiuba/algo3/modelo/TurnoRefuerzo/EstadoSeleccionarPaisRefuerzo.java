package edu.fiuba.algo3.modelo.TurnoRefuerzo;

import edu.fiuba.algo3.modelo.Pais;

public interface EstadoSeleccionarPaisRefuerzo {

    void seleccionarPais(Pais pais);

    void cancelarAccion();

    void reforzar(int cantidadEjercitosAReforzar);

    void agregarEjercitos(int ejercitos);

    boolean tieneEjercitosParaReforzar();

    boolean puedoReforzar();

    int getEjercitosParaReforzar();

    boolean puedoCancelar();

    boolean puedoPasarDeTurno();

    boolean paisPuedeSeleccionarse(Pais pais);

    boolean paisSeleccionado(String nombrePais);
}
