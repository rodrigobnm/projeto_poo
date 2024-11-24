package br.com.cesarschool.poo.titulos.relatorios;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;
import br.com.cesarschool.poo.titulos.utils.ComparadorTransacaoPorNomeCredora;
import br.com.cesarschool.poo.titulos.utils.Ordenador;
import br.com.cesarschool.poo.titulos.utils.ComparadorTransacaoPorDataHora;
import br.gov.cesarschool.poo.daogenerico.Entidade;

public class RelatorioTransacaoBroker {
    private RepositorioTransacao repositorioTransacao = new RepositorioTransacao();

    public Transacao[] relatorioTransacaoPorNomeEntidadeCredora() {
        Transacao[] transacoes = obterTransacoesDoRepositorio();
        ComparadorTransacaoPorNomeCredora comparador = new ComparadorTransacaoPorNomeCredora();
        Ordenador.ordenar(transacoes, comparador);
        return transacoes;
    }

    public Transacao[] relatorioTransacaoPorDataHora() {
        Transacao[] transacoes = obterTransacoesDoRepositorio();
        ComparadorTransacaoPorDataHora comparador = new ComparadorTransacaoPorDataHora();
        Ordenador.ordenar(transacoes, comparador);
        return transacoes;
    }

    private Transacao[] obterTransacoesDoRepositorio() {
        Entidade[] entidades = repositorioTransacao.buscarTodos();
        return converterParaTransacoes(entidades);
    }

    private Transacao[] converterParaTransacoes(Entidade[] entidades) {
        return java.util.Arrays.stream(entidades)
                .filter(Transacao.class::isInstance)
                .map(Transacao.class::cast)
                .toArray(Transacao[]::new);
    }
}