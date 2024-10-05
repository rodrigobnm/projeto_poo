package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTransacao {

    private static final String ARQUIVO = "Transacao.txt";

    public boolean incluir(Transacao transacao) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            String linha = transacao.getIdentificador() + ";" +
                    transacao.getDataHora() + ";" +
                    transacao.getTipo() + ";" +
                    transacao.getEntidadeOperadora().getIdentificador() + ";" +
                    (transacao.getAcao() != null ? transacao.getAcao().getIdentificador() : "null") + ";" +
                    (transacao.getTituloDivida() != null ? transacao.getTituloDivida().getIdentificador() : "null");
            writer.write(linha);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Transacao> listarTodas() {
        List<Transacao> transacoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);
                LocalDateTime dataHora = LocalDateTime.parse(dados[1]);
                String tipo = dados[2];
                int idEntidadeOperadora = Integer.parseInt(dados[3]);
                int idAcao = dados[4].equals("null") ? -1 : Integer.parseInt(dados[4]);
                int idTituloDivida = dados[5].equals("null") ? -1 : Integer.parseInt(dados[5]);

                EntidadeOperadora entidadeOperadora = new EntidadeOperadora(idEntidadeOperadora);
                Acao acao = idAcao != -1 ? new Acao(idAcao) : null;
                TituloDivida tituloDivida = idTituloDivida != -1 ? new TituloDivida(idTituloDivida) : null;

                Transacao transacao = new Transacao(id, dataHora, tipo, entidadeOperadora, acao, tituloDivida);
                transacoes.add(transacao);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transacoes;
    }
}
