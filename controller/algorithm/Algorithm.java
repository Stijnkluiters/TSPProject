/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.algorithm;

import java.util.List;
import java.util.concurrent.Callable;
import model.Route;

/**
 *
 * @author Stijn Kluiters
 */
public interface Algorithm<T> {
  
  public Route getRoute();
  public <T> List<T> doWork(List<T> packages);
  
}
