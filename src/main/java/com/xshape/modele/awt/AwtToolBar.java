package com.xshape.modele.awt;

import com.xshape.vue.awt.AwtApplication;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class AwtToolBar extends Panel {

    public AwtToolBar(AwtApplication app, int x, int y, int width, int height) {

        setBackground(Color.CYAN);
        setBounds(x, y, width, height);
        app.add(this);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;


    }
}