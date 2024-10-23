package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioAcao;

public class MediatorAcao {
    private static MediatorAcao instanciaSingleton = new MediatorAcao();
    private RepositorioAcao repositorioAcao = new RepositorioAcao();

    private MediatorAcao() { }
    
    public static MediatorAcao getInstanciaSingleton() {
        return instanciaSingleton;
    }
    
    private String validar(Acao acao) {
    	if (acao.getIdentificador() <= 0 || acao.getIdentificador() >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        if (acao.getNome() == null || acao.getNome().strip().isEmpty()) {
            return "Nome deve ser preenchido.";
        }
        if (acao.getNome().length() < 10 || acao.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        LocalDate dataAtual = LocalDate.now();
        if (acao.getDataDeValidade().isBefore(dataAtual.plusDays(30))) {
            return "Data de validade deve ter pelo menos 30 dias na frente da data atual.";
        }
        if (acao.getValorUnitario() <= 0) {
            return "Valor unitário deve ser maior que zero.";
        }
		return null;
    }
    
    public String incluir(Acao acao) {
        String retornoValidacao = validar(acao);   
        if (retornoValidacao == null) {
            boolean resultado = repositorioAcao.incluir(acao);
            if (resultado == true) {
                return null;
            } else {
                return "Ação já existente";
            }
        } else {
            return retornoValidacao;
        }
    }
    
    public String alterar(Acao acao) {
    	String retornoValidacao = validar(acao);   
        if (retornoValidacao == null) {
        	boolean resultado = repositorioAcao.alterar(acao);
        	if (resultado == true) {
                return null;
            } else {
                return "Ação inexistente";
            }
        } else {
        	return retornoValidacao;
        }
    }
    
    public String excluir(int identificador) {
        if (identificador < 1 || identificador >= 100000) {
            return "Ação inexistente";
        }        
        boolean resultado = repositorioAcao.excluir(identificador);
        if (resultado == true) {
            return null;
        } else {
            return "Ação inexistente";
        }
    }
    
    public Acao buscar(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioAcao.buscar(identificador);
    }
}