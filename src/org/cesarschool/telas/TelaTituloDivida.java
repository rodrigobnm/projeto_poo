package org.cesarschool.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TelaTituloDivida extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;

    private JTextField txtId, txtNome, txtDataValidade, txtTaxaJuros;
    private JLabel resultLabel;
    private MediatorTituloDivida mediator = MediatorTituloDivida.getInstanciaSingleton();

    public TelaTituloDivida() {
        setTitle("Títulos de Dívida");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitulo = new JLabel("TÍTULOS DE DÍVIDA", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setFont(new Font("Impact", Font.BOLD, 48));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        txtId = createTextField("Identificador:", panel, gbc, 1);
        txtNome = createTextField("Nome:", panel, gbc, 2);
        txtDataValidade = createTextField("Data Validade (AAAA-MM-DD):", panel, gbc, 3);
        txtTaxaJuros = createTextField("Taxa de Juros:", panel, gbc, 4);

        JButton btnIncluir = createButton("Incluir", e -> incluirTitulo());
        JButton btnAlterar = createButton("Alterar", e -> alterarTitulo());
        JButton btnExcluir = createButton("Excluir", e -> excluirTitulo());
        JButton btnBuscar = createButton("Buscar", e -> buscarTitulo());
        JButton btnVoltar = createButton("Voltar", e -> {
            TelaMenu mainScreen = new TelaMenu();
            mainScreen.setVisible(true);
            dispose();
        });

        gbc.gridwidth = 2;
        gbc.gridy = 5;
        panel.add(btnIncluir, gbc);
        gbc.gridy = 6;
        panel.add(btnExcluir, gbc);
        gbc.gridy = 7;
        panel.add(btnAlterar, gbc);
        gbc.gridy = 8;
        panel.add(btnBuscar, gbc);
        gbc.gridy = 9;
        panel.add(btnVoltar, gbc);
        
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        gbc.gridy = 10;
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

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(new Color(160, 32, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.addActionListener(action);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        return button;
    }

    private void incluirTitulo() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            LocalDate dataValidade = LocalDate.parse(txtDataValidade.getText());
            double taxaJuros = Double.parseDouble(txtTaxaJuros.getText());

            TituloDivida titulo = new TituloDivida(id, nome, dataValidade, taxaJuros);
            String resultado = mediator.incluir(titulo);
            resultLabel.setText(resultado != null ? resultado : "Título incluído com sucesso!");
            limparCampos();
        } catch (NumberFormatException | DateTimeParseException ex) {
            resultLabel.setText("Erro: Verifique os dados inseridos.");
        }
    }

    private void alterarTitulo() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            LocalDate dataValidade = LocalDate.parse(txtDataValidade.getText());
            double taxaJuros = Double.parseDouble(txtTaxaJuros.getText());

            TituloDivida titulo = new TituloDivida(id, nome, dataValidade, taxaJuros);
            String resultado = mediator.alterar(titulo);
            resultLabel.setText(resultado != null ? resultado : "Título alterado com sucesso!");
            limparCampos();
        } catch (NumberFormatException | DateTimeParseException ex) {
            resultLabel.setText("Erro: Verifique os dados inseridos.");
        }
    }

    private void excluirTitulo() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String resultado = mediator.excluir(id);
            resultLabel.setText(resultado != null ? resultado : "Título excluído com sucesso!");
            limparCampos();
        } catch (NumberFormatException ex) {
            resultLabel.setText("Erro: O ID deve ser numérico.");
        }
    }

    private void buscarTitulo() {
        try {
            int id = Integer.parseInt(txtId.getText());
            TituloDivida titulo = mediator.buscar(id);
            if (titulo != null) {
                txtNome.setText(titulo.getNome());
                txtDataValidade.setText(titulo.getDataDeValidade().toString());
                txtTaxaJuros.setText(String.valueOf(titulo.getTaxaJuros()));
                resultLabel.setText("Título encontrado!");
            } else {
                resultLabel.setText("Título não encontrado.");
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Erro: O ID deve ser numérico.");
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtDataValidade.setText("");
        txtTaxaJuros.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaTituloDivida().setVisible(true);
        });
    }
}
