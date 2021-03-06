package edu.fiuba.algo3.modelo;

import java.util.ArrayList;

public abstract class Ronda {

    private Juego juego;
    private EstadoTurno estadoTurno;

    public Ronda(Juego juego, EstadoTurno estadoTurno) {
        this.juego = juego;
        this.estadoTurno = estadoTurno;
    }

    public void atacar(int cantidadEjercitos) {
        this.estadoTurno.atacar(cantidadEjercitos);
    }

    public void reagrupar(int cantidadEjercitos) {
        this.estadoTurno.reagrupar(cantidadEjercitos);
    }

    public void reforzar(Integer ejercitosAReforzar) {
        this.estadoTurno.reforzar(ejercitosAReforzar);
    }

    public void seleccionarPais(String nombrePais)  {
        Pais pais = this.juego.seleccionarPais(nombrePais);
        this.estadoTurno.seleccionarPais(pais);
    }

    public void cancelarAccion() {
        this.estadoTurno.cancelarAccion();
    }

    public void canjearTarjetas(ArrayList<String> tarjetasACanjear) {
        estadoTurno.canjearTarjetas(tarjetasACanjear, juego);
    }

    abstract Ronda siguienteRonda();

    abstract void empezarTurno(Jugador jugador);

    protected EstadoTurno getEstadoTurno() {
        return estadoTurno;
    }

    protected void setEstadoTurno(EstadoTurno estadoTurno) {
        this.estadoTurno = estadoTurno;
    }

    abstract void terminarAtaque(Jugador jugador);

    public void activarTarjeta(String nombreTarjeta) {
        this.estadoTurno.activarTarjeta(nombreTarjeta, juego);
    }

    public boolean puedoAtacar() {
        return this.estadoTurno.puedoAtacar();
    }

    public int getEjercitosParaAtacar() {
        return this.estadoTurno.getEjercitosParaAtacar();
    }

    public boolean puedoReforzar() {
        return this.estadoTurno.puedoReforzar();
    }

    public int getEjercitosParaReforzar() {
        return this.estadoTurno.getEjercitosParaReforzar();
    }

    public boolean puedoCancelar() {
        return this.estadoTurno.puedoCancelar();
    }

    public boolean estoyEnTurnoAtaque() {
        return this.estadoTurno.estoyEnTurnoAtaque();
    }

    public boolean puedoPasarDeTurno() {
        return this.estadoTurno.puedoPasarDeTurno();
    }

    public boolean puedoReagrupar() {
        return this.estadoTurno.puedoReagrupar();
    }

    public boolean paisPuedeSeleccionarse(String nombrePais) {
        Pais pais = juego.seleccionarPais(nombrePais);
        return this.estadoTurno.puedoSeleccionarPais(pais);
    }

    public boolean puedoActivarTarjeta(String nombreTarjeta) {
        return this.estadoTurno.puedoActivarTarjeta(nombreTarjeta);
    }

    public boolean puedoCanjearTarjeta() {
        return this.estadoTurno.puedoCanjearTarjeta();
    }

    public boolean paisSeleccionado(String nombrePais) {
        return this.estadoTurno.paisSeleccionado(nombrePais);
    }
}
