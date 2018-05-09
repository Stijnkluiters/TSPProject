/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stijn Kluiters
 */
public class Storage {
  
  private List<Package> grid;

  public Storage() {
    this.grid = new ArrayList<>();
  }
  
  public void addPackage(Package p) {
    this.grid.add(p);
  }
  
}
