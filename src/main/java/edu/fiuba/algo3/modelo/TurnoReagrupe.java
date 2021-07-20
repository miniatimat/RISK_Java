package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.excepciones.*;

public class TurnoReagrupe implements EstadoTurno {

    private final Tablero tablero;
    private Pais paisOrigen;
    private Pais paisDestino;

    public TurnoReagrupe(Tablero tablero) {
        this.tablero = tablero;
    }

    public EstadoTurno cambiarEstado() {
        return new TurnoAtaque(tablero);
    }

    @Override
    public void atacar(int cantidadEjercitosAtacantes) {
        throw new AtaqueInvalidoException("No es posible atacar en un turno de reagrupe");
    }

    private void paisPuedeReagrupar(int cantidadEjercitosAMovilizar) {
        if ((this.paisOrigen == null) || (this.paisDestino == null)) {
            throw new AtaqueInvalidoException("Pais origen o destino faltante");
        }
        if (cantidadEjercitosAMovilizar < 1) {
            throw new EjercitosInvalidosException("Cantidad de ejércitos a realocar invalida");
        }
        if (cantidadEjercitosAMovilizar > (paisOrigen.getEjercitos() - 1)) {
            throw new EjercitosInvalidosException("No hay suficientes ejércitos para realocar");
        }
    }

    @Override
    public Pais seleccionarPais(String nombrePais, Jugador jugador) {
        if (this.paisOrigen != null && this.paisDestino != null) {
            throw new PaisesYaSeleccionadosException("Los paises origen y destino ya estan seleccionados, apreta 'Reagrupar' o 'Cancelar accion'.");
        }
        Pais paisSeleccionado = this.tablero.seleccionarPais(nombrePais);
        if (this.paisOrigen == null) {
            if (!paisSeleccionado.esDuenio(jugador)) {
                throw new SeleccionaPaisAjenoException("El pais: " + nombrePais + " no te pertenece");
            }
            if (!paisSeleccionado.puedeAtacar()) {    // simplemente checkea que la cantEjercitos del pais sea > 1
                throw new EjercitosInvalidosException("El pais: " + nombrePais + " no tiene ejercitos suficientes para atacar");
            }
            paisOrigen = paisSeleccionado;
            return paisOrigen;
        }
        if (!paisSeleccionado.esAliado(paisOrigen)) {
            throw new ReagrupeAPaisAjenoException("No podes reagrupar con un pais ajeno como destino");
        }
        this.paisDestino = paisSeleccionado;
        return paisSeleccionado;
    }

    @Override
    public void cancelarAccion() {
        paisOrigen = null;
        paisDestino = null;
    }

    @Override
    public void reagrupar(int cantidadEjercitos){
        this.paisPuedeReagrupar(cantidadEjercitos);
        this.paisOrigen.disminuirEjercitos(cantidadEjercitos);
        this.paisDestino.reforzar(cantidadEjercitos);
        this.cancelarAccion();
    }

    @Override
    public void reforzar(int cantidadEjercitosAReforzar) {
        throw new RefuerzoInvalidoException("No es posible reforzar en un turno de ataque");
    }
}