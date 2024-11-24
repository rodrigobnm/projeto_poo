package br.com.cesarschool.poo.titulos.entidades;

import br.gov.cesarschool.poo.daogenerico.Entidade;
public class EntidadeOperadora extends Entidade {
    private static final long serialVersionUID = 1L;
    
    private long identificador;
    private String nome;
    private boolean autorizadoAcao;
    private double saldoAcao;
    private double saldoTituloDivida;
	
    public EntidadeOperadora(long identificador, String nome, double autorizadoAcao) {
        super();
        this.identificador = identificador;
        this.nome = nome;
    }

    public long getIdentificador() {
        return identificador;
    }

    @Override
    public String getIdUnico() {
        return String.valueOf(identificador);
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAutorizadoAcao() {
        return autorizadoAcao;
    }

    public void setAutorizadoAcao(boolean autorizadoAcao) {
        this.autorizadoAcao = autorizadoAcao;
    }

    public double getSaldoAcao() {
        return saldoAcao;
    }

    public double getSaldoTituloDivida() {
        return saldoTituloDivida;
    }

    public void creditarSaldoAcao(double valor) {
        saldoAcao += valor;
    }

    public void debitarSaldoAcao(double valor) {
        saldoAcao -= valor;
    }

    public void creditarSaldoTituloDivida(double valor) {
        saldoTituloDivida += valor;
    }

    public void debitarSaldoTituloDivida(double valor) {
        saldoTituloDivida -= valor;
    }
}