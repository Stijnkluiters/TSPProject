package controller.algorithm;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import model.Route;

import model.Package;

/**
 *
 * @author Stijn Kluiters
 */
public class FarthestInsertion implements Algorithm<List<Package>> {

  private List<Package> route;
  private Package start;
  private final Route model;
  private List<Package> subroute;

  public FarthestInsertion(Route route) {
    this.model = route;
  }

  @Override
  public Route getRoute() {
    return this.model;
  }

  /**
   * This method returns the most optimal route, calculated with the farthest
   * insertion algorithm. The idea is to find the hull of the points, by using
   * the Farthest Selection algorithm. And then inserting is into the new route
   * with a Nearest Insertion. This way you'll always have a circle.
   *
   * @return List
   */
  public List<Package> getNewRoute() {
    List<Package> controlList = new ArrayList<>(this.route);
    this.subroute = new ArrayList<>();
    // Add start point to subroute
    this.subroute.add(this.start);
    //Add farthest from startpoint to subroute
    Point startPoint = new Point(this.start.getX(), this.start.getY());
    Package farthestFromStart = this.findFarthest(startPoint, this.route);
    this.subroute.add(farthestFromStart);
    // Remove the startpoint and farthest from it from the controllist
    for (Package s : this.subroute) {
      controlList.remove(s);
    }
    // Add farthest from the subroute's line or hull to subroute
    Point medPointOfSubroute;
    Package farthestFromSubroute;
    while (!controlList.isEmpty()) {
      // Get the median point for the subroute (x, y)
      medPointOfSubroute = this.getMedPoint(this.subroute);
      // Get the farthest Object from the found point
      farthestFromSubroute = this.findFarthest(medPointOfSubroute, controlList);
      // Insert package into subroute
      this.subroute = this.insert(this.subroute, farthestFromSubroute);
      // Remove from the control list
      controlList.remove(farthestFromSubroute);
    }
    
    System.out.println("Total time for Farthest Insertion in Milli seconds: " + this.model.getRunningTime());
    this.model.updateRoute(this.getClass().getSimpleName(), this.subroute, null);
    return this.subroute;
  }

  /**
   * This method is used to find the best possible spot for 
   * the given Package in the given route.
   * It works by injecting the package into the given route, 
   * checking the total distance, and then injecting it into 
   * the next position in the list and so on...
   * This will find the route with the minimum possible arc.
   * 
   *
   * @param sub List<Package>
   * @param p Package
   * @return List<Package>
   */
  private List<Package> insert(List<Package> route, Package p) {
    double distance = Double.MAX_VALUE;
    Package subject = p;
    List<Package> minimumArc = null;
    List<Package> temp = new ArrayList<>(route);
    for(int i = 1; i < temp.size(); i++) {
      temp.add(i, subject);
//      System.out.println(temp);
      double tempDistance = this.getDistance(temp);
      if(tempDistance < distance) {
        distance = tempDistance;
        minimumArc = new ArrayList<>(temp);
      }
      temp.remove(subject);
      temp.removeAll(Collections.singleton(null));
    }
    return minimumArc;
  }

  /**
   * Get the Package that is the farthest away from the specified point, from the given route.
   * @param p Point
   * @param route List<Package>
   * @return Package
   */
  private Package findFarthest(Point p, List<Package> route) {
    double distance = 0;
    int farthestIndex = 0;
    List<Package> temp = route;

    for (int i = 0; i < temp.size(); i++) {
      Package current = temp.get(i);
      double tempDistance = this.getDistance(p.x, current.getX(), p.y, current.getY());
      if (tempDistance > distance) {
        this.model.updateRoute(this.getClass().getSimpleName(), this.subroute, current);
        distance = tempDistance;
        farthestIndex = i;
      }
    }

    Package farthest = temp.get(farthestIndex);
    return farthest;
  }
  
  /**
   * Gets the median point from the given subroute.
   * @param subroute List<Package>
   * @return Point
   */
  private Point getMedPoint(List<Package> subroute) {
    int medX = 0;
    int medY = 0;
    Point p = new Point();

    for (Package s : subroute) {
      medX += s.getX();
      medY += s.getY();
    }
    // create average point
    medX = (medX / subroute.size());
    medY = (medY / subroute.size());

    p.setLocation(medX, medY);
    return p;
  }

  /**
   * Calculates the distance between a and b using their x and y coordinates
   *
   * @param x1 double
   * @param x2 double
   * @param y1 double
   * @param y2 double
   * @return double
   */
  private double getDistance(double x1, double x2, double y1, double y2) {
    double distance = Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2);
    return Math.sqrt(distance);
  }
  
  /**
   * Get the total distance of the given route.
   * @param route List<Package>
   * @return double
   */
  private double getDistance(List<Package> route) {
    double distance = 0;
    List<Package> temp = new ArrayList<>(route);
    for (int i = 0; i < temp.size()-1; i++) {
      Package current = temp.get(i);
      Package next = temp.get(i+1);
      distance += this.getDistance(current.getX(), next.getX(), current.getY(), next.getY());
    }
    return distance;
  }

  /**
   * Removes the duplicates by using a Linked Hash Set
   *
   * @param packages List<Package>
   * @return List<Package>
   */
  private List<Package> removeDuplicates(List<Package> packages) {
    LinkedHashSet lhs = new LinkedHashSet();
    List<Package> temp = packages;
    lhs.addAll(temp);
    temp.clear();
    temp.addAll(lhs);
    lhs.clear();
    lhs = null;
    return temp;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> List<T> doWork(List<T> packages) {
    this.route = new ArrayList<>((List<Package>) packages);
    this.start = this.route.get(0);
    return (List<T>) this.getNewRoute();
  }

}
