package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEntidadeOperadora {

    private static final String ARQUIVO = "EntidadeOperadora.txt";

    public boolean incluir(EntidadeOperadora entidadeOperadora) {
        if (buscar(entidadeOperadora.getIdentificador()) != null) {
            return false; // Identificador já existe
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            String linha = entidadeOperadora.getIdentificador() + ";" +
                    entidadeOperadora.getNome() + ";" +
                    entidadeOperadora.getAutorizadoAcao() + ";" +
                    entidadeOperadora.getSaldoAcao() + ";" +
                    entidadeOperadora.getSaldoTituloDivida();
            writer.write(linha);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean alterar(EntidadeOperadora entidadeOperadora) {
        List<EntidadeOperadora> entidades = listarTodas();
        boolean encontrado = false;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (EntidadeOperadora ent : entidades) {
                if (ent.getIdentificador() == entidadeOperadora.getIdentificador()) {
                    // Substitui a entidade encontrada
                    ent = entidadeOperadora;
                    encontrado = true;
                }
                String linha = ent.getIdentificador() + ";" +
                        ent.getNome() + ";" +
                        ent.getAutorizadoAcao() + ";" +
                        ent.getSaldoAcao() + ";" +
                        ent.getSaldoTituloDivida();
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encontrado;
    }

    public boolean excluir(long identificador) {
        List<EntidadeOperadora> entidades = listarTodas();
        boolean encontrado = false;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (EntidadeOperadora ent : entidades) {
                if (ent.getIdentificador() != identificador) {
                    String linha = ent.getIdentificador() + ";" +
                            ent.getNome() + ";" +
                            ent.getAutorizadoAcao() + ";" +
                            ent.getSaldoAcao() + ";" +
                            ent.getSaldoTituloDivida();
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

    public EntidadeOperadora buscar(long identificador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                long id = Long.parseLong(dados[0]);
                if (id == identificador) {
                    String nome = dados[1];
                    double autorizadoAcao = Double.parseDouble(dados[2]);
                    double saldoAcao = Double.parseDouble(dados[3]);
                    double saldoTituloDivida = Double.parseDouble(dados[4]);
                    return new EntidadeOperadora(id, nome, autorizadoAcao);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<EntidadeOperadora> listarTodas() {
        List<EntidadeOperadora> entidades = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                long id = Long.parseLong(dados[0]);
                String nome = dados[1];
                double autorizadoAcao = Double.parseDouble(dados[2]);
                double saldoAcao = Double.parseDouble(dados[3]);
                double saldoTituloDivida = Double.parseDouble(dados[4]);
                EntidadeOperadora entidade = new EntidadeOperadora(id, nome, autorizadoAcao);
                entidades.add(entidade);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entidades;
    }
}
