package br.com.cesarschool.poo.titulos.mediators;

import java.io.IOException;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;

public class MediatorEntidadeOperadora {
    private static MediatorEntidadeOperadora instance = new MediatorEntidadeOperadora(); 
    private RepositorioEntidadeOperadora repositorioEntidadeOperadora = new RepositorioEntidadeOperadora();

    public static MediatorEntidadeOperadora getInstance() {
        return instance;
    }

    private String validar(EntidadeOperadora entidadeOperadora) {
        if (entidadeOperadora.getIdentificador() <= 100 || entidadeOperadora.getIdentificador() >= 1000000) {
            return "Identificador deve estar entre 100 e 1000000.";
        }
        if (entidadeOperadora.getNome() == null || entidadeOperadora.getNome().trim().isEmpty()) {
            return "Nome deve ser preenchido.";
        }
        if (entidadeOperadora.getNome().length() < 10 || entidadeOperadora.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        if (entidadeOperadora.isAutorizadoAcao() == false) {
            return "A ação deve ser autorizada";
        }
        if (entidadeOperadora.getSaldoAcao() < 0) {
            return "Erro o saldo da ação deve ser maior que ou igual a 0";
        }
        if (entidadeOperadora.getSaldoTituloDivida() < 0) {
            return "Erro o saldo do titulo da divida deve ser maior que ou igual a 0";
        }
        return null;
    }

    public String incluir(EntidadeOperadora entidade) throws IOException {
        String validacao = validar(entidade);
        if (validacao != null) {
            return validacao;
        }
        boolean incluido = repositorioEntidadeOperadora.incluir(entidade);
        if (incluido) {
            return null;
        } else {
            return "Entidade já existente.";
        }
    }

    public String alterar(EntidadeOperadora entidade) throws IOException {
        String validacao = validar(entidade);
        if (validacao != null) {
            return validacao;
        }
        boolean alterado = repositorioEntidadeOperadora.alterar(entidade);
        if (alterado) {
            return null; 
        } else {
            return "Entidade inexistente.";
        }
    }

    public String excluir(int identificador) throws IOException {
        if (identificador <= 100 || identificador >= 1000000) {
            return "Identificador inválido.";
        }
        boolean excluido = repositorioEntidadeOperadora.excluir(identificador);
        if (excluido) {
            return null;
        } else {
            return "Entidade inexistente.";
        }
    }

    public EntidadeOperadora buscar(int identificador) throws IOException {
        if (identificador <= 100 || identificador >= 1000000) {
            return null;
        }
        return repositorioEntidadeOperadora.buscar(identificador);
    }
}