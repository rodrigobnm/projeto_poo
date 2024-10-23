package org.cesarschool.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;

public class TelaEntidadeOperadora extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtIdentificador, txtNome, txtAutorizadoAcao, txtSaldoAcao, txtSaldoTituloDivida;
    private JLabel resultLabel;
    private MediatorEntidadeOperadora mediator;

    public TelaEntidadeOperadora() {
        mediator = MediatorEntidadeOperadora.getInstanciaSingleton();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Entidade Operadora");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("GERENCIAR ENTIDADES OPERADORAS", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Impact", Font.BOLD, 48));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        txtIdentificador = createTextField("Identificador (ID):", panel, gbc, 1);
        txtNome = createTextField("Nome:", panel, gbc, 2);
        txtAutorizadoAcao = createTextField("Autorizado (true/false):", panel, gbc, 3);
        txtSaldoAcao = createTextField("Saldo Ação:", panel, gbc, 4);
        txtSaldoTituloDivida = createTextField("Saldo Título Dívida:", panel, gbc, 5);

        JButton btnIncluir = new JButton("Adicionar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnVoltar = new JButton("Voltar");

        configureButton(btnIncluir, e -> incluirEntidade());
        configureButton(btnAlterar, e -> alterarEntidade());
        configureButton(btnExcluir, e -> excluirEntidade());
        configureButton(btnBuscar, e -> buscarEntidade());
        configureButton(btnVoltar, e -> {
            new TelaMenu().setVisible(true);
            dispose();
        });

        gbc.gridwidth = 2;
        gbc.gridy = 6;
        panel.add(btnIncluir, gbc);
        gbc.gridy++;
        panel.add(btnExcluir, gbc);
        gbc.gridy++;
        panel.add(btnAlterar, gbc);
        gbc.gridy++;
        panel.add(btnBuscar, gbc);
        gbc.gridy++;
        panel.add(btnVoltar, gbc);

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setFont(new Font("Impact", Font.PLAIN, 24));
        gbc.gridy++;
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

    private void incluirEntidade() {
        EntidadeOperadora entidade = criarEntidade();
        String resultado = mediator.incluir(entidade);
        resultLabel.setText(resultado != null ? resultado : "Entidade incluída com sucesso!");
        clearFields();
    }

    private void alterarEntidade() {
        EntidadeOperadora entidade = criarEntidade();
        String resultado = mediator.alterar(entidade);
        resultLabel.setText(resultado != null ? resultado : "Entidade alterada com sucesso!");
        clearFields();
    }

    private void excluirEntidade() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            String resultado = mediator.excluir(identificador);
            resultLabel.setText(resultado != null ? resultado : "Entidade excluída com sucesso!");
            clearFields();
        } catch (NumberFormatException e) {
            resultLabel.setText("Erro: ID inválido.");
        }
    }

    private void buscarEntidade() {
        try {
            int identificador = Integer.parseInt(txtIdentificador.getText());
            EntidadeOperadora entidade = mediator.buscar(identificador);
            if (entidade != null) {
                preencherCampos(entidade);
                resultLabel.setText("Entidade encontrada!");
            } else {
                resultLabel.setText("Entidade não encontrada!");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Erro: ID inválido.");
        }
    }

    private EntidadeOperadora criarEntidade() {
        int identificador = Integer.parseInt(txtIdentificador.getText());
        String nome = txtNome.getText();
        boolean autorizadoAcao = Boolean.parseBoolean(txtAutorizadoAcao.getText());
        double saldoAcao = Double.parseDouble(txtSaldoAcao.getText());
        double saldoTituloDivida = Double.parseDouble(txtSaldoTituloDivida.getText());

        return new EntidadeOperadora(identificador, nome, autorizadoAcao, saldoAcao, saldoTituloDivida);
    }

    private void preencherCampos(EntidadeOperadora entidade) {
        txtIdentificador.setText(String.valueOf(entidade.getIdentificador()));
        txtNome.setText(entidade.getNome());
        txtAutorizadoAcao.setText(String.valueOf(entidade.getAutorizadoAcao()));
        txtSaldoAcao.setText(String.valueOf(entidade.getSaldoAcao()));
        txtSaldoTituloDivida.setText(String.valueOf(entidade.getSaldoTituloDivida()));
    }

    private void clearFields() {
        txtIdentificador.setText("");
        txtNome.setText("");
        txtAutorizadoAcao.setText("");
        txtSaldoAcao.setText("");
        txtSaldoTituloDivida.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaEntidadeOperadora::new);
    }
}
