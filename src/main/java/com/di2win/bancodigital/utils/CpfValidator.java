package com.di2win.bancodigital.utils;

import java.util.Arrays;

public class CpfValidator {
    public static boolean isCPF(String CPF) {
        if (CPF == null) {
            return false;
        }
        CPF = CPF.replaceAll("[.-]", "");
        if (isInvalid(CPF)) {
            return false;
        }

        try {
            char dig10 = calculaDigito(CPF, 9, 10);
            char dig11 = calculaDigito(CPF, 10, 11);

            return dig10 == CPF.charAt(9) && dig11 == CPF.charAt(10);
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isInvalid(String cpf) {
        if (cpf.length() != 11) {
            return true;
        }

        String[] invalidCPFs = {
                "00000000000", "11111111111", "22222222222",
                "33333333333", "44444444444", "55555555555",
                "66666666666", "77777777777", "88888888888", "99999999999"
        };

        return Arrays.asList(invalidCPFs).contains(cpf);
    }

    private static char calculaDigito(String cpf, int length, int peso) {
        int sum = 0;
        for (int i = 0; i < length; i++) {
            int num = cpf.charAt(i) - '0';
            sum += num * peso--;
        }

        int remainder = 11 - (sum % 11);
        if (remainder >= 10) {
            return '0';
        }

        return (char) (remainder + '0');
    }
}