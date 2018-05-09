/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.gui;

import controller.service.TSP;
import model.TSPFrame;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.Route;
import model.Package;
import view.DrawPanel;

/**
 *
 * @author StijnKluiters
 */
public class GuiController implements ActionListener {

    private TSPFrame frame;
    private TSP tsp;
    private Route[] routes;

    private List<Package> order;

    public GuiController() {
        routes = new Route[1];
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("Something went wrong while setting the look and feel");
        }

        frame = new TSPFrame(this);
    }

    public List<Package> getRandomOrder(int productCount, int maxBoundary) {
        // n & amount is the amount of products on the shelfs
        final int MAX = maxBoundary;
        Random random = new Random();
        List<Package> order = new ArrayList<>();

        for (int i = 0; i < productCount; i++) {
            int x = 0;
            int y = 0;
            int unique = 0;
            if (i != 0) {
                x = random.nextInt(MAX);
                y = random.nextInt(MAX);
            }
            Package p = new Package(x, y);
            p.name = ("Package " + i);

            // unique points in the algorithm, no points can be placed at the same position, ( NOT ON X AND Y )
            if (!order.isEmpty()) {
                // this funtionality is used for unique points on the screen,
                // no points can be placed at the same position
                for (Package o : order) {
                    // if the package position is NOT the same as the new order position we can add a new order
                    if (p.getX() != o.getX() || p.getY() != o.getY()) {
                        unique++;
                    }
                }
                if (unique == order.size()) {
                    order.add(p);
                }
            } else {
                order.add(p);
            }
        }

        return order;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Start".equals(((Component) (e.getSource())).getName())) {
                int width = 5;
                int height = 5;

                frame.setGrid(0,0);
                this.order = this.getRandomOrder(4, 4);
                frame.setRandomOrder(this.order);
                System.out.println("start button has been pressed");
                frame.setGrid(width, height);

                System.out.println("--(!) INITIALIZING HANDLER");
                PanelHandler handler = this.initPanelHandler();

                System.out.println("--(!) INITIALIZING TSP");
                TSP tsp = this.initTSP(this.order, handler);

                System.out.println("--(!) ADDING MODELS TO DRAWPANELS");
                this.setPanelModels();

                tsp.serve();
                System.out.println("--(!) SETTINGS TSP TO NULL");
                frame.updateRoute(routes);
                tsp = null;

        }
    }

    @SuppressWarnings("unchecked")
    private TSP initTSP(List<Package> order, PanelHandler handler) {
        TSP tsp = new TSP(order, handler);
        this.routes[0] = tsp.getWorker().getAlgorithm().getRoute();
        return tsp;
    }

    private PanelHandler initPanelHandler() {
        PanelHandler handler = new PanelHandler();
        handler.addPanel(this.frame.getDrawPanel());
        handler.setDetailsPanel(this.frame.getDetails());
        return handler;
    }

    private void setPanelModels() {
        this.frame.getDrawPanel().setModel(this.routes[0]);
        this.frame.getDetails().setModel(this.routes[0]);
    }

    private void startTSP() {
        this.tsp.serve();
    }

}
