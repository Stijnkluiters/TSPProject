/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.gui.PanelHandler;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Stijn Kluiters
 */
public class Route {

    private Map<List<Package>, Float> routesWithDistance;

    private long currentTime;
    private long oldTime;
    private long runningTime;

    private String algorithm;
    private List<Package> subroute;
    private Package target;
    private PanelHandler handler;

    public Route(PanelHandler handler) {
        this.handler = handler;
        this.routesWithDistance = new LinkedHashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T> void updateRoute(String from, List<T> route, T t) {
        this.algorithm = from;
        this.subroute = (List<Package>) route;
        this.target = (Package) t;
        // TIME
        this.oldTime = this.currentTime;
        this.currentTime = System.currentTimeMillis();

        if (this.oldTime != 0) {
            this.runningTime += (this.currentTime - this.oldTime);
        }

        // DISTANCE
        float totalDistance = 0;
        if (this.target == null) {
            totalDistance = this.getRouteDistance(subroute);
        }

        this.routesWithDistance.put(this.subroute, totalDistance);
        handler.updatePanels();
    }

    public float getScore() {
        final int MAX_TIME = 1500;
        final int MAX_DISTANCE = 100;

        float t = this.getRunningTime();
        float d = this.getRouteDistance(this.subroute);
        float timeScore = ((MAX_TIME - t) / MAX_TIME) * 100;
        float distanceScore = (MAX_DISTANCE - d);

        float finalScore = (timeScore + distanceScore) / 2;
        return finalScore;
    }

    public List<Package> getSubroute() {
        return this.subroute;
    }

    public Package getTarget() {
        return this.target;
    }

    public float getRouteDistance(List<Package> p) {
        // DISTANCE
        float totalDistance = 0;
        for (int i = 0; i < p.size() - 1; i++) {
            double x1 = p.get(i).getX();
            double y1 = p.get(i).getY();
            double x2 = p.get(i + 1).getX();
            double y2 = p.get(i + 1).getY();
            double temp = this.getDistance(x1, x2, y1, y2);

            totalDistance += temp;
        }
        return totalDistance;
    }

    public List<Package> getOptimumRoute() {
        Float optimum = Float.MAX_VALUE;
        List<Package> optimumList = null;
        for (Map.Entry<List<Package>, Float> entry : routesWithDistance.entrySet()) {
            List<Package> key = entry.getKey();
            Float value = entry.getValue();
            if (value < optimum) {
                optimum = value;
                optimumList = key;
            }
        }
        return optimumList;
    }

    public long getRunningTime() {
        return this.runningTime;
    }

    public Map getRoutesWithDistances() {
        return this.routesWithDistance;
    }

    public void resetTime() {
        this.oldTime = 0;
        this.currentTime = 0;
        this.runningTime = 0;
    }

    /**
     * get the distance between 2 points http://www.teacherschoice.com.au/maths_library/analytical%20geometry/alg_15.htm
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return sqrt(x²,y²)
     */
    private double getDistance(double x1, double x2, double y1, double y2) {
        return Math.hypot(x1-x2, y1-y2);
    }



}
