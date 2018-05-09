/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.Package;
import model.Route;

/**
 *
 * @author Stijn Kluiters
 */
public class DrawPanel extends JPanel {

    private final int PANEL_WIDTH = 333;
    private final int PANEL_HEIGHT = 300;

    private int width;
    private int height;
    private int incrementx;
    private int incrementy;
    public static String algorithm;
    private boolean end;

    private Package target;
    private BufferedImage image;
    private Route model;

    private List<int[]> drawList;
    private List<Package> subRoute;
    private List<Package> points;

    public DrawPanel(String algorithm) {
        this.algorithm = algorithm;
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        drawList = new ArrayList<>();
        width = 1;
        height = 1;
        end = false;

        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), algorithm));
    }

    public void setModel(Route model) {
        this.model = model;
    }

    public void setWidthHeight(int x, int y) {
        width = x;
        height = y;
        if (width == 0) {
            width = 1;
        }
        if (height == 0) {
            height = 1;
        }
    }

    public void toggleEnd() {
        this.end = !end;
    }

    public void update() {
        this.setDrawingPoints();
        this.createImage();
        this.render();
        try {
            Thread.sleep(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPoints(List<Package> points) {
        this.points = points;
    }

    private void render() {
        Graphics g = this.getGraphics();
        super.paint(g);
        g.drawImage(this.image, 0, 0, this);
        g.dispose();
    }

    private int[] getLoc(int incrementx, int incrementy, int hor, int ver) {
        int coords[] = new int[2];
        coords[0] = (hor * incrementx) + (incrementx / 2);
        coords[1] = (ver * incrementy) + (incrementy / 2);
        return coords;
    }

    private void drawGrid(Graphics g) {
            incrementx = this.getWidth() / width;
            int x = incrementx;
            incrementy = this.getHeight() / height;
            int y = incrementy;
            while (this.getWidth() - x > 20) {
                g.drawLine(x, 20, x, this.getHeight() - 10);
                x += incrementx;
            }
            while (this.getHeight() - y > 20) {
                g.drawLine(10, y, this.getWidth() - 10, y);
                y += incrementy;
            }
            // drawing all points on grid
            for (Package p : this.points) {
                int[] coordinates = this.getLoc(this.incrementx, this.incrementy, p.getX(), p.getY());
                g.fillRect(coordinates[0] - 4, coordinates[1] - 4, width, height);
                g.drawString(p.name, coordinates[0] + 7, coordinates[1] + 5);
            }
    }

    private void setDrawingPoints() {
        if (this.end) {
            this.subRoute = this.model.getOptimumRoute();
        } else {
            this.target = this.model.getTarget();
            this.subRoute = this.model.getSubroute();
        }
    }

    private void createImage() {
        image = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D imageGraphics = image.createGraphics();
        imageGraphics.setColor(Color.BLACK);
        this.drawGrid(imageGraphics);
        if (this.subRoute != null) {
            for (int i = 0; i < this.subRoute.size() - 1; i++) {
                Package next = this.subRoute.get(i + 1);
                Package current = this.subRoute.get(i);

                int[] nextCoordinates = this.getLoc(incrementx, incrementy, next.getX(), next.getY());
                int[] currentCoordinates = this.getLoc(incrementx, incrementy, current.getX(), current.getY());
                imageGraphics.setColor(Color.decode("#339933"));
                imageGraphics.setStroke(new BasicStroke(4));
                imageGraphics.drawLine(currentCoordinates[0], currentCoordinates[1], nextCoordinates[0], nextCoordinates[1]);
            }
        }

    }
    public String getAlgorithm() {
        return this.algorithm;
    }
}
