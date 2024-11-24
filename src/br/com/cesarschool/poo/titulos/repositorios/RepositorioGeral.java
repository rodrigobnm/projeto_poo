package br.com.cesarschool.poo.titulos.repositorios;

import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;
import br.gov.cesarschool.poo.daogenerico.Entidade;

public abstract class RepositorioGeral {
    protected DAOSerializadorObjetos dao;
    public RepositorioGeral() {
        this.dao = new DAOSerializadorObjetos(getClasseEntidade());
    }
    
    public abstract Class<?> getClasseEntidade();
    
    public DAOSerializadorObjetos getDao() {
        return this.dao;
    }
    public boolean incluir(Entidade entidade) {
        return dao.incluir(entidade);
    }
    public boolean alterar(Entidade entidade) {
        return dao.alterar(entidade);
    }
    public boolean excluir(String idUnico) {
        return dao.excluir(idUnico);
    }
    public Entidade buscar(String idUnico) {
        return dao.buscar(idUnico);
    }
    public Entidade[] buscarTodos() {
        return dao.buscarTodos();
    }
}