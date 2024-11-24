package br.com.cesarschool.poo.titulos.entidades;

import java.time.LocalDate;

public class Acao extends Ativo {
    private static final long serialVersionUID = 1L;
    private double valorUnitario;

    public Acao(int identificador, String nome, LocalDate dataDeValidade, double valorUnitario) {
        super(identificador, nome, dataDeValidade);
        this.valorUnitario = valorUnitario;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double calcularPrecoTransacao(double montante) {
        if (montante < 0) {
            throw new IllegalArgumentException("Montante não pode ser negativo.");
        }
        return montante * valorUnitario;
    }

    @Override
    public String getIdUnico() {
        return String.valueOf(getIdentificador());
    }
}
