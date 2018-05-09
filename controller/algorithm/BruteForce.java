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
public class BruteForce implements Algorithm<List<Package>> {

  private final Route route;

  /**
   * A custom class for Permutation Lists
   *
   * @param <T> Generic
   *
   * @author Stijn Kluiters
   */
  public class PermutationList<T> extends ArrayList<List<T>> {

    private Route route;

    public PermutationList(Route route) {
      this.route = route;
    }

    /**
     * Returns a list of all possible combinations of the list you gave it.
     *
     * @param list List
     * @return A list of lists Permutation
     *
     * @author Stijn Kluiters
     */
    @SuppressWarnings("unchecked")
    public List<List<T>> getPermutations(List<T> list) {
      this.permute(list, 1);

      System.out.println("Total time for Brute Force in Milli seconds: " + this.route.getRunningTime());
      return Collections.unmodifiableList(this);
    }

    /**
     * This method creates a permutation of the list you gave it, by swapping
     * <br>
     * two items at the specified indexes. <br>
     * This method uses Recursion which can be quite Stack heavy <br><br>
     *
     * For more information about Recursion: <br>
     * <a href="https://en.wikipedia.org/wiki/Recursion_%28computer_science%29">Recursion
     * (Computer Science)</a>
     *
     * @param list List<T>
     * @param startIndex Integer
     *
     * @author Stijn Kluiters
     */
    private void permute(List<T> list, int startIndex) {
      if (startIndex == (list.size() - 1)) {
        List<T> temp = new ArrayList<>();
        for (T t : list) {
          temp.add(t);
        }
//        System.err.println(temp);
        this.route.updateRoute(this.getClass().getSimpleName(), temp, null);
        this.add(temp);
      } else {
        for (int i = startIndex; i < list.size(); i++) {
          this.swap(list, i, startIndex);
          this.permute(list, startIndex + 1);
          this.swap(list, i, startIndex);
        }
      }
    }

    /**
     * This is the method that swaps two items at the specified indexes. <br>
     * It works by setting the Object passed to it at the specified index
     * (subject) <br>
     * It is optional to use a second operation (which I used) to obtain the
     * second Object (target) <br>
     *
     * @param list List<T>
     * @param subject Integer
     * @param target Integer
     *
     * @author Stijn Kluiters
     */
    private void swap(List<T> list, int subject, int target) {
      final List<T> l = list;
      l.set(subject, l.set(target, list.get(subject)));
    }
  }

  public BruteForce(Route route) {
    this.route = route;
  }

  @Override
  public Route getRoute() {
    return this.route;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> List<T> doWork(List<T> packages) {
    PermutationList<Package> list = new PermutationList<>(this.route);



    return (List<T>) list.getPermutations((List<Package>) packages);
  }

}
