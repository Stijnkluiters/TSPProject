/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Stijn Kluiters
 */
public class InitPanel extends JPanel {

    private ArrayList<JComponent> InitBar = new ArrayList<>();
    private int amount;

    private AlgorithmSelector algorithmSelect;
    private JButton start;

    public InitPanel(ActionListener listener) {
        this.amount = 0;
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 165, 0));
        // init start button with a new listener and give it a name.
        JButton start = new JButton();
        start.setName("Start");
        start.setText("Start");
        start.addActionListener(listener);
        InitBar.add(start);



        algorithmSelect = new AlgorithmSelector();
        InitBar.add(algorithmSelect.getCb());

        // all buttons are identified with integers.
        // Whenever a button is created, the button gets an internal number. Which is being reused.
        InitBar.add(new JLabel("Aantal ophaalpunten huidige simulatie: " + amount));
        InitBar.stream().forEach((c) -> {
            this.add(c);
        });
    }


    public JLabel getLabel(int i) {
        return ((JLabel) InitBar.get(i));
    }

    public JButton getButton(int i) {
        return ((JButton) InitBar.get(i));
    }

    public JTextField getTextField(int i) {
        return ((JTextField) InitBar.get(i));
    }

    public void setAmount(int a) {
        this.amount = a;
    }
    
    public void updateAmount() {
        this.getLabel(2).setText("Aantal ophaalpunten huidige simulatie: " + this.amount);
    }

}
