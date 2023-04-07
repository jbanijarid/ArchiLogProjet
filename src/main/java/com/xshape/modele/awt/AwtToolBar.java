package com.xshape.modele.awt;

import com.xshape.modele.IRenderer;
import com.xshape.modele.Polygone;
import com.xshape.modele.Rectangle;
import com.xshape.vue.awt.AwtApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class AwtToolBar extends Panel {

    public AwtToolBar(AwtApplication app, int x, int y, int width, int height) {

        setBackground(Color.CYAN);
        setBounds(x, y, width, height);


        ImageIcon deleteIcon = new ImageIcon("src/main/resources/com/xshape/delete.png");
        deleteIcon.setImage(deleteIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        JButton deleteButton = new JButton(deleteIcon);
        add(deleteButton, BorderLayout.SOUTH);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("delete");
            }
        });



        app.add(this);

    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;



    }
}