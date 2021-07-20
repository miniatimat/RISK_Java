package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.excepciones.SeleccionaPaisAjenoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class TestRondaDeRefuerzo {

    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugador3;
    private Pais argentina;
    private Pais uruguay;
    private Pais china;
    private Pais chile;
    private RondaDeAtaqueYReagrupe ronda;

    @BeforeEach
    void setUp() {
        this.jugador1 = new Jugador("000000");
        this.jugador2 = new Jugador("ffffff");
        this.jugador3 = new Jugador("fff000");
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(jugador1);
        listaJugadores.add(jugador2);
        listaJugadores.add(jugador3);

        this.argentina = new Pais("Argentina",jugador1);
        this.uruguay = new Pais("Uruguay",jugador2);
        this.china = new Pais("China",jugador3);
        this.chile = new Pais("Chile", jugador1);
        argentina.hacerLimitrofe(uruguay);
        uruguay.hacerLimitrofe(argentina);
        argentina.hacerLimitrofe(chile);
        chile.hacerLimitrofe(argentina);
        argentina.reforzar(3);
        ArrayList<Pais> paises = new ArrayList<>();
        paises.add(argentina);
        paises.add(uruguay);
        paises.add(china);
        paises.add(chile);

        Tablero tablero = new Tablero(paises);
        this.ronda = new RondaDeAtaqueYReagrupe(tablero);
    }

    @Test
    public void test01CreoUnaRondaDeRefuerzoYNoEsNull () {
        assertNotNull(ronda);
    }

    @Test
    public void test02CreoUnaRondaDeRefuerzoYNoPuedoSeleccionarUnPaisAjeno() {
        assertThrows(SeleccionaPaisAjenoException.class, () -> ronda.seleccionarPais("Uruguay", jugador1));
    }

    @Test
    public void test03CreoUnaRondaDeRefuerzoYPuedoSeleccionarUnPaisPropio() {
        Pais paisSeleccionado = ronda.seleccionarPais("Argentina", jugador1);

        assertEquals(argentina, paisSeleccionado);
    }

}
