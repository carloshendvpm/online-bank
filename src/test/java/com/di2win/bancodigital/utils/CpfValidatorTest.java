package com.di2win.bancodigital.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CpfValidatorTest {

    @Test
    public void testValidCPF() {
        // CPF válido
        String cpf = "529.982.247-25";
        assertTrue(CpfValidator.isCPF(cpf));
    }

    @Test
    public void testInvalidCPF() {
        // CPF inválido
        String cpf = "123.456.789-10";
        assertFalse(CpfValidator.isCPF(cpf));
    }

    @Test
    public void testCPFAllSameNumbers() {
        // CPF inválido
        String cpf = "111.111.111-11";
        assertFalse(CpfValidator.isCPF(cpf));
    }

    @Test
    public void testCPFWithInvalidLength() {
        // CPF inválido
        String cpf = "123.456";
        assertFalse(CpfValidator.isCPF(cpf));
    }
}