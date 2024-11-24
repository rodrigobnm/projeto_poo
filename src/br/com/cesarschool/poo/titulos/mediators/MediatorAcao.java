package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioAcao;

import java.io.IOException;
import java.time.LocalDate;

public class MediatorAcao {
    private RepositorioAcao repositorioAcao = new RepositorioAcao();
    private static MediatorAcao instancia;

    private MediatorAcao() {
        repositorioAcao = new RepositorioAcao();
    }

    public static MediatorAcao getInstance() {
        if (instancia == null) {
            instancia = new MediatorAcao();
        }
        return instancia;
    }

    private String validar(Acao acao) {
        if (acao.getIdentificador() <= 0 || acao.getIdentificador() >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }

        if (acao.getNome() == null || acao.getNome().trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }
        if (acao.getNome().length() < 10 || acao.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }

        if (acao.getDataDeValidade().isBefore(LocalDate.now().plusDays(30))) {
            return "Data de validade deve ser maior do que a data atual mais 30 dias.";
        }

        if (acao.getValorUnitario() <= 0) {
            return "Valor unitário deve ser maior que zero.";
        }
        return null;
    }

    public String incluir(Acao acao) throws IOException {
        String validacao = validar(acao);

        if (validacao != null) {
            return validacao;
        }
        boolean incluido = repositorioAcao.incluir(acao);
        if (incluido) {
            return null;
        } else {
            return "Ação já existente";
        }
    }

    public String alterar(Acao acao) throws IOException {
        String validacao = validar(acao);

        if (validacao != null) {
            return validacao;
        }

        boolean alterado = repositorioAcao.alterar(acao);

        if (alterado) {
            return null;
        } else {
            return "Ação inexistente";
        }
    }

    public String excluir(int identificador) throws IOException {
        if (identificador <= 0 || identificador >= 100000) {
            return "Ação inexistente";
        }

        boolean excluido = repositorioAcao.excluir(identificador);

        if (excluido) {
            return null;
        } else {
            return "Ação inexistente";
        }
    }

    public Acao buscar(int identificador) throws IOException {
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioAcao.buscar(identificador); 
    }
}