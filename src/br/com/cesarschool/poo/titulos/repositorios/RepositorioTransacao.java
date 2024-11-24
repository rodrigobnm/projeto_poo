package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Transacao;

public class RepositorioTransacao extends RepositorioGeral {

    public boolean incluir(Transacao transacao) {
        return this.dao.incluir(transacao);
    }
    public Class<?> getClasseEntidade() {
        return Transacao.class;
    }
}
