package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

public class RepositorioTituloDivida extends RepositorioGeral {
	@Override
	public Class<?> getClasseEntidade() {
        return TituloDivida.class;
    }
	public boolean incluir(TituloDivida tituloDivida) {
        return super.incluir(tituloDivida);
    }
	public boolean alterar(TituloDivida tituloDivida) {
        return super.alterar(tituloDivida);
    }
	public boolean excluir(int identificador) {
		return super.excluir(String.valueOf(identificador));
	}
	public TituloDivida buscar(int identificador) {
        return (TituloDivida) super.buscar(String.valueOf(identificador));
    }
}