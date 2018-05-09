/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import model.Route;
import model.Package;

/**
 * @author Stijn Kluiters
 */
public class Grid extends JPanel {

    public static DrawPanel grid;

    public Grid() {
        grid = new DrawPanel("Farthest Insertion");
        this.setPreferredSize(new Dimension(355, 300));
        this.setLayout(new GridLayout(1, 3, 6, 0));
        this.add(grid);
        repaint();
    }

    public void setPoints(List<Package> points) {
        grid.setPoints(points);
    }

    public void setGrid(int x, int y) {
        grid.setWidthHeight(x, y);
    }

    public DrawPanel getDrawPanel() {
        return grid;
    }

}
