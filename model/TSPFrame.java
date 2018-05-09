/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import view.AlgoritmFrame;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import view.Details;
import view.DrawPanel;

/**
 *
 * @author Stijn Kluiters
 */
public class TSPFrame extends JFrame {

    private List<Canvas> grids = new ArrayList<>();
    private AlgoritmFrame AlgoritmFrame;
    private List<Package> points;

    public TSPFrame(ActionListener listener) throws HeadlessException {
        AlgoritmFrame = new AlgoritmFrame(listener);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1050, 700);
        this.setTitle("TSP-Simulator");
        this.setLayout(new BorderLayout());
        this.add(AlgoritmFrame, BorderLayout.CENTER);
        this.AlgoritmFrame.getInitPanel().setAmount(0);
        this.AlgoritmFrame.getInitPanel().updateAmount();
        this.setResizable(false);
        this.setVisible(true);
    }

    public void updateRoute(Route[] routes) {
        AlgoritmFrame.updateRoute(routes);

        repaint();
    }

    public Component getUpperBarComponent(int i) {
        return AlgoritmFrame.getUpperBarComponent(i);
    }

    public void setGrid(int x, int y) {
        AlgoritmFrame.setGrid(x, y);
        repaint();
    }

    public DrawPanel getDrawPanel() {
        return AlgoritmFrame.getDrawPanel();
    }

    public Details getDetails() {
        return this.AlgoritmFrame.getDetails();
    }

    public void setRandomOrder(List<Package> order) {
        this.points = order;
        this.AlgoritmFrame.setPoints(points);
        this.AlgoritmFrame.getInitPanel().setAmount(points.size());
        this.AlgoritmFrame.getInitPanel().updateAmount();

    }

}
