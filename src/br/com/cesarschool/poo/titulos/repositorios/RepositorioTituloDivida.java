package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTituloDivida {

    private static final String ARQUIVO = "TituloDivida.txt";

    public boolean incluir(TituloDivida tituloDivida) {
        if (buscar(tituloDivida.getIdentificador()) != null) {
            return false; // Identificador já existe
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            String linha = tituloDivida.getIdentificador() + ";" +
                    tituloDivida.getNome() + ";" +
                    tituloDivida.getDataValidade() + ";" +
                    tituloDivida.getTaxaJuros();
            writer.write(linha);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean alterar(TituloDivida tituloDivida) {
        List<TituloDivida> titulos = listarTodas();
        boolean encontrado = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (TituloDivida td : titulos) {
                if (td.getIdentificador() == tituloDivida.getIdentificador()) {
                    td = tituloDivida; // Substitui o título encontrado
                    encontrado = true;
                }
                String linha = td.getIdentificador() + ";" +
                        td.getNome() + ";" +
                        td.getDataValidade() + ";" +
                        td.getTaxaJuros();
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encontrado;
    }

    public boolean excluir(int identificador) {
        List<TituloDivida> titulos = listarTodas();
        boolean encontrado = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (TituloDivida td : titulos) {
                if (td.getIdentificador() != identificador) {
                    String linha = td.getIdentificador() + ";" +
                            td.getNome() + ";" +
                            td.getDataValidade() + ";" +
                            td.getTaxaJuros();
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

    public TituloDivida buscar(int identificador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);
                if (id == identificador) {
                    String nome = dados[1];
                    LocalDate dataValidade = LocalDate.parse(dados[2]);
                    double taxaJuros = Double.parseDouble(dados[3]);
                    return new TituloDivida(id, nome, dataValidade, taxaJuros);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TituloDivida> listarTodas() {
        List<TituloDivida> titulos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0]);
                String nome = dados[1];
                LocalDate dataValidade = LocalDate.parse(dados[2]);
                double taxaJuros = Double.parseDouble(dados[3]);
                TituloDivida titulo = new TituloDivida(id, nome, dataValidade, taxaJuros);
                titulos.add(titulo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return titulos;
    }
}
