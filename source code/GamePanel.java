package com.mycompany.dsfinalprojectapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    // Sets up structure and rendering (which is toggleable)
    private SpatialStructure structure;
    private boolean renderEnabled = true;

    // Camera for movement
    private int camX = 0;
    private int camY = 0;
    
    // Zoom function to test query size
    private double zoom = 1.0;

    // Base world size (too large and 2d breaks)
    private int worldWidth = 2000;
    private int worldHeight = 2000;

    // movement flags
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    // camera speed
    private int moveSpeed = 10;

    // fps
    private int fps = 0;
    private int frames = 0;
    private long lastFPSCheck = System.currentTimeMillis();

    public GamePanel() {
        // Sets default
        structure = new HashGrid(5000, 5000);
        setFocusable(true);

        // Sets controls
        setupKeyBindings();

        // Manages frames
        Timer timer = new Timer(16, e -> {
            updateCamera();
            frames++;
            long now = System.currentTimeMillis();
            if (now - lastFPSCheck >= 1000) {
                fps = frames;
                frames = 0;
                lastFPSCheck = now;
            }

            repaint();
        });

        timer.start();
    }

    // Movement keybindings
    private void setupKeyBindings() {
        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        // Classic WASD movement 
        im.put(KeyStroke.getKeyStroke("pressed W"), "w_press");
        im.put(KeyStroke.getKeyStroke("pressed S"), "s_press");
        im.put(KeyStroke.getKeyStroke("pressed A"), "a_press");
        im.put(KeyStroke.getKeyStroke("pressed D"), "d_press");

        // The other part of movement
        im.put(KeyStroke.getKeyStroke("released W"), "w_release");
        im.put(KeyStroke.getKeyStroke("released S"), "s_release");
        im.put(KeyStroke.getKeyStroke("released A"), "a_release");
        im.put(KeyStroke.getKeyStroke("released D"), "d_release");

        am.put("w_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                up = true;
            }
        });

        am.put("s_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                down = true;
            }
        });

        am.put("a_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                left = true;
            }
        });

        am.put("d_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                right = true;
            }
        });

        am.put("w_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                up = false;
            }
        });

        am.put("s_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                down = false;
            }
        });

        am.put("a_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                left = false;
            }
        });

        am.put("d_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                right = false;
            }
        });
    }

    private void updateCamera() {
        // Moves the camera/center of the screen on the map
        if (up) {
            camY -= moveSpeed;
        }
        if (down) {
            camY += moveSpeed;
        }
        if (left) {
            camX -= moveSpeed;
        }
        if (right) {
            camX += moveSpeed;
        }

        // Ensures that the camera does not go too far to the left
        if (camX < 0) {
            camX = 0;
        }
        if (camY < 0) {
            camY = 0;
        }
    }

    // Manages active structure
    public SpatialStructure getStructure() {
        return structure;
    }

    public void setStructure(SpatialStructure structure) {
        this.structure = structure;
    }

    // Enables and disables rendering
    public void setRenderEnabled(boolean enabled) {
        this.renderEnabled = enabled;
    }

    // Getter methods
    public int getCamX() {
        return camX;
    }

    public int getCamY() {
        return camY;
    }
    
    // Allows the ability to zoom/manipulate query area size
    public double getZoom() {
        return zoom;
    }

    public void zoomIn() {
        zoom *= 1.25;

        if (zoom > 20) {
            zoom = 20;
        }
    }

    public void zoomOut() {
        zoom /= 1.25;

        if (zoom < 0.1) {
            zoom = 0.1;
        }
    }

    public void createWorld(String type, int width, int height) {
        // Takes height
        worldWidth = width;
        worldHeight = height;

        // Uses polymorphism to create a structure based on type requested
        switch(type) {
            case "LinkedList":
                structure = new OneD(width, height);
                break;
            case "2D Grid":
                structure = new TwoD(width, height);
                break;
            default:
                structure = new HashGrid(width, height);
                break;
        }

        // Resets cam back to 0 so you don't get stranded a mile away upon a new beginning
        camX = 0;
        camY = 0;
    }

    // Drawing function
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (structure == null) {
            return;
        }

        // Renders things nearby the camera with offset
        if (renderEnabled) {
            int queryWidth = (int)(getWidth() / zoom);
            int queryHeight = (int)(getHeight() / zoom);
            ArrayList<Entity> visible = structure.getRange(camX, camY, camX + queryWidth, camY + queryHeight);

            g.setColor(Color.BLACK);

            for (Entity e : visible) {
                int sx = (int)((e.getX() - camX) * zoom);
                int sy = (int)((e.getY() - camY) * zoom);
                int size = Math.max(1, (int)(4 * zoom));
                g.fillRect(sx, sy, size, size);
            }
        }

        // background box
        g.setColor(new Color(255, 255, 255, 255));
        g.fillRect(5, 5, 140, 130);

        // border
        g.setColor(Color.BLACK);
        g.drawRect(5, 5, 140, 130);

        // text are all red
        g.setColor(Color.RED);
        g.drawString("FPS: " + fps, 15, 25);
        g.drawString("Entities: " + structure.getCount(), 15, 45);
        g.drawString("Camera: (" + camX + ", " + camY + ")", 15, 65);
        String name = structure.getClass().getSimpleName();
        g.drawString("Structure: " + name, 15, 85);
        g.drawString( "Zoom: " + String.format("%.2f", zoom), 15, 105);

        g.drawString("Map: " + worldWidth + " x " + worldHeight, 15, 125);
    }
}