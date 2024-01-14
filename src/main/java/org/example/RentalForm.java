package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RentalForm {
    private JFrame mainFrame;
    private JPanel controlPanel;
    private JComboBox<String> carComboBox;
    private JCheckBox year2010CheckBox;
    private JCheckBox year2014CheckBox;
    private JCheckBox year2016CheckBox;
    private JCheckBox year2018CheckBox;
    private JCheckBox year2020CheckBox;
    private JCheckBox year2022CheckBox;

    private JList<String> cityList;
    private JTextField nameField;
    private JComboBox<String> ageRangeComboBox;
    private JTextField phoneNumberField;
    private JSlider satisfactionSlider;
    private JTextArea commentsTextArea;

    public RentalForm() {
        prepareGUI();
    }

    public static void main(String[] args) {
        RentalForm rentalForm = new RentalForm();
        rentalForm.showRentalForm();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Formular inchirieri");
        mainFrame.setSize(600, 600);
        mainFrame.setLayout(new GridLayout(10, 2));

        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(new JLabel("Selecteaza masina:"));
        carComboBox = new JComboBox<>(new String[]{"Audi", "BMW","Chevrolet","Dacia","Fiat","Ford","Jeep","Volkswagen"});
        mainFrame.add(carComboBox);

        mainFrame.add(new JLabel("Anul fabricatiei:"));
        year2010CheckBox = new JCheckBox("2010");
        year2014CheckBox = new JCheckBox("2014");
        year2016CheckBox = new JCheckBox("2016");
        year2018CheckBox = new JCheckBox("2018");
        year2020CheckBox = new JCheckBox("2020");
        year2022CheckBox = new JCheckBox("2022");



        JPanel yearPanel = new JPanel();
        yearPanel.add(year2010CheckBox);
        yearPanel.add(year2014CheckBox);
        yearPanel.add(year2016CheckBox);
        yearPanel.add(year2018CheckBox);
        yearPanel.add(year2020CheckBox);
        yearPanel.add(year2022CheckBox);

        mainFrame.add(yearPanel);

        mainFrame.add(new JLabel("Seleteaza orasul din care ridici masina:"));
        cityList = new JList<>(new String[]{"Timisoara", "Cluj", "Bucuresti", "Oradea", "Alba-Iulia", "Iasi"});
        mainFrame.add(new JScrollPane(cityList));

        mainFrame.add(new JLabel("Nume si prenume:"));
        nameField = new JTextField(5);
        mainFrame.add(nameField);

        mainFrame.add(new JLabel("Selecteaza varsta:"));
        ageRangeComboBox = new JComboBox<>(new String[]{"18-25", "25-35", "35-50", "50-61"});
        mainFrame.add(ageRangeComboBox);

        mainFrame.add(new JLabel("Numar de telefon:"));
        phoneNumberField = new JTextField(5);
        mainFrame.add(phoneNumberField);

        mainFrame.add(new JLabel("Sunteti multumit de optiuni?"));
        satisfactionSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        satisfactionSlider.setMajorTickSpacing(20);
        satisfactionSlider.setMinorTickSpacing(5);
        satisfactionSlider.setPaintTicks(true);
        satisfactionSlider.setPaintLabels(true);
        mainFrame.add(satisfactionSlider);

        mainFrame.add(new JLabel("Comentarii:"));
        commentsTextArea = new JTextArea(5, 20);
        commentsTextArea.setLineWrap(true);
        JScrollPane commentsScrollPane = new JScrollPane(commentsTextArea);
        mainFrame.add(commentsScrollPane);


        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataToJson();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        mainFrame.add(saveButton);
        mainFrame.add(cancelButton);

        mainFrame.setVisible(true);
    }

    private void saveDataToJson() {
        Map<String, Object> data = new HashMap<>();
        data.put("Masina", carComboBox.getSelectedItem());

        java.util.List<String> selectedYears = new java.util.ArrayList<>();
        if (year2010CheckBox.isSelected()) {
            selectedYears.add("2010");
        }
        if (year2014CheckBox.isSelected()) {
            selectedYears.add("2014");
        }
        if (year2016CheckBox.isSelected()) {
            selectedYears.add("2016");
        }
        if (year2018CheckBox.isSelected()) {
            selectedYears.add("2018");
        }
        if (year2020CheckBox.isSelected()) {
            selectedYears.add("2020");
        }
        if (year2022CheckBox.isSelected()) {
            selectedYears.add("2022");
        }
        data.put("An", selectedYears);
        data.put("Oras", cityList.getSelectedValue());
        data.put("Nume", nameField.getText());
        data.put("Varsta", ageRangeComboBox.getSelectedItem());
        data.put("Numar de telefon", phoneNumberField.getText());
        data.put("Satisfactie", satisfactionSlider.getValue());
        data.put("Comentarii", commentsTextArea.getText());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(data);

            try (FileWriter fileWriter = new FileWriter("rental_data.json", true)) {
                fileWriter.write(json + "\n");
            }

            JOptionPane.showMessageDialog(mainFrame, "Datele au fost salvate cu succes!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Eroare la salvarea datelor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRentalForm() {
        mainFrame.setVisible(true);
    }
}
