package controller.algorithm;

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
public class NearestNeighbour implements Algorithm<List<Package>> {

  private List<Package> subRoute = new ArrayList<>();
  private final Route route;

  public NearestNeighbour(Route route) {
    this.route = route;
  }

  @Override
  public Route getRoute() {
    return this.route;
  }

  /**
   * Creates a route based on the Nearest neighbour algorithm. The idea is
   * simply to get the nearest package to the first one, then add it to the
   * route, and then find the nearest package to the next one etc...
   *
   * @param packages List
   * @return List
   */
  public List<Package> getNewRoute(List<Package> packages) {

    List<Package> controlList = new ArrayList<>();
    controlList.addAll(packages);

    if (!this.subRoute.contains(packages.get(0))) {
      this.subRoute.add(packages.get(0));
    }
    
    for (Package p : this.subRoute) {
      controlList.remove(p);
    }

    int i = 0;
    while (!controlList.isEmpty()) {
      Package pa = this.findNearest(controlList, this.subRoute.get(i));
      this.subRoute.add(pa);
//      this.route.updateRoute(this.getClass().getSimpleName(), route, pa);
      controlList.remove(pa);
      i++;
    }

    this.route.updateRoute(this.getClass().getSimpleName(), this.subRoute, null);

    System.out.println("Total time for Nearest Neighbour in Milli seconds: " + this.route.getRunningTime());
    return this.removeDuplicates(this.subRoute);
  }

  /**
   * Finds the nearest package to p
   *
   * @param packages List
   * @param p Package
   * @return Package
   */
  public Package findNearest(List<Package> packages, Package p) {
    Package nearest = null;
    double distance = Double.MAX_VALUE;

    for (Package s : packages) {
      if (this.getDistance(p.getX(), s.getX(), p.getY(), s.getY()) < distance) {
        this.route.updateRoute(this.getClass().getSimpleName(), this.subRoute, s);
        distance = this.getDistance(p.getX(), s.getX(), p.getY(), s.getY());
        nearest = s;
      }
    }

    return nearest;
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
  @SuppressWarnings({"unchecked", "unchecked"})
  public <T> List<T> doWork(List<T> packages) {
    return (List<T>) this.getNewRoute((List<Package>) packages);
  }

}
