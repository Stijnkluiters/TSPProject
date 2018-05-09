/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.JPanel;

import model.Route;
import model.Package;

/**
 *
 * @author Stijn Kluiters
 */
public class AlgoritmFrame extends JPanel {

    private InitPanel initPanel;
    private Grid grid;
    private Details details;

    public AlgoritmFrame(ActionListener listener) {
        initPanel = new InitPanel(listener);
        grid = new Grid();
        details = new Details();

        this.setPreferredSize(new Dimension(600, 400));
        this.setLayout(new FlowLayout());
        this.add(initPanel);
        this.add(grid);
        this.add(details);
    }

    public void setPoints(List<Package> points) {
        grid.setPoints(points);
    }

    public void updateRoute(Route[] routes) {
        for (Route r : routes) {
            r.resetTime();
        }
    }

    public Details getDetails() {
        return this.details;
    }

    public Component getUpperBarComponent(int i) {
        return initPanel.getComponent(i);
    }

    public InitPanel getInitPanel() {
        return this.initPanel;
    }

    public void setGrid(int x, int y) {
        grid.setGrid(x, y);
    }

    public DrawPanel getDrawPanel() {
        return grid.getDrawPanel();
    }

}
