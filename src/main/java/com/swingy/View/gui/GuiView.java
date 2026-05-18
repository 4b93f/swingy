package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;

public class GuiView extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel     cards      = new JPanel(cardLayout);

    public GuiView() {
        setTitle("Swingy");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(cards);
    }

    public void addCard(JPanel panel, String name) {
        cards.add(panel, name);
    }

    public void showCard(String name) {
        cardLayout.show(cards, name);
    }
}
