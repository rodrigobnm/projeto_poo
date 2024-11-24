package br.com.cesarschool.poo.titulos.entidades;

import br.com.cesarschool.poo.titulos.utils.Comparavel;
import br.gov.cesarschool.poo.daogenerico.Entidade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao extends Entidade implements Comparavel {
	private static final long serialVersionUID = 1L;
	
    private EntidadeOperadora entidadeCredito;
    private EntidadeOperadora entidadeDebito;
    private Acao acao;
    private TituloDivida tituloDivida;
    private double valorOperacao;
    private LocalDateTime dataHoraOperacao;

    public Transacao(EntidadeOperadora entidadeCredito, EntidadeOperadora entidadeDebito, Acao acao, TituloDivida tituloDivida, double valorOperacao, LocalDateTime dataHoraOperacao) {
        this.entidadeCredito = entidadeCredito;
        this.entidadeDebito = entidadeDebito;
        this.acao = acao;
        this.tituloDivida = tituloDivida;
        this.valorOperacao = valorOperacao;
        this.dataHoraOperacao = dataHoraOperacao;
    }

    public EntidadeOperadora getEntidadeCredito() {
        return entidadeCredito;
    }

    public EntidadeOperadora getEntidadeDebito() {
        return entidadeDebito;
    }

    public Acao getAcao() {
        return acao;
    }

    public TituloDivida getTituloDivida() {
        return tituloDivida;
    }

    public double getValorOperacao() {
        return valorOperacao;
    }

    public LocalDateTime getDataHoraOperacao() {
        return dataHoraOperacao;
    }

    public String getIdUnico() {
        String diferenca = "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        String formattedDateTime = this.getDataHoraOperacao().format(formatter);

        if (this.getAcao() != null) {
            diferenca =  this.getAcao().getIdUnico();
        } else {
            diferenca = this.getTituloDivida().getIdUnico();
        }

        return this.entidadeCredito.getIdUnico() + this.entidadeDebito.getIdUnico() + diferenca + formattedDateTime;
    }

    @Override
    public int comparar(Comparavel c) {
        Transacao outra = (Transacao) c;
        return outra.getDataHoraOperacao().compareTo(this.dataHoraOperacao);
    }
}