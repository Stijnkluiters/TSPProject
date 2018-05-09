/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.algorithm.FarthestInsertion;
import model.Route;

/**
 * @author Stijn Kluiters
 */
public class Details extends JPanel {

    private Route model;

    private JLabel score;
    private JLabel initDisplay;

    public Details() {
        this.setPreferredSize(new Dimension(1000, 300));
        this.setLayout(new GridLayout(2, 3, 10, 0));
        this.score = new JLabel( "Score: 0");
        this.score.setName("score");
        this.add(this.score);
        this.initDisplay = new JLabel("<html>Berekentijd huidige simulatie: 0<br>Afstand van berekende route: 0</html>");
        this.initDisplay.setName("initDisplay");
        this.add(this.initDisplay);
    }

    public void setModel(Route models) {
        this.model = models;
    }

    // TODO: Rework all the component names... create a getter for components. instead of using a loop with index.
    public void updateLabels() {

        String string = "<html>Berekentijd huidige simulatie: " + this.model.getRunningTime() + "<br>Afstand van berekende route: ";
        string += this.model.getRouteDistance(this.model.getOptimumRoute());
        this.initDisplay.setText(string + "</html>");
        this.score.setText("Score: " + this.model.getScore());
        repaint();
    }
}
