package controller.algorithm;

import controller.service.ThreadListener;
import java.util.List;
import model.Package;

/**
 *
 * @author Stijn Kluiters
 */
public class Worker extends Thread {

  private String name;
  private Algorithm a = null;
  private List<Package> packages;
  private ThreadListener listener;

  public Worker(Algorithm a, String name, List<Package> packages) {
    this.a = a;
    this.name = name;
    this.packages = packages;
  }

  public Algorithm getAlgorithm() {
    return this.a;
  }
  
  public void setListener(ThreadListener listener) {
    this.listener = listener;
  }
  
  public void notifyListener() {
    this.listener.notifyOnDone(this);
  }

  @Override
  public void run() {
    try {
      this.doRun();
    } finally {
      this.notifyListener();
    }
  }
  
  public void doRun() {
    this.a.doWork(packages);
  }

}
