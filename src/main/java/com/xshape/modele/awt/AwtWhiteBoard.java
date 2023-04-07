package com.xshape.modele.awt;

import com.xshape.modele.IRenderer;
import com.xshape.modele.Polygone;
import com.xshape.modele.Rectangle;
import com.xshape.vue.awt.AwtApplication;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class AwtWhiteBord extends Panel {

    public AwtWhiteBord(AwtApplication app, int x, int y, int width, int height) {

        setBackground(Color.white);
        setBounds(x, y, width, height);
        app.add(this);
    }

    public void paint(Graphics g) {

        IRenderer test = new AwtRenderer(g);
        Rectangle rectangle = new Rectangle(100,100, 150,125, test);
        rectangle.draw();

        Polygone test2 = new Polygone( 400,400, 70, 50, test);
        test2.draw();


    }
}