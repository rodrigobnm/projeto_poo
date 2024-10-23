package br.com.cesarschool.poo.titulos.repositorios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

public class RepositorioTituloDivida {

    private final String arquivoNome = "TituloDivida.txt";

    public RepositorioTituloDivida() {
        File arquivo = new File(arquivoNome);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean incluir(TituloDivida tituloDivida) {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoNome))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue; 
                String[] dados = linha.split(";");
                if (Integer.parseInt(dados[0]) == tituloDivida.getIdentificador()) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivoNome, true))) {
            pw.println(tituloDivida.getIdentificador() + ";" + 
                       tituloDivida.getNome() + ";" + 
                       tituloDivida.getDataDeValidade() + ";" + 
                       tituloDivida.getTaxaJuros());
            return true; 
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(TituloDivida tituloDivida) {
        StringBuilder conteudo = new StringBuilder();
        boolean identificadorEncontrado = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoNome))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue; 
                String[] dados = linha.split(";");
                if (Integer.parseInt(dados[0]) == tituloDivida.getIdentificador()) {
                    linha = tituloDivida.getIdentificador() + ";" + 
                            tituloDivida.getNome() + ";" + 
                            tituloDivida.getDataDeValidade() + ";" + 
                            tituloDivida.getTaxaJuros();
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
                if (linha.trim().isEmpty()) continue; 
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

    public TituloDivida buscar(int identificador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoNome))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(";");
                if (Integer.parseInt(dados[0]) == identificador) {
                    int id = Integer.parseInt(dados[0]);
                    String nome = dados[1];
                    LocalDate dataDeValidade = LocalDate.parse(dados[2]);
                    double taxaJuros = Double.parseDouble(dados[3]);
                    return new TituloDivida(id, nome, dataDeValidade, taxaJuros); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; 
    }
}