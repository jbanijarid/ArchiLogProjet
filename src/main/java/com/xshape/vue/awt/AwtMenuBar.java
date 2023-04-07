package com.xshape.vue.awt;

import com.xshape.vue.awt.AwtApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class AwtMenuBar extends JToolBar {

    public AwtMenuBar(AwtApplication app, int x, int y, int width, int height) {

        setBackground(Color.gray);
        setBounds(x,y,width,height);

        ImageIcon undoIcon = new ImageIcon("src/main/resources/com/xshape/undo.png");
        ImageIcon redoIcon = new ImageIcon("src/main/resources/com/xshape/redo.png");
        ImageIcon saveIcon = new ImageIcon("./src/main/resources/com/xshape/save.png");
        ImageIcon loadIcon = new ImageIcon("src/main/resources/com/xshape/load.png");

        int iconWidth = 24; // largeur des icônes des boutons
        int iconHeight = 24; // hauteur des icônes des boutons
        undoIcon.setImage(undoIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));
        redoIcon.setImage(redoIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));
        saveIcon.setImage(saveIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));
        loadIcon.setImage(loadIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));

        JButton undoButton = new JButton(undoIcon);
        JButton redoButton = new JButton(redoIcon);
        JButton saveButton = new JButton(saveIcon);
        JButton loadButton = new JButton(loadIcon);

        add(undoButton);
        add(redoButton);
        add(saveButton);
        add(loadButton);

        addSeparator();

        app.add(this, BorderLayout.NORTH);

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ehhhhhhhhhhhhhhhh");
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }


}