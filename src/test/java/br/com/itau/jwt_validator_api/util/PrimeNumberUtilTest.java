package br.com.itau.jwt_validator_api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimeNumberUtilTest {

    @Test
    void deveRetornarFalseParaNumerosMenoresOuIguaisA1() {
        assertFalse(PrimeNumberUtil.isPrime(-10));
        assertFalse(PrimeNumberUtil.isPrime(0));
        assertFalse(PrimeNumberUtil.isPrime(1));
    }

    @Test
    void deveRetornarTrueParaNumero2() {
        assertTrue(PrimeNumberUtil.isPrime(2));
    }

    @Test
    void deveRetornarFalseParaNumerosParesMaioresQue2() {
        assertFalse(PrimeNumberUtil.isPrime(4));
        assertFalse(PrimeNumberUtil.isPrime(100));
    }

    @Test
    void deveRetornarTrueParaNumerosPrimosImpares() {
        assertTrue(PrimeNumberUtil.isPrime(3));
        assertTrue(PrimeNumberUtil.isPrime(5));
        assertTrue(PrimeNumberUtil.isPrime(7));
        assertTrue(PrimeNumberUtil.isPrime(11));
        assertTrue(PrimeNumberUtil.isPrime(13));
        assertTrue(PrimeNumberUtil.isPrime(17));
        assertTrue(PrimeNumberUtil.isPrime(7841)); // caso de teste do Itaú
    }

    @Test
    void deveRetornarFalseParaNumerosNaoPrimosImpares() {
        assertFalse(PrimeNumberUtil.isPrime(9));
        assertFalse(PrimeNumberUtil.isPrime(15));
        assertFalse(PrimeNumberUtil.isPrime(21));
        assertFalse(PrimeNumberUtil.isPrime(25));
    }
}
