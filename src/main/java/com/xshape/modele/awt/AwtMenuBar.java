package com.xshape.modele.awt;

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

    public AwtMenuBar(AwtApplication app) {

        setBackground(Color.gray);
        setBounds(0,23,800,60);

        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        add(undoButton);
        add(redoButton);
        add(saveButton);
        add(loadButton);

        addSeparator();

        app.add(this, BorderLayout.NORTH);

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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