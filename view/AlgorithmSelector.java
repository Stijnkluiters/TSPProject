package view;

import com.sun.deploy.util.ArrayUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AlgorithmSelector extends JComboBox implements ActionListener {

    private JComboBox cb;

    private static String selectedValue;

    private static String[] algorithms = { "Selecteer uw optie" , "Brute force", "Farthest insertion", "Nearest neighbour", "Random" };

    public AlgorithmSelector() {
        cb = new JComboBox(algorithms);
        cb.addActionListener(this);
    }

    public JComboBox getCb() {
        return cb;
    }

    public static String getSelectedValue(){
        // convert the options array to list object
        // so we can check if the selected value is inside the option list
        List<String> list = new ArrayList(Arrays.asList(algorithms));
        // remove the first option of the list, we assume that the first option is the one without a value. Used for more user friendly interface.
        list.remove(0);

        if(!list.contains(selectedValue)) {
            JOptionPane.showMessageDialog(null, "Er is geen algoritme geselecteerd", "Foutmelding", JOptionPane.INFORMATION_MESSAGE);
            return "";
        }
        return selectedValue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        this.selectedValue = (String)cb.getSelectedItem();

    }

    public String[] getAlgorithms() {
        return algorithms;
    }
}
