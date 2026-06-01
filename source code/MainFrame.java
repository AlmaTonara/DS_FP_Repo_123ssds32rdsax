package com.mycompany.dsfinalprojectapp;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Spatial Object Manager");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GamePanel gamePanel = new GamePanel();
        ControlPanel controlPanel = new ControlPanel(gamePanel);

        add(gamePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        setLocationRelativeTo(null);
        setVisible(true);  
    }
}