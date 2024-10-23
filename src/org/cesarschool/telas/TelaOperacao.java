package org.cesarschool.telas;

import javax.swing.*;
import br.com.cesarschool.poo.titulos.mediators.MediatorOperacao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TelaOperacao extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField campoEhAcao, campoEntidadeCredito, campoEntidadeDebito, campoAcaoOuTitulo, campoValor;
    private JLabel resultadoLabel;
    private JTextArea extratoArea;

    public TelaOperacao() {
        setTitle("Realizar Operação");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitulo = new JLabel("REALIZAR OPERAÇÃO", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setFont(new Font("Impact", Font.BOLD, 48));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        campoEhAcao = createTextField("Tipo Ação (true/false):", panel, gbc, 1);
        campoEntidadeCredito = createTextField("ID Entidade Crédito:", panel, gbc, 2);
        campoEntidadeDebito = createTextField("ID Entidade Débito:", panel, gbc, 3);
        campoAcaoOuTitulo = createTextField("ID Título/Ação:", panel, gbc, 4);
        campoValor = createTextField("Valor da Operação:", panel, gbc, 5);

        JButton botaoOperacao = new JButton("Confirmar");
        JButton botaoExtrato = new JButton("Gerar Extrato");
        JButton btnVoltar = new JButton("Voltar");

        configureButton(botaoOperacao, e -> realizarOperacao());
        configureButton(botaoExtrato, e -> gerarExtrato());
        configureButton(btnVoltar, e -> {
            TelaMenu mainScreen = new TelaMenu();
            mainScreen.setVisible(true);
            dispose();
        });

        resultadoLabel = new JLabel("", SwingConstants.CENTER);
        resultadoLabel.setForeground(Color.BLACK);
        resultadoLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        gbc.gridwidth = 2;
        gbc.gridy = 6;
        panel.add(resultadoLabel, gbc);

        // Título para o campo de extratos
        JLabel lblExtratos = new JLabel("Visualização de Extratos", SwingConstants.CENTER);
        lblExtratos.setForeground(Color.BLACK);
        lblExtratos.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 7;
        panel.add(lblExtratos, gbc);

        extratoArea = new JTextArea();
        extratoArea.setEditable(false);
        extratoArea.setFont(new Font("Arial", Font.PLAIN, 18));
        extratoArea.setLineWrap(true);
        extratoArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(extratoArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        gbc.gridy = 8;
        panel.add(scrollPane, gbc);

        gbc.gridy = 9;
        panel.add(botaoOperacao, gbc);
        gbc.gridy = 10;
        panel.add(botaoExtrato, gbc);
        gbc.gridy = 11;
        panel.add(btnVoltar, gbc);

        panel.setBorder(BorderFactory.createEmptyBorder(50, 300, 50, 300));
        add(panel);
        setVisible(true);
    }

    private JTextField createTextField(String label, JPanel panel, GridBagConstraints gbc, int gridY) {
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(Color.BLACK);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        
        fieldPanel.add(jLabel);
        fieldPanel.add(textField);

        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; 
        panel.add(fieldPanel, gbc);
        
        return textField;
    }

    private void configureButton(JButton button, ActionListener action) {
        button.setBackground(new Color(160, 32, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.addActionListener(action);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)));
    }

    private void realizarOperacao() {
        try {
            boolean ehAcao = Boolean.parseBoolean(campoEhAcao.getText());
            int entidadeCredito = Integer.parseInt(campoEntidadeCredito.getText());
            int entidadeDebito = Integer.parseInt(campoEntidadeDebito.getText());
            int idAcaoOuTitulo = Integer.parseInt(campoAcaoOuTitulo.getText());
            double valor = Double.parseDouble(campoValor.getText());

            MediatorOperacao mediador = MediatorOperacao.getInstancia();
            String resultado = mediador.realizarOperacao(ehAcao, entidadeCredito, entidadeDebito, idAcaoOuTitulo, valor);
            resultadoLabel.setText(resultado);
        } catch (NumberFormatException ex) {
            resultadoLabel.setText("Erro: Entrada inválida.");
        }
    }

    private void gerarExtrato() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Transacao.txt"))) {
            StringBuilder conteudo = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
            extratoArea.setText(conteudo.toString());
        } catch (IOException e) {
            extratoArea.setText("Erro ao ler o extrato.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaOperacao::new);
    }
}
