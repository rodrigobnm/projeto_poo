package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;

import java.io.IOException;
import java.time.LocalDateTime;

public class MediatorOperacao {

    private final MediatorAcao mediatorAcao = MediatorAcao.getInstance();
    private final MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getInstance();
    private final MediatorEntidadeOperadora mediatorEntidadeOperadora = MediatorEntidadeOperadora.getInstance();
    private final RepositorioTransacao repositorioTransacao = new RepositorioTransacao();

    private static MediatorOperacao instancia;

    private MediatorOperacao() {}

    public static MediatorOperacao getInstance() {
        if (instancia == null) {
            instancia = new MediatorOperacao();
        }
        return instancia;
    }

    public String realizarOperacao(boolean ehAcao, int entidadeCreditoId, int entidadeDebitoId, int idAcaoOuTitulo, double valor) throws IOException {
        if (valor <= 0) {
            return "Valor inválido";
        }

        EntidadeOperadora entidadeCredito = mediatorEntidadeOperadora.buscar(entidadeCreditoId);
        if (entidadeCredito == null) {
            return "Entidade crédito inexistente";
        }

        EntidadeOperadora entidadeDebito = mediatorEntidadeOperadora.buscar(entidadeDebitoId);
        if (entidadeDebito == null) {
            return "Entidade débito inexistente";
        }

        if (ehAcao && !entidadeCredito.isAutorizadoAcao()) {
            return "Entidade de crédito não autorizada para ação";
        }

        if (ehAcao && !entidadeDebito.isAutorizadoAcao()) {
            return "Entidade de débito não autorizada para ação";
        }

        Acao acao = null;
        TituloDivida tituloDivida = null;
        if (ehAcao) {
            acao = mediatorAcao.buscar(idAcaoOuTitulo);
            if (acao == null) {
                return "Ação inexistente";
            }
        } else {
            tituloDivida = mediatorTituloDivida.buscar(idAcaoOuTitulo);
            if (tituloDivida == null) {
                return "Título de dívida inexistente";
            }
        }

        if (ehAcao && entidadeDebito.getSaldoAcao() < valor) {
            return "Saldo da entidade débito insuficiente";
        } else if (!ehAcao && entidadeDebito.getSaldoTituloDivida() < valor) {
            return "Saldo da entidade débito insuficiente";
        }

        if (ehAcao && acao.getValorUnitario() > valor) {
            return "Valor da operação é menor do que o valor unitário da ação";
        }

        double valorOperacao;
        if (ehAcao) {
            valorOperacao = valor;
        } else {
            valorOperacao = tituloDivida.calcularPrecoTransacao(valor);
        }

        if (ehAcao) {
            entidadeCredito.creditarSaldoAcao(valorOperacao);
        } else {
            entidadeCredito.creditarSaldoTituloDivida(valorOperacao);
        }

        if (ehAcao) {
            entidadeDebito.debitarSaldoAcao(valorOperacao);
        } else {
            entidadeDebito.debitarSaldoTituloDivida(valorOperacao);
        }

        String respostaAlterarCredito = mediatorEntidadeOperadora.alterar(entidadeCredito);
        if (respostaAlterarCredito != null) {
            return respostaAlterarCredito;
        }
        String respostaAlterarDebito = mediatorEntidadeOperadora.alterar(entidadeDebito);
        if (respostaAlterarDebito != null) {
            return respostaAlterarDebito;
        }
        Transacao transacao = new Transacao(entidadeCredito, entidadeDebito, acao, tituloDivida, valorOperacao, LocalDateTime.now());
        repositorioTransacao.incluir(transacao);

        return "Operação realizada com sucesso";
    }
}