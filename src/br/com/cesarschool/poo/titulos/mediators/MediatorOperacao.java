package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDateTime;
import java.util.Arrays;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;

public class MediatorOperacao {
    private final MediatorAcao mediatorAcao = MediatorAcao.getInstanciaSingleton();
    private final MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getInstanciaSingleton();
    private final MediatorEntidadeOperadora mediatorEntidadeOperadora = MediatorEntidadeOperadora.getInstanciaSingleton();
    private final RepositorioTransacao repositorioTransacao = new RepositorioTransacao();
    private static MediatorOperacao instancia;

    private MediatorOperacao() { }

    public static MediatorOperacao getInstancia() {
        if (instancia == null) {
            synchronized (MediatorOperacao.class) {
                if (instancia == null) {
                    instancia = new MediatorOperacao();
                }
            }
        }
        return instancia;
    }

    public String realizarOperacao(boolean ehAcao, int entidadeCredito, int idEntidadeDebito, int idAcaoOuTitulo, double valor) {
        if (valor <= 0) {
            return "Valor inválido";
        }
        EntidadeOperadora credito = mediatorEntidadeOperadora.buscar(entidadeCredito);
        if (credito == null) {
            return "Entidade crédito inexistente";
        }
        EntidadeOperadora debito = mediatorEntidadeOperadora.buscar(idEntidadeDebito);
        if (debito == null) {
            return "Entidade débito inexistente";
        }
        if (ehAcao && !credito.getAutorizadoAcao()) {
            return "Entidade de crédito não autorizada para ação";
        }
        if (ehAcao && !debito.getAutorizadoAcao()) {
            return "Entidade de débito não autorizada para ação";
        }
        Acao acao = null;
        TituloDivida titulo = null;
        if (ehAcao) {
            acao = mediatorAcao.buscar(idAcaoOuTitulo);
            if (acao == null) {
                return "Ação inexistente";
            }
        } else {
            titulo = mediatorTituloDivida.buscar(idAcaoOuTitulo);
            if (titulo == null) {
                return "Título inexistente";
            }
        }
        if (ehAcao) {
            if (debito.getSaldoAcao() < valor) {
                return "Saldo da entidade débito insuficiente";
            }
        } else {
            if (debito.getSaldoTituloDivida() < valor) {
                return "Saldo da entidade débito insuficiente";
            }
        }
        if (ehAcao && acao.getValorUnitario() > valor) {
            return "Valor da operação é menor do que o valor unitário da ação";
        }
        double valorOperacao = ehAcao ? valor : titulo.calcularPrecoTransacao(valor);
        if (ehAcao) {
            credito.creditarSaldoAcao(valorOperacao);
        } else {
            credito.creditarSaldoTituloDivida(valorOperacao);
        }
        if (ehAcao) {
            debito.debitarSaldoAcao(valorOperacao);
        } else {
            debito.debitarSaldoTituloDivida(valorOperacao);
        }
        String resultadoCredito = mediatorEntidadeOperadora.alterar(credito);
        if (resultadoCredito != null) {
            return resultadoCredito;
        }
        String resultadoDebito = mediatorEntidadeOperadora.alterar(debito);
        if (resultadoDebito != null) {
            return resultadoDebito;
        }
        Transacao transacao = new Transacao(
            credito, debito, ehAcao ? acao : null, ehAcao ? null : titulo,
            valorOperacao, LocalDateTime.now()
        );
        repositorioTransacao.incluir(transacao);
        return "Operação realizada com sucesso";
    }

    public Transacao[] gerarExtrato(int entidade) {
        Transacao[] transacoesCredoras = repositorioTransacao.buscarPorEntidadeCredora(entidade);
        Transacao[] transacoesDevedoras = repositorioTransacao.buscarPorEntidadeDevedora(entidade);
        Transacao[] todasTransacoes = new Transacao[transacoesCredoras.length + transacoesDevedoras.length];
        System.arraycopy(transacoesCredoras, 0, todasTransacoes, 0, transacoesCredoras.length);
        System.arraycopy(transacoesDevedoras, 0, todasTransacoes, transacoesCredoras.length, transacoesDevedoras.length);
        Arrays.sort(todasTransacoes, (t1, t2) -> t2.getDataHoraOperacao().compareTo(t1.getDataHoraOperacao()));
        return todasTransacoes;
    }
}