/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.service;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Route;
import controller.algorithm.BruteForce;
import controller.algorithm.FarthestInsertion;
import controller.algorithm.NearestNeighbour;
import controller.algorithm.Worker;
import controller.gui.PanelHandler;
import java.util.ArrayList;

import model.Package;
import view.AlgorithmSelector;
import view.DrawPanel;
import view.Grid;

/**
 *
 * @author Stijn Kluiters
 */
public class TSP<T> implements Service, ThreadListener {

    private final Worker[] workers;
    private List<Thread> threads;
    private PanelHandler handler;

    public TSP(List<Package> packages, PanelHandler handler) {
        this.handler = handler;


        System.out.println(AlgorithmSelector.getSelectedValue());
        this.workers = new Worker[1];
        switch (AlgorithmSelector.getSelectedValue()) {
            case "Brute force":
                this.workers[0] = new Worker(new BruteForce(new Route(this.handler)), DrawPanel.algorithm, packages);
            break;
            case "Farthest insertion":
                this.workers[0] = new Worker(new FarthestInsertion(new Route(this.handler)), DrawPanel.algorithm, packages);
                break;
            case "Nearest neighbour":
                this.workers[0] = new Worker(new NearestNeighbour(new Route(this.handler)), DrawPanel.algorithm, packages);
                break;
            case "Random":
                // worker thread...
                break;
            default:
                return;
        }
    }

    public Worker[] getWorkers() {
        return this.workers;
    }

    public Worker getWorker() {
        return this.workers[0];
    }

    @Override
    public void serve() {
        this.threads = new ArrayList<>();
        for (Worker w : this.workers) {
            w.setListener(this);
            w.start();
        }
    }

    public List<Thread> getThreads() {
        return this.threads;
    }

    @Override
    public void notifyOnDone(Thread t) {
        this.handler.endPanels();
        t.interrupt();
        this.threads.remove(t);
    }

}
