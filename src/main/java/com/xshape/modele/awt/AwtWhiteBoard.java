package com.xshape.modele.awt;

import com.xshape.vue.awt.AwtApplication;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class AwtWhiteBord extends Panel {

    public AwtWhiteBord(AwtApplication app) {

        //setBounds(app.getWidth() - this.getPreferredSize().width, app.getHeight() - this.getPreferredSize().height, 200, 200);
        // Créer un panneau pour les boutons
        //setBounds(app.getWidth() - this.getPreferredSize().width, app.getHeight() - this.getPreferredSize().height, 700, 508);7
        setBackground(Color.BLUE);
        setBounds(app.getWidth() - 650, app.getHeight() - 540, 650, 540);
        app.add(this);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Dessiner un cercle
        g2.setColor(Color.RED);
        Ellipse2D circle = new Ellipse2D.Double(50, 50, 100, 100);
        g2.fill(circle);

        // Dessiner un rectangle
        g2.setColor(Color.GREEN);
        Rectangle2D rect = new Rectangle2D.Double(150, 75, 100, 50);
        g2.fill(rect);

    }
}