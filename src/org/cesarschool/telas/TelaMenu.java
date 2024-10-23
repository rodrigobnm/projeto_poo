package org.cesarschool.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class TelaMenu extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;

    public TelaMenu() {
        setTitle("Menu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255));

        JLabel lblTitulo = new JLabel("Atividade Continuada, JAVA", SwingConstants.CENTER);
        JLabel lblAutores = new JLabel("Bernardo Heuer, Rodrigo Nunes", SwingConstants.CENTER);
        JLabel lblEmailAutores = new JLabel("bchg@cesar.school, rbnm@cesar.school", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.BLACK);
        lblAutores.setForeground(Color.BLACK);
        lblEmailAutores.setForeground(Color.BLACK);
        lblTitulo.setFont(new Font("Impact", Font.BOLD, 48));
        lblAutores.setFont(new Font("Arial", Font.PLAIN, 24));
        lblEmailAutores.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton btnAcao = new JButton("Ação");
        JButton btnEntidadeOperadora = new JButton("Entidade Operadora");
        JButton btnTituloDivida = new JButton("Titulo de Divida");
        JButton btnOperacao = new JButton("Operação");

        configurarBotao(btnAcao);
        configurarBotao(btnEntidadeOperadora);
        configurarBotao(btnTituloDivida);
        configurarBotao(btnOperacao);

        btnAcao.addActionListener(e -> {
            TelaAcao acaoScreen = new TelaAcao();
            acaoScreen.setVisible(true);
            dispose();
        });

        btnEntidadeOperadora.addActionListener(e -> {
            TelaEntidadeOperadora entidadeScreen = new TelaEntidadeOperadora();
            entidadeScreen.setVisible(true);
            dispose();
        });

        btnTituloDivida.addActionListener(e -> {
            TelaTituloDivida acaoScreen = new TelaTituloDivida();
            acaoScreen.setVisible(true);
            dispose();
        });

        btnOperacao.addActionListener(e -> {
            TelaOperacao acaoScreen = new TelaOperacao();
            acaoScreen.setVisible(true);
            dispose();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;

        panel.add(lblTitulo, gbc);
        gbc.gridy = 1;
        panel.add(lblAutores, gbc);
        gbc.gridy = 2;
        panel.add(lblEmailAutores, gbc);
        gbc.gridy = 3;
        panel.add(btnAcao, gbc);
        gbc.gridy = 4;
        panel.add(btnEntidadeOperadora, gbc);
        gbc.gridy = 5;
        panel.add(btnTituloDivida, gbc);
        gbc.gridy = 6;
        panel.add(btnOperacao, gbc);

        panel.setBorder(BorderFactory.createEmptyBorder(50, 300, 50, 300));
        add(panel);
        setVisible(true);
    }

    private void configurarBotao(JButton botao) {
        botao.setBackground(new Color(160, 32, 240));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 24));
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 20, 10, 20))); // Bordas arredondadas
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaMenu::new);
    }
}
