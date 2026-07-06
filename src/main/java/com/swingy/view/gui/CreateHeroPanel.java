package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;
import com.swingy.controller.GuiController;

public class CreateHeroPanel extends JPanel {

    private final JTextField      nameField;
    private final JComboBox<String> classBox;
    private final JLabel          errorLabel;

    public CreateHeroPanel(GuiController controller) {
        setLayout(new BorderLayout());
        setBackground(GuiConstants.BG);

        add(GuiConstants.makeTitle("NEW HERO"), BorderLayout.NORTH);

        nameField  = GuiConstants.sizeField(new JTextField());
        classBox   = GuiConstants.sizeField(new JComboBox<>(new String[]{"Warrior", "Mage", "Rogue", "Tank"}));
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton create = GuiConstants.makeButton("Create Hero");
        JButton back   = GuiConstants.makeButton("Back");

        create.addActionListener(e -> controller.onCreateHero(nameField.getText().trim(), (String) classBox.getSelectedItem()));
        back.addActionListener(e -> controller.onBack());

        JPanel column = GuiConstants.centeredColumn();
        addheroColumnContent(column, create, back);

        add(column, BorderLayout.CENTER);
    }

    private void addheroColumnContent(JPanel column, JButton create, JButton back) {
        column.add(Box.createVerticalGlue());
        column.add(GuiConstants.makeLabel("Hero Name:"));
        column.add(Box.createVerticalStrut(5));
        column.add(nameField);
        column.add(Box.createVerticalStrut(15));
        column.add(GuiConstants.makeLabel("Class:"));
        column.add(Box.createVerticalStrut(5));
        column.add(classBox);
        column.add(Box.createVerticalStrut(20));
        column.add(create);
        column.add(Box.createVerticalStrut(10));
        column.add(errorLabel);
        column.add(Box.createVerticalStrut(10));
        column.add(back);
        column.add(Box.createVerticalGlue());
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }

    public void clearError() {
        errorLabel.setText(" ");
    }
}
