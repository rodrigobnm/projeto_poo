package br.com.cesarschool.poo.titulos.utils;

import br.com.cesarschool.poo.titulos.entidades.Transacao;

import java.time.LocalDateTime;

public class ComparadorTransacaoPorDataHora implements Comparador {
    @Override
    public int comparar(Comparavel c1, Comparavel c2) {
        Transacao t1 = (Transacao) c1;
        Transacao t2 = (Transacao) c2;
        LocalDateTime dataHora1 = t1.getDataHoraOperacao();
        LocalDateTime dataHora2 = t2.getDataHoraOperacao();
        return dataHora1.compareTo(dataHora2);
    }
}

