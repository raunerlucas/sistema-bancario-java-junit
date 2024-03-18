package com.sistemabancario.model;

import org.junit.jupiter.api.Test;

import java.nio.file.SecureDirectoryStream;

import static org.junit.jupiter.api.Assertions.*;

public class ContaTest {

    @Test
    void testSetNumeroValido_R01() {
        final Conta inst = new Conta();
        final String num = "12345-6";
        inst.setNumero(num);
        final String obt = inst.getNumero();
        assertEquals(num, obt);
    }
    @Test
    void testSetNumeroInvalido_R01() {
        final Conta inst = new Conta();
        final String inv = "123";
        assertThrows(IllegalArgumentException.class, () -> inst.setNumero(inv));
        final String obt = inst.getNumero();
        assertNotEquals(inv, obt);
    }

    @Test
    void testSetPoupanca_R02() {
        final Conta inst = new Conta();
        assertFalse(inst.isPoupanca());
    }

    @Test
    void testSetLimiteCEspecias_R03() {
        final Conta inst = new Conta();
        inst.setEspecial(true);
        final Double limite = 1000.0;
        inst.setLimite(limite);
        final Double obt = inst.getLimite();
        assertEquals(limite, obt);
    }

    @Test
    void testSetLimiteCNaoEspecias_R03() {
        final Conta inst = new Conta();
        final Double limite = 1000.0;
        assertThrows(IllegalArgumentException.class, () -> inst.setLimite(limite));
        final Double obt = inst.getLimite();
        assertNotEquals(limite, obt);
    }

    @Test
    void testHistoricoNotNull_R04(){
        final Conta inst = new Conta();
        assertNotNull(inst.getMovimentacoes());
    }

}
