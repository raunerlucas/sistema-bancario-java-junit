package com.sistemabancario.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    private Conta conta;

    @BeforeEach
    public void setUp() {
        conta = new Conta();
    };

    @Test
    void setNumValido_R01() {
        final var valido = "02120-2";
        conta.setNumero(valido);
        assertEquals(valido, conta.getNumero());
    }

    @Test
    void setNumInv_R01() {
        final var inv = "abc";
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> conta.setNumero(inv)),
                () -> assertNotEquals(inv, conta.getNumero())
        );
    }

    @Test
    void poupancaFalse_R02() {
        assertFalse(conta.isPoupanca());
    }

    @Test
    void getSaldoTotal_R06() {
        int expected = 100;
        conta.setEspecial(true);
        conta.setLimite(expected);
        assertEquals(expected, conta.getSaldoTotal());
    }

    @Test
    void addAddMovimentacao_R07() {
        final var mov = new Movimentacao(conta);
        mov.setConfirmada(true);
        mov.setTipo('C');
        final int valor = 50;
        mov.setValor(valor);
        conta.addMovimentacao(mov);
        assertEquals(valor, conta.getSaldoTotal());
    }

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
    void testHistoricoNotNull_R04() {
        final Conta inst = new Conta();
        assertNotNull(inst.getMovimentacoes());
    }


    @Test
    void testGetSaldoTotal_R06() {
        final Conta inst = new Conta();
        inst.setEspecial(true);
        final double limete = 500.0;
        inst.setLimite(limete);
        final double obt = inst.getSaldoTotal();
        assertEquals(limete, obt);
    }

    @Test
    void testDepositoDinheiro_R08() {
        final Conta inst = new Conta();
        inst.setEspecial(true);
        final double limite = 500.6, deposito = 500.8, esp = 1001.4;
        inst.setLimite(limite);
        inst.depositoDinheiro(deposito);

        final double obt = inst.getSaldoTotal();
        assertEquals(esp, obt, 0.001);
    }

}
