package com.mycompany.dsfinalprojectapp;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ControlPanel extends JPanel {
    // Manages panels
    private GamePanel gamePanel;
    private JPanel contentPanel;
    
    // Manages number boces
    private JLabel runtimeLabel;
    private JComboBox<String> structureBox;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField operationCountField;

    public ControlPanel(GamePanel gamePanel) {
        // Creates the panel
        this.gamePanel = gamePanel;
        setPreferredSize(new Dimension(200, 500));
        setLayout(new BorderLayout());

        // Puts it all onto one panel so the background color can be neat
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(255,255,255));
        contentPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY), BorderFactory.createEmptyBorder(10,10,10,10)));
        add(contentPanel, BorderLayout.NORTH);

        createComponents();
    }

    // Adds components, mostly to contentPanel
    private void createComponents() {
        // Structure Box
        contentPanel.add(new JLabel("Structure"));
        structureBox = new JComboBox<>(new String[]{"LinkedList", "2D Grid", "Hash Grid"});
        structureBox.setMaximumSize(new Dimension(120, 25));
        structureBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(structureBox);
        
        // Alter Map Width
        contentPanel.add(new JLabel("Map Width"));
        widthField = new JTextField("5000");
        widthField.setMaximumSize(new Dimension(120,25));
        widthField.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(widthField);

        // Alter Map Height
        contentPanel.add(new JLabel("Map Height"));
        heightField = new JTextField("5000");
        heightField.setMaximumSize(new Dimension(120,25));
        heightField.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(heightField);
        
        // Create New World Button, ala Neon Genesis
        JButton createWorldButton = new JButton("Create World");
        createWorldButton.addActionListener(e -> createWorld());
        contentPanel.add(createWorldButton);
        contentPanel.add(Box.createVerticalStrut(20));

        // Allows for the modification of operation count
        contentPanel.add(new JLabel("Operation Count"));
        operationCountField = new JTextField("1000");
        operationCountField.setMaximumSize(new Dimension(120,25));
        operationCountField.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(operationCountField);
        
        // Randomly inserts x entities/objects
        JButton randomInsert = new JButton("Insert Random");
        randomInsert.addActionListener(e -> {
            try {
                int count = Integer.parseInt(operationCountField.getText());
                insertRandom(count);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid operation count");
            }
        });
        randomInsert.setMaximumSize(new Dimension(120,25));
        randomInsert.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(randomInsert);

        // Removes entities at x random coordinates
        JButton randomDelete = new JButton("Delete Random");
        randomDelete.addActionListener(e -> {
            try {
                int count = Integer.parseInt(operationCountField.getText());
                deleteRandom(count);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid operation count");
            }
        });
        randomDelete.setMaximumSize(new Dimension(120,25));
        randomDelete.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(randomDelete);

        // Moves entities at x random coordinates
        JButton randomMove = new JButton("Move Random");
        randomMove.addActionListener(e -> {
            try {
                int count = Integer.parseInt(operationCountField.getText());
                moveRandom(count);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid operation count");
            }
        });
        randomMove.setMaximumSize(new Dimension(120,25));
        randomMove.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(randomMove);

        // Decorative empty gap
        contentPanel.add(Box.createVerticalStrut(20));

        // Zoom button
        JButton zoomInButton = new JButton("Zoom +");
        zoomInButton.addActionListener(e -> gamePanel.zoomIn());
        contentPanel.add(zoomInButton);

        // Zoom button
        JButton zoomOutButton = new JButton("Zoom -");
        zoomOutButton.addActionListener(e -> gamePanel.zoomOut());
        contentPanel.add(zoomOutButton);
        
        // Enables and disables rendering
        JCheckBox renderBox = new JCheckBox("Render Enabled", true);
        renderBox.addActionListener(e -> {gamePanel.setRenderEnabled(renderBox.isSelected());});
        contentPanel.add(renderBox);
        
        // Decorative empty gap
        contentPanel.add(Box.createVerticalStrut(20));

        // Shows runtime for a certain operation (not for direct measurement but for comparison with other runtimes)
        runtimeLabel = new JLabel("Runtime:");
        contentPanel.add(runtimeLabel);
    }
    
    private void createWorld() {
        try {
            // Gathers size and type of new map
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            String selected = (String) structureBox.getSelectedItem();
            
            // Creates a new map
            gamePanel.createWorld(selected, width, height);
        } catch (Exception ex) {
           JOptionPane.showMessageDialog( this, "Invalid map size");
        }
    }
    
    // Inserts x entities at random x coordinates
    private void insertRandom(int count) {
        Random rand = new Random();
        SpatialStructure s = gamePanel.getStructure();
        
        long start = System.nanoTime();
        
        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(s.getWidth());
            int y = rand.nextInt(s.getHeight());
            s.insert(new Entity(x, y));
        }
        
        long end = System.nanoTime();

        runtimeLabel.setText("Insert: " + (end - start) + " ns");
    }

    // Deletes entities at x random coordinates
    private void deleteRandom(int count) {
        Random rand = new Random();
        SpatialStructure s = gamePanel.getStructure();

        long start = System.nanoTime();

        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(s.getWidth());
            int y = rand.nextInt(s.getHeight());
            s.remove(x, y);
        }

        long end = System.nanoTime();

        runtimeLabel.setText("Delete: " + (end - start) + " ns");
    }

    // Moves entities at x random coordinates, if exists, to other coordinates
    private void moveRandom(int count) {
        Random rand = new Random();
        SpatialStructure s = gamePanel.getStructure();

        long start = System.nanoTime();

        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(s.getWidth());
            int y = rand.nextInt(s.getHeight());

            int nx = x + rand.nextInt(3) - 1;
            int ny = y + rand.nextInt(3) - 1;

            s.move(x, y, nx, ny);
        }

        long end = System.nanoTime();

        runtimeLabel.setText("Move: " + (end - start) + " ns");
    }
}