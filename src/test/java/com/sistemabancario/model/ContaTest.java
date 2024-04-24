package com.sistemabancario.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    private Conta conta;

    @BeforeEach
    public void setUp() {
        conta = new Conta(100);
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
    void addMovimentacaoExistente_R05() {
        final var mov = new Movimentacao(conta);
        mov.setConfirmada(true);
        mov.setTipo('C');
        mov.setValor(50);
        conta.addMovimentacao(mov);
        assertThrows(IllegalArgumentException.class, () -> conta.addMovimentacao(mov));
    }

    @Test
    void addMovimentacaoCredito_R05() {
        final var mov = new Movimentacao(conta);
        mov.setConfirmada(true);
        mov.setTipo('C');
        final int valor = 50;
        final double esp = valor+ conta.getSaldoTotal();
        mov.setValor(valor);
        conta.addMovimentacao(mov);
        assertEquals(esp, conta.getSaldoTotal());
    }

    @Test
    void addMovimentacaoDebito_R05() {
        final var mov = new Movimentacao(conta);
        mov.setConfirmada(true);
        mov.setTipo('D');
        final int valor = 50;
        final double esp = conta.getSaldoTotal()-valor;
        mov.setValor(valor);
        conta.addMovimentacao(mov);
        assertEquals(esp, conta.getSaldoTotal());
    }

    @Test
    void addMovimentacaoDebitoMaiorSaldo_R05() {
        final var mov = new Movimentacao(conta);
        mov.setConfirmada(true);
        mov.setTipo('D');
        final int valor = 150;
        final double esp = conta.getSaldoTotal()-valor;
        mov.setValor(valor);
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> conta.addMovimentacao(mov)),
                () -> assertNotEquals(esp, conta.getSaldoTotal())
        );
    }

    @Test
    void addMovimentacaoNaoConfirmada_R05() {
        final var mov = new Movimentacao(conta);
        mov.setConfirmada(false);
        mov.setTipo('C');
        final int valor = 150;
        final double esp = conta.getSaldoTotal()+valor;
        mov.setValor(valor);
        conta.addMovimentacao(mov);
        assertNotEquals(esp, conta.getSaldoTotal());
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
    void saqueValido_R05() {
        final double esp = conta.getSaldoTotal()-50;
        conta.saque(50);
        assertEquals(esp, conta.getSaldoTotal());
    }

    @Test
    void saqueInvalido_R05() {
        final double esp = conta.getSaldoTotal()-150;
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> conta.saque(-150)),
                () -> assertThrows(IllegalArgumentException.class, () -> conta.saque(150)),
                () -> assertNotEquals(esp, conta.getSaldoTotal())
        );
    }

    @Test
    void testDepositoDinheiro_R08() {
        final double limite = 400.6, deposito = 500.8, esp = 1001.4;
        conta.setEspecial(true);
        conta.setLimite(limite);
        conta.depositoDinheiro(deposito);
        final double obt = conta.getSaldoTotal();
        assertEquals(esp, obt, 0.001);
    }

    @Test
    void testDepositoDinheiroInvalido_R08() {
        assertThrows(IllegalArgumentException.class, () -> conta.depositoDinheiro(-100));
    }

    @Test
    void testDepositoCheque_R09(){
        conta.depositoCheque(100);
        final double esp = 100 + conta.getSaldoTotal();
        assertNotEquals(esp, conta.getSaldoTotal());
    }

    @Test
    void testDepositoChequeInvalido_R09(){
        assertThrows(IllegalArgumentException.class, () -> conta.depositoCheque(-100));
    }
}
