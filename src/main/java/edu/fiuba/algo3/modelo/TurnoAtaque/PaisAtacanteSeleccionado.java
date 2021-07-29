package edu.fiuba.algo3.modelo.TurnoAtaque;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Pais;
import edu.fiuba.algo3.modelo.excepciones.AtaqueAPaisNoLimitrofeException;
import edu.fiuba.algo3.modelo.excepciones.AtaqueAPaisPropioException;
import edu.fiuba.algo3.modelo.excepciones.AtaqueInvalidoException;

public class PaisAtacanteSeleccionado implements EstadoSeleccionarPaisAtaque {

    private final TurnoAtaque turnoAtaque;
    private final Jugador jugador;
    private final Pais paisAtacante;

    public PaisAtacanteSeleccionado(TurnoAtaque turnoAtaque, Jugador jugador, Pais paisAtacante) {
        this.turnoAtaque = turnoAtaque;
        this.jugador = jugador;
        this.paisAtacante = paisAtacante;
    }

    @Override
    public void seleccionarPais(Pais paisDefensor) {
        if (!paisDefensor.esLimitrofe(paisAtacante)) {
            throw new AtaqueAPaisNoLimitrofeException(paisDefensor + " no limita con " + paisAtacante.toString());
        }
        if (paisDefensor.esAliado(paisAtacante)) {
            throw new AtaqueAPaisPropioException("No podes atacar a un pais propio");
        }
        turnoAtaque.cambiarEstado(new PaisDefensorSeleccionado(turnoAtaque, jugador, paisAtacante, paisDefensor));
    }

    @Override
    public void atacar(int cantidadEjercitos) {
        throw new AtaqueInvalidoException("Pais defensor no seleccionado.");
    }

    @Override
    public void cancelarAccion() {
        turnoAtaque.cambiarEstado(new NingunPaisSeleccionadoAtaque(turnoAtaque, jugador));
    }

}