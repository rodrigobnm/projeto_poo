package org.cesarschool.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TelaAcao extends JFrame {
    private JTextField idField, nameField, valueField, dateField;
    private JLabel resultLabel;
    private MediatorAcao mediator;

    public TelaAcao() {
        mediator = MediatorAcao.getInstanciaSingleton();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Ações");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("REALIZAR AÇÕES", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Impact", Font.BOLD, 48));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        idField = createTextField("Identificação (ID):", panel, gbc, 1);
        nameField = createTextField("Nome:", panel, gbc, 2);
        valueField = createTextField("Valor:", panel, gbc, 3);
        dateField = createTextField("AAAA-MM-DD:", panel, gbc, 4);

        JButton addButton = new JButton("Adicionar");
        JButton updateButton = new JButton("Alterar");
        JButton deleteButton = new JButton("Excluir");
        JButton searchButton = new JButton("Buscar");
        JButton backButton = new JButton("Voltar");

        configureButton(addButton, e -> addAcao());
        configureButton(updateButton, e -> updateAcao());
        configureButton(deleteButton, e -> deleteAcao());
        configureButton(searchButton, e -> searchAcao());
        configureButton(backButton, e -> {
            new TelaMenu().setVisible(true);
            dispose();
        });

        gbc.gridwidth = 2;
        gbc.gridy = 6;
        panel.add(addButton, gbc);
        gbc.gridy = 7;
        panel.add(deleteButton, gbc);
        gbc.gridy = 8;
        panel.add(updateButton, gbc);
        gbc.gridy = 9;
        panel.add(searchButton, gbc);
        gbc.gridy = 10;
        panel.add(backButton, gbc);
        
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        gbc.gridy = 11;
        panel.add(resultLabel, gbc);
        
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

    private void addAcao() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double value = Double.parseDouble(valueField.getText());
            LocalDate date = LocalDate.parse(dateField.getText());

            Acao acao = new Acao(id, name, date, value);
            String result = mediator.incluir(acao);
            resultLabel.setText(result != null ? result : "Ação criada com sucesso!");
            clearFields();
        } catch (NumberFormatException | DateTimeParseException e) {
            resultLabel.setText("Erro: Dados inválidos.");
        }
    }

    private void updateAcao() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double value = Double.parseDouble(valueField.getText());
            LocalDate date = LocalDate.parse(dateField.getText());

            Acao acao = new Acao(id, name, date, value);
            String result = mediator.alterar(acao); // Adicione o método alterar no MediatorAcao
            resultLabel.setText(result != null ? result : "Ação modificada com sucesso!");
            clearFields();
        } catch (NumberFormatException | DateTimeParseException e) {
            resultLabel.setText("Erro: Dados inválidos.");
        }
    }

    private void deleteAcao() {
        try {
            int id = Integer.parseInt(idField.getText());
            String result = mediator.excluir(id); // Adicione o método excluir no MediatorAcao
            resultLabel.setText(result != null ? result : "Ação excluída com sucesso!");
            clearFields();
        } catch (NumberFormatException e) {
            resultLabel.setText("Erro: ID inválido.");
        }
    }

    private void searchAcao() {
        try {
            int id = Integer.parseInt(idField.getText());
            Acao acao = mediator.buscar(id);
            if (acao != null) {
                nameField.setText(acao.getNome());
                dateField.setText(acao.getDataDeValidade().toString());
                valueField.setText(String.valueOf(acao.getValorUnitario()));

                resultLabel.setText("Ação encontrada com sucesso!");
            } else {
                resultLabel.setText("Ação não encontrada.");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Erro: ID inválido.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        valueField.setText("");
        dateField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaAcao::new);
    }
}
