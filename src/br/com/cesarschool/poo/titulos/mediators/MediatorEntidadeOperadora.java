package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;

public class MediatorEntidadeOperadora {
	private static MediatorEntidadeOperadora instanciaSingleton = new MediatorEntidadeOperadora();
    private final RepositorioEntidadeOperadora repositorioEntidadeOperadora = new RepositorioEntidadeOperadora();
    private MediatorEntidadeOperadora() { }
    public static MediatorEntidadeOperadora getInstanciaSingleton() {
        return instanciaSingleton;
    }
    private String validar(EntidadeOperadora entidadeOperadora) {
        if (entidadeOperadora.getIdentificador() <= 0 || entidadeOperadora.getIdentificador() >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        if (entidadeOperadora.getNome() == null || entidadeOperadora.getNome().strip().isEmpty()) {
            return "Nome deve ser preenchido.";
        }
        if (entidadeOperadora.getNome().length() < 10 || entidadeOperadora.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        if (entidadeOperadora.getAutorizadoAcao() == false) {
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
    public String incluir(EntidadeOperadora entidade) {
        String validacao = validar(entidade);
        if (validacao == null) {
            boolean retorno = repositorioEntidadeOperadora.incluir(entidade);
            return retorno ? null : "Entidade já existente";
        } else {
            return validacao;
        }
    }
    public String alterar(EntidadeOperadora entidade) {
        String validacao = validar(entidade);
        if (validacao == null) {
            boolean retorno = repositorioEntidadeOperadora.alterar(entidade);
            return retorno ? null : "Entidade inexistente";
        } else {
            return validacao;
        }
    }
    public String excluir(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return "Entidade inexistente";
        }
        boolean retorno = repositorioEntidadeOperadora.excluir(identificador);
        return retorno ? null : "Entidade inexistente";
    }
    public EntidadeOperadora buscar(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioEntidadeOperadora.buscar(identificador);
    }
}
