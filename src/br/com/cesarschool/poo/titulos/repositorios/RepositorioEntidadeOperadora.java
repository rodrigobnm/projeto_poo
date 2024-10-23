package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RepositorioEntidadeOperadora {

    private final String arquivoNome = "EntidadeOperadora.txt";

    public RepositorioEntidadeOperadora() {
        File arquivo = new File(arquivoNome);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean incluir(EntidadeOperadora entidadeOperadora) {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoNome))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (Integer.parseInt(dados[0]) == entidadeOperadora.getIdentificador()) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivoNome, true))) {
            pw.println(entidadeOperadora.getIdentificador() + ";" + 
                       entidadeOperadora.getNome() + ";" + 
                       entidadeOperadora.getAutorizadoAcao() + ";" + 
                       entidadeOperadora.getSaldoAcao() + ";" + 
                       entidadeOperadora.getSaldoTituloDivida());
            return true; 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; 
    }

    public boolean alterar(EntidadeOperadora entidadeOperadora) {
        StringBuilder conteudo = new StringBuilder();
        boolean identificadorEncontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoNome))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (Integer.parseInt(dados[0]) == entidadeOperadora.getIdentificador()) {
                    linha = entidadeOperadora.getIdentificador() + ";" + 
                            entidadeOperadora.getNome() + ";" + 
                            entidadeOperadora.getAutorizadoAcao() + ";" + 
                            entidadeOperadora.getSaldoAcao() + ";" + 
                            entidadeOperadora.getSaldoTituloDivida();
                    identificadorEncontrado = true; 
                }
                conteudo.append(linha).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (identificadorEncontrado) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(arquivoNome))) {
                pw.print(conteudo.toString());
                return true; 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean excluir(int identificador) {
        StringBuilder conteudo = new StringBuilder();
        boolean identificadorEncontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoNome))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (Integer.parseInt(dados[0]) == identificador) {
                    identificadorEncontrado = true;
                    continue;
                }
                conteudo.append(linha).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (identificadorEncontrado) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(arquivoNome))) {
                pw.print(conteudo.toString());
                return true; 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false; 
    }

    public EntidadeOperadora buscar(int identificador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoNome))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (Integer.parseInt(dados[0]) == identificador) {
                    String nome = dados[1];
                    boolean autorizadoAcao = Boolean.parseBoolean(dados[2]);
                    double saldoAcao = Double.parseDouble(dados[3]);
                    double saldoTituloDivida = Double.parseDouble(dados[4]);
                    return new EntidadeOperadora(identificador, nome, autorizadoAcao, saldoAcao, saldoTituloDivida);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}