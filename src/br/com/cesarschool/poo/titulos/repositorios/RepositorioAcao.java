package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioAcao {

    private static final String ARQUIVO = "Acao.txt";

    public boolean incluir(Acao acao) {
        if (buscar(acao.getIdentificador()) != null) {
            return false; // Identificador já existe
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            String linha = acao.getIdentificador() + ";" +
                    acao.getNome() + ";" +
                    acao.getDataValidade() + ";" +
                    acao.getValorUnitario();
            writer.write(linha);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean alterar(Acao acao) {
        List<Acao> acoes = listarTodas();
        boolean encontrado = false;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Acao ac : acoes) {
                if (ac.getIdentificador() == acao.getIdentificador()) {
                    ac = acao; // Substitui a ação encontrada
                    encontrado = true;
                }
                String linha = ac.getIdentificador() + ";" +
                        ac.getNome() + ";" +
                        ac.getDataValidade() + ";" +
                        ac.getValorUnitario();
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encontrado;
    }

    public boolean excluir(int identificador) {
        List<Acao> acoes = listarTodas();
        boolean encontrado = false;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Acao ac : acoes) {
                if (ac.getIdentificador() != identificador) {
                    String linha = ac.getIdentificador() + ";" +
                            ac.getNome() + ";" +
                            ac.getDataValidade() + ";" +
                            ac.getValorUnitario();
                    writer.write(linha);
                    writer.newLine();
                } else {
                    encontrado = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encontrado;
    }

    public Acao buscar(int identificador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);
                if (id == identificador) {
                    String nome = dados[1];
                    LocalDate dataValidade = LocalDate.parse(dados[2]);
                    double valorUnitario = Double.parseDouble(dados[3]);
                    return new Acao(id, nome, dataValidade, valorUnitario);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Acao> listarTodas() {
        List<Acao> acoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);
                String nome = dados[1];
                LocalDate dataValidade = LocalDate.parse(dados[2]);
                double valorUnitario = Double.parseDouble(dados[3]);
                Acao acao = new Acao(id, nome, dataValidade, valorUnitario);
                acoes.add(acao);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acoes;
    }
}
