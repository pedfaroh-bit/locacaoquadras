package com.locacaoquadras.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Formatação em português sem depender dos dados de idioma da JVM.
public final class Formato {

    private Formato() {
    }

    // 1200 -> "R$ 1.200,00"
    public static String moeda(BigDecimal valor) {
        if (valor == null) {
            return "R$ 0,00";
        }
        String texto = valor.setScale(2, RoundingMode.HALF_UP).toPlainString();
        int ponto = texto.indexOf('.');
        String inteiro = texto.substring(0, ponto);
        String centavos = texto.substring(ponto + 1);

        boolean negativo = inteiro.startsWith("-");
        if (negativo) {
            inteiro = inteiro.substring(1);
        }
        StringBuilder milhar = new StringBuilder();
        for (int i = 0; i < inteiro.length(); i++) {
            if (i > 0 && (inteiro.length() - i) % 3 == 0) {
                milhar.append('.');
            }
            milhar.append(inteiro.charAt(i));
        }
        return (negativo ? "-" : "") + "R$ " + milhar + "," + centavos;
    }
}
