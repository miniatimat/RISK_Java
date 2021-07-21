package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.excepciones.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TestSistemaDeTurnos {
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugador3;
    private Pais argentina;
    private Pais uruguay;
    private Pais china;
    private ArrayList<Tarjeta> tarjetas;
    private SistemaDeTurnos sistema;

    @BeforeEach
    void setUp() {
        this.jugador1 = new Jugador("000000");
        this.jugador2 = new Jugador("ffffff");
        this.jugador3 = new Jugador("fff000");
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(jugador1);
        listaJugadores.add(jugador2);
        listaJugadores.add(jugador3);

        Queue<Integer> cola = new LinkedList<>();
        cola.add(5);    // Primera Ronda Inicial dejara a cada jugador poner 5 ejercitos en sus paises.
        cola.add(3);    // Segunda Ronda Inicial dejara a cada jugador poner 3 ejercitos en sus paises. Luego de esta Ronda, comenzara la Fase De Juego.

        this.argentina = new Pais("Argentina", jugador1);
        this.uruguay = new Pais("Uruguay", jugador2);
        this.china = new Pais("China", jugador3);
        argentina.hacerLimitrofe(uruguay);
        uruguay.hacerLimitrofe(argentina);
        ArrayList<Pais> paises = new ArrayList<>();
        paises.add(argentina);
        paises.add(uruguay);
        paises.add(china);

        Tarjeta tarjeta1 = new Tarjeta(argentina, "Globo");
        Tarjeta tarjeta2 = new Tarjeta(uruguay, "Canion");
        Tarjeta tarjeta3 = new Tarjeta(china, "Barco");
        this.jugador1.agregarTarjeta(tarjeta1);
        this.jugador1.agregarTarjeta(tarjeta2);
        this.jugador1.agregarTarjeta(tarjeta3);
        this.tarjetas = new ArrayList<>();
        this.tarjetas.add(tarjeta1);
        this.tarjetas.add(tarjeta2);
        this.tarjetas.add(tarjeta3);

        Tablero tablero = new Tablero(paises);
        this.sistema = new SistemaDeTurnos(listaJugadores, tablero, cola);
    }

    @Test
    public void test01CreoUnSistemaDeTurnosYNoEsNull() {
        assertNotNull(sistema);
    }

    @Test
    public void test02CreoUnSistemaDeTurnosYComienzaElJugadorCorrecto() {
        assertEquals(jugador1, sistema.turnoDe());
    }

    @Test
    public void test03CreoUnSistemaDeTurnosYJugadorNoPuedeSeleccionarUnPaisAjeno() {
        assertThrows(SeleccionaPaisAjenoException.class, () -> sistema.seleccionarPais("Uruguay"));
    }

    @Test
    public void test04CreoUnSistemaDeTurnosYJugadorPuedeSeleccionarUnPaisPropio() {
        Pais unPais = sistema.seleccionarPais("Argentina");

        assertEquals(unPais, argentina);
    }

    @Test
    public void test05CreoUnSistemaDeTurnosYPasoDeTurnoYJugador2NoPuedeSeleccionarPaisDeJugador1() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(5);
        sistema.siguienteTurno();

        assertThrows(SeleccionaPaisAjenoException.class, () -> sistema.seleccionarPais("Argentina"));
    }

    @Test
    public void test05CreoUnSistemaDeTurnosYCambiaElTurnoCorrectamente() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(5);
        sistema.siguienteTurno();

        assertEquals(jugador2, sistema.turnoDe());
    }

    @Test
    public void test04CreoUnSistemaDeTurnosYHagoUnaRondaCompletaYVuelveAlPrimerJugador() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("Uruguay");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("China");
        sistema.reforzar(5);
        sistema.siguienteTurno();

        assertEquals(jugador1, sistema.turnoDe());
    }

    @Test
    public void test05CreoUnSistemaDeTurnosYJugadorColocanCincoEjercitosCorrectamente() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(5);

        assertEquals(6, argentina.getEjercitos());
    }

    @Test
    public void test06CreoUnSistemaDeTurnosYJugadorNoPuedeColocarMasDeCincoFichas() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(2);

        assertThrows(NoPuedeColocarTantosEjercitosException.class, () -> sistema.reforzar(4));

    }

    @Test
    public void test07CreoUnSistemaDeTurnosYJugadoresColocanCincoFichasCorrectamente() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("Uruguay");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("China");
        sistema.reforzar(5);

        int cantidadEjercitosTotales = 0;
        cantidadEjercitosTotales += argentina.getEjercitos();
        cantidadEjercitosTotales += uruguay.getEjercitos();
        cantidadEjercitosTotales += china.getEjercitos();

        assertEquals(18, cantidadEjercitosTotales);
    }

    @Test
    public void test07CreoUnSistemaDeTurnosYJugadoresColocanCincoYDespuesNoPuedenColocarCinco() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("Uruguay");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("China");
        sistema.reforzar(5);
        sistema.siguienteTurno();

        assertThrows(NoPuedeColocarTantosEjercitosException.class, () -> sistema.reforzar(5));
    }

    @Test
    public void test08SeColocanTodosLosEjercitosEnLaFaseIncialCorrectamente() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("Uruguay");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("China");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(3);
        sistema.siguienteTurno();
        sistema.seleccionarPais("Uruguay");
        sistema.reforzar(3);
        sistema.siguienteTurno();
        sistema.seleccionarPais("China");
        sistema.reforzar(3);

        int cantidadEjercitosTotales = 0;
        cantidadEjercitosTotales += argentina.getEjercitos();
        cantidadEjercitosTotales += uruguay.getEjercitos();
        cantidadEjercitosTotales += china.getEjercitos();

        assertEquals(27, cantidadEjercitosTotales);
    }

    @Test
    public void test09SeColocanTodosLosEjercitosEnLaFaseIncialCorrectamenteYComienzaLaFaseDeJuego() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("Uruguay");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("China");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(3);
        sistema.siguienteTurno();
        sistema.seleccionarPais("Uruguay");
        sistema.reforzar(3);
        sistema.siguienteTurno();
        sistema.seleccionarPais("China");
        sistema.reforzar(3);
        sistema.siguienteTurno();

        assertEquals("Fase de juego", sistema.getFaseActual());
    }

    @Test
    public void test10CreoUnSistemaDeTurnosYJugadorNoPuedeColocarMenosDeCincoEjercitos() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(2);

        assertThrows(NoReforzoTodosLosEjercitosException.class, sistema::siguienteTurno);
    }

    @Test
    public void test11CreoUnSistemaDeTurnosYNoPuedoAtacar() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(2);

        assertThrows(AtaqueInvalidoException.class, () -> sistema.atacar(1));
    }

    @Test
    public void test12CreoUnSistemaDeTurnosYNoPuedoReagrupar() {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(2);
        sistema.seleccionarPais("Argentina");

        assertThrows(ReagrupeInvalidoException.class, () -> sistema.reagrupar(3));
    }

    @Test
    public void test13PuedoObtenerLasTarjetasDelJugador1 () {
        assertEquals(tarjetas, sistema.obtenerTarjetas());
    }

    @Test
    public void test14PasoDeTurnoYNoPuedoObtenerLasTarjetasDelJugador1 () {
        sistema.seleccionarPais("Argentina");
        sistema.reforzar(5);
        sistema.siguienteTurno();
        assertNotEquals(tarjetas, sistema.obtenerTarjetas());
    }
}