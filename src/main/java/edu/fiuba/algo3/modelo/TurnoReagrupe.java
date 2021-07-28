package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.excepciones.*;

import java.util.ArrayList;
import java.util.Hashtable;

public class TurnoReagrupe implements EstadoTurno {

    private final Jugador jugador;
    private Pais paisOrigen;
    private Pais paisDestino;
    private Hashtable<Pais, Integer> datosRefuerzo;

    public TurnoReagrupe(Jugador jugador) {
        this.jugador = jugador;
        this.datosRefuerzo = new Hashtable<Pais, Integer>();
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
    public Pais seleccionarPais(Pais paisSeleccionado) {
        if (this.paisOrigen != null && this.paisDestino != null) {
            throw new PaisesYaSeleccionadosException("Los paises origen y destino ya estan seleccionados, apreta 'Reagrupar' o 'Cancelar accion'.");
        }

        if (this.paisOrigen == null) {
            if (!paisSeleccionado.esDuenio(jugador)) {
                throw new SeleccionaPaisAjenoException("El pais: " + paisSeleccionado + " no te pertenece");
            }
            if (!paisSeleccionado.puedeAtacar()) {    // simplemente checkea que la cantEjercitos del pais sea > 1
                throw new EjercitosInvalidosException("El pais: " + paisSeleccionado + " no tiene ejercitos suficientes para atacar");
            }
            paisOrigen = paisSeleccionado;
            return paisOrigen;
        }
        if (!paisSeleccionado.esLimitrofe(paisOrigen)) {
            throw new ReagrupeAPaisNoLimitrofeException(paisSeleccionado.toString() + " no limita con " + paisOrigen.toString());
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
    public void reagrupar(int cantidadEjercitos) {
        this.paisPuedeReagrupar(cantidadEjercitos);
        this.paisOrigen.disminuirEjercitos(cantidadEjercitos);
        if (!(datosRefuerzo.containsKey(paisDestino))) {
            this.datosRefuerzo.put(paisDestino, 0);
        }
        this.datosRefuerzo.put(paisDestino, this.datosRefuerzo.get(paisDestino) + cantidadEjercitos);
        //this.paisDestino.reforzar(cantidadEjercitos);
        this.cancelarAccion();
    }

    @Override
    public void reforzar(int cantidadEjercitosAReforzar) {
        throw new RefuerzoInvalidoException("No es posible reforzar en un turno de ataque");
    }

    @Override
    public void canjearTarjetas(ArrayList<Tarjeta> tarjetasACanjear) {
        throw new CanjeNoPermitidoException("No se puede canjear en una ronda de ataque y reagrupe");
    }

    public void efectivizarReagrupe() {
        datosRefuerzo.forEach((pais, ejercitosAEfectivizar) -> {
            pais.reforzar(ejercitosAEfectivizar);
        });
    }

    public void activarTarjeta(Tarjeta tarjetaAActivar) {
        throw new ActivacionTarjetaInvalidaException("No podés activar la tarjeta en un turno de reagrupe.");
    }
}
