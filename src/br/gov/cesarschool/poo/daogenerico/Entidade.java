package br.gov.cesarschool.poo.daogenerico;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * Esta classe representa uma superclasse de todas as entidades. 
 * 
 * Deve ter os seguintes atributos (com respectivos set e get): 
 *	LocalDateTime dataHoraInclusao, 
 *  LocalDateTime dataHoraUltimaAlteracao,
 *	String usuarioInclusao e 
 *	String usuarioUltimaAlteracao
 *
 * Deve ter um único construtor sem parâmetros.
 * 
 * Deve ter um método abstrato getIdUnico().
 * 
 * Deve implementar a interface Serializable do JAVA
 */

public abstract class Entidade implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime dataHoraInclusao;
    private LocalDateTime dataHoraUltimaAlteracao;
    private String usuarioInclusao;
    private String usuarioUltimaAlteracao;

    // Construtor sem parâmetros
    public Entidade() {
        this.dataHoraInclusao = LocalDateTime.now();  // Definir a data e hora de inclusão
        this.dataHoraUltimaAlteracao = LocalDateTime.now();  // Definir a data e hora de última alteração
        this.usuarioInclusao = null;  // Definido como null inicialmente
        this.usuarioUltimaAlteracao = null;  // Definido como null inicialmente
    }

    // Método abstrato que será implementado nas classes filhas
    public abstract String getIdUnico();

    // Getters e Setters para os atributos
    public LocalDateTime getDataHoraInclusao() {
        return dataHoraInclusao;
    }

    public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
        this.dataHoraInclusao = dataHoraInclusao;
    }

    public LocalDateTime getDataHoraUltimaAlteracao() {
        return dataHoraUltimaAlteracao;
    }

    public void setDataHoraUltimaAlteracao(LocalDateTime dataHoraUltimaAlteracao) {
        this.dataHoraUltimaAlteracao = dataHoraUltimaAlteracao;
    }

    public String getUsuarioInclusao() {
        return usuarioInclusao;
    }

    public void setUsuarioInclusao(String usuarioInclusao) {
        this.usuarioInclusao = usuarioInclusao;
    }

    public String getUsuarioUltimaAlteracao() {
        return usuarioUltimaAlteracao;
    }

    public void setUsuarioUltimaAlteracao(String usuarioUltimaAlteracao) {
        this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
    }
}
