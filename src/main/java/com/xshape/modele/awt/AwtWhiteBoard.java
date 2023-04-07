package com.xshape.modele.awt;

import com.xshape.modele.IRenderer;
import com.xshape.modele.Polygone;
import com.xshape.modele.Rectangle;
import com.xshape.vue.awt.AwtApplication;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class AwtWhiteBord extends Panel {


    private int startX, startY, endX, endY;
    private boolean isDrawing;

    public AwtWhiteBord(AwtApplication app, int x, int y, int width, int height) {

        setBackground(Color.white);
        setBounds(x, y, width, height);
        app.add(this);
        DrawRectangle();
    }

    public void DrawRectangle(){
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                isDrawing = true;
            }

            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
                endX = e.getX();
                endY = e.getY();

                Graphics g = getGraphics();
                IRenderer test = new AwtRenderer(g);
                Rectangle rectangle = new Rectangle(startX,startY, endX-startX,endY-startY, test);
                rectangle.draw();
            }
        });
    }

    public void paint(Graphics g) {

        /*
        IRenderer test = new AwtRenderer(g);
        Rectangle rectangle = new Rectangle(100,100, 150,125, test);
        rectangle.draw();

        Polygone test2 = new Polygone( 400,400, 70, 50, test);
        test2.draw();

         */



    }
}