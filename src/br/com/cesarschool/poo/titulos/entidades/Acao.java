package br.com.cesarschool.poo.titulos.entidades;

import java.time.LocalDate;

public class Acao extends Ativo {
	private double valorUnitario;

	public Acao(int identificador, String nome, LocalDate datadDeValidade, double valorUnitario) {
		super(identificador, nome, datadDeValidade);
		this.valorUnitario = valorUnitario;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public double calcularPrecoTransacao(double montante) {
		return montante * valorUnitario;
	}
}