package br.com.cesarschool.poo.titulos.entidades;

import java.time.LocalDate;

/*
 * Esta classe herda de Ativo.
 * Possui o atributo taxaJuros, do tipo double.
 * 
 * Contém um construtor público que inicializa os atributos, 
 * além de métodos set/get públicos para os atributos.
 * 
 * Inclui um método público calcularPrecoTransacao(double montante): 
 * o preço da transação é o montante vezes (1 - taxaJuros/100.0).
 */

public class TituloDivida extends Ativo {
	private static final long serialVersionUID = 1L;	
	private double taxaJuros;

    public TituloDivida(int identificador, String nome, LocalDate datadevalidade, double taxaJuros) {
        super(identificador, nome, datadevalidade);
        this.taxaJuros = taxaJuros;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public double calcularPrecoTransacao(double montante) {
        return montante * (1 - taxaJuros / 100.0);
    }
}