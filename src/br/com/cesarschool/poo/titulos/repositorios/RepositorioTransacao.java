package br.com.cesarschool.poo.titulos.repositorios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;

public class RepositorioTransacao {

    public boolean incluir(Transacao transacao) {
        File arquivo = new File("Transacao.txt");

        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                
                if (partes.length < 20) {
                    System.out.println("Linha com formato inválido: " + linha);
                    continue;
                }

                if (Integer.parseInt(partes[0]) == transacao.getEntidadeCredito().getIdentificador() &&
                    Double.parseDouble(partes[18]) == transacao.getValorOperacao() &&
                    partes[19].equals(transacao.getDataHoraOperacao().toString())) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, true))) {
            String linhaTransacao = construirLinhaTransacao(transacao);
            pw.println(linhaTransacao);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String construirLinhaTransacao(Transacao transacao) {
        String entidadeCredito = transacao.getEntidadeCredito().getIdentificador() + ";" +
                                 transacao.getEntidadeCredito().getNome() + ";" +
                                 transacao.getEntidadeCredito().getAutorizadoAcao() + ";" +
                                 transacao.getEntidadeCredito().getSaldoAcao() + ";" +
                                 transacao.getEntidadeCredito().getSaldoTituloDivida();

        String entidadeDebito = transacao.getEntidadeDebito().getIdentificador() + ";" +
                                transacao.getEntidadeDebito().getNome() + ";" +
                                transacao.getEntidadeDebito().getAutorizadoAcao() + ";" +
                                transacao.getEntidadeDebito().getSaldoAcao() + ";" +
                                transacao.getEntidadeDebito().getSaldoTituloDivida();

        String acao = (transacao.getAcao() != null) ? transacao.getAcao().getIdentificador() + ";" +
                       transacao.getAcao().getNome() + ";" +
                       transacao.getAcao().getDataDeValidade() + ";" +
                       transacao.getAcao().getValorUnitario() : "null";

        String tituloDivida = (transacao.getTituloDivida() != null) ? transacao.getTituloDivida().getIdentificador() + ";" +
                           transacao.getTituloDivida().getNome() + ";" +
                           transacao.getTituloDivida().getDataDeValidade() + ";" +
                           transacao.getTituloDivida().getTaxaJuros() : "null";

        return entidadeCredito + ";" +
               entidadeDebito + ";" +
               acao + ";" +
               tituloDivida + ";" +
               transacao.getValorOperacao() + ";" +
               transacao.getDataHoraOperacao();
    }

    public Transacao[] buscarPorEntidadeCredora(int identificadorEntidadeCredito) {
        List<Transacao> transacoesEncontradas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Transacao.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
               
                if (partes.length < 20) {
                    System.out.println("Linha com formato inválido: " + linha);
                    continue;
                }

                if (Integer.parseInt(partes[0]) == identificadorEntidadeCredito) {
                    try {
                        EntidadeOperadora entidadeCredito = new EntidadeOperadora(
                            Integer.parseInt(partes[0]),
                            partes[1],
                            Boolean.parseBoolean(partes[2]),
                            Double.parseDouble(partes[3]),
                            Double.parseDouble(partes[4])
                        );

                        EntidadeOperadora entidadeDebito = new EntidadeOperadora(
                            Integer.parseInt(partes[5]),
                            partes[6],
                            Boolean.parseBoolean(partes[7]),
                            Double.parseDouble(partes[8]),
                            Double.parseDouble(partes[9])
                        );

                        Acao acao = (!partes[10].equals("null")) ? new Acao(
                                Integer.parseInt(partes[10]),
                                partes[11],
                                LocalDate.parse(partes[12]),
                                Double.parseDouble(partes[13])
                            ) : null;

                        TituloDivida tituloDivida = (!partes[14].equals("null")) ? new TituloDivida(
                                Integer.parseInt(partes[14]),
                                partes[15],
                                LocalDate.parse(partes[16]),
                                Double.parseDouble(partes[17])
                            ) : null;

                        Transacao transacao = new Transacao(
                            entidadeCredito,
                            entidadeDebito,
                            acao,
                            tituloDivida,
                            Double.parseDouble(partes[18]),
                            LocalDateTime.parse(partes[19])
                        );
                        transacoesEncontradas.add(transacao);
                    } catch (Exception e) {
                        System.out.println("Erro ao processar linha: " + linha);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transacoesEncontradas.toArray(new Transacao[0]);
    }

    public Transacao[] buscarPorEntidadeDevedora(int identificadorEntidadeDebito) {
        List<Transacao> transacoesEncontradas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Transacao.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                
                if (partes.length < 20) {
                    System.out.println("Linha com formato inválido: " + linha);
                    continue; 
                }

                if (Integer.parseInt(partes[5]) == identificadorEntidadeDebito) {
                    try {
                        EntidadeOperadora entidadeCredito = new EntidadeOperadora(
                            Integer.parseInt(partes[0]),
                            partes[1],
                            Boolean.parseBoolean(partes[2]),
                            Double.parseDouble(partes[3]),
                            Double.parseDouble(partes[4])
                        );

                        EntidadeOperadora entidadeDebito = new EntidadeOperadora(
                            Integer.parseInt(partes[5]),
                            partes[6],
                            Boolean.parseBoolean(partes[7]),
                            Double.parseDouble(partes[8]),
                            Double.parseDouble(partes[9])
                        );

                        Acao acao = (!partes[10].equals("null")) ? new Acao(
                                Integer.parseInt(partes[10]),
                                partes[11],
                                LocalDate.parse(partes[12]),
                                Double.parseDouble(partes[13])
                            ) : null;

                        TituloDivida tituloDivida = (!partes[14].equals("null")) ? new TituloDivida(
                                Integer.parseInt(partes[14]),
                                partes[15],
                                LocalDate.parse(partes[16]),
                                Double.parseDouble(partes[17])
                            ) : null;

                        Transacao transacao = new Transacao(
                            entidadeCredito,
                            entidadeDebito,
                            acao,
                            tituloDivida,
                            Double.parseDouble(partes[18]),
                            LocalDateTime.parse(partes[19])
                        );
                        transacoesEncontradas.add(transacao);
                    } catch (Exception e) {
                        System.out.println("Erro ao processar linha: " + linha);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transacoesEncontradas.toArray(new Transacao[0]);
    }
}