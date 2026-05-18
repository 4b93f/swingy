package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;

public class GuiConstants {

    public static final Color BG      = new Color(20, 20, 30); // Dark blue background
    public static final int   COL_W   = 400;
    public static final int   COL_H   = 400;
    public static final int   BTN_H   = 40;
    public static final int   FIELD_H = 35;

    public static JPanel centeredColumn() {
        JPanel column = new JPanel(new GridBagLayout());
        column.setBackground(BG);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(BG);
        inner.setPreferredSize(new Dimension(COL_W, COL_H));

        column.add(inner);
        return inner;
    }

    public static JLabel makeTitle(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Monospaced", Font.BOLD, 36));
        label.setForeground(new Color(100, 180, 255));
        label.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        return label;
    }

    public static JButton makeButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(COL_W, BTN_H));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    public static JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Monospaced", Font.PLAIN, 13));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public static <T extends JComponent> T sizeField(T field) {
        field.setMaximumSize(new Dimension(COL_W, FIELD_H));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        return field;
    }
}
