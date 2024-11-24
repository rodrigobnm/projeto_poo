package br.com.cesarschool.poo.titulos.utils;

import br.com.cesarschool.poo.titulos.entidades.Transacao;

public class ComparadorTransacaoPorNomeCredora extends ComparadorPadrao implements Comparador {
    @Override
    public int comparar(Comparavel c1, Comparavel c2) {
        Transacao t1 = (Transacao) c1;
        Transacao t2 = (Transacao) c2;
        return t1.getEntidadeCredito().getNome().compareToIgnoreCase(t2.getEntidadeCredito().getNome());
    }
}