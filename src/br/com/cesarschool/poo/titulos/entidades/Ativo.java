package br.com.cesarschool.poo.titulos.entidades;

import java.time.LocalDate;
import java.io.Serializable;
import br.gov.cesarschool.poo.daogenerico.Entidade;

/**
 * A classe Ativo deve estender a classe Entidade e implementar o método getIdUnico().
 * O id único de Ativo é o identificador (atributo 'identificador').
 */

public class Ativo extends Entidade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int identificador;
    private String nome;
    private LocalDate dataDeValidade;
    public Ativo(int identificador, String nome, LocalDate dataDeValidade) {
        this.identificador = identificador;
        this.nome = nome;
        this.dataDeValidade = dataDeValidade;
    }
    public int getIdentificador() {
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
    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }
    public void setDataDeValidade(LocalDate dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }
}