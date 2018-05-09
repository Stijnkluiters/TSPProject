/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.gui;

import java.util.ArrayList;
import java.util.List;
import view.Details;
import view.DrawPanel;

/**
 *
 * @author Stijn Kluiters
 */
public class PanelHandler {

  private List<DrawPanel> panels;
  private Details details;

  public PanelHandler() {
    this.panels = new ArrayList<>();
  }

  public void addPanel(DrawPanel panel) {
    this.panels.add(panel);
  }

  public void setDetailsPanel(Details details) {
    this.details = details;
  }

  public void updatePanels() {
    for (DrawPanel p : panels) {
      p.update();
    }
  }

  public void endPanels() {
    int i = 0;
    for (DrawPanel p : panels) {
      if (i == 0) {
        p.toggleEnd();
        System.out.println("--(!) ENDING");
      }
      i++;
      this.details.updateLabels();
    }
    // this creates a bug where the points wil reset to the starting position
//    this.updatePanels();
  }
}
