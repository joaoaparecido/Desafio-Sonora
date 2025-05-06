package com.desafio.produtos.domain;

public enum Uf {
    AC, AL, AP, AM, BA, CE, DF, ES, GO, MA, MT, MS, MG, PA,
    PB, PR, PE, PI, RJ, RN, RS, RO, RR, SC, SP, SE, TO;

    public static boolean isValid(String uf) {
        if (uf == null) {
            return false;
        }
        try {
            Uf.valueOf(uf.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
