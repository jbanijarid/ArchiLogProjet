package com.xshape.vue.awt;


import com.xshape.modele.IRenderer;
import com.xshape.modele.IShape;
import com.xshape.modele.Polygone;
import com.xshape.modele.Rectangle;
import com.xshape.modele.awt.AwtRenderer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class AwtConcreteWhiteBoard extends AwtAbstractWhiteBoard {

 ArrayList<IShape> MyShapes = new ArrayList<>();


    public AwtConcreteWhiteBoard(AwtApplication app, int x, int y, int width, int height) {
        super(app, x, y, width, height);
        app.add(this);
    }


    @Override
    public void paint(Graphics g) {
        System.out.println("ahhhhhhhhhhhhhhh");
        IRenderer r = new AwtRenderer(g);
        super.paint(g);
        for (IShape s : this.MyShapes) {
            s.setRenderer(r);
            s.draw();
            System.out.println(s);
        }
    }

    public void addShape(IShape shape) {
        IRenderer r = new AwtRenderer(getGraphics());
        shape.setRenderer(r);
        this.MyShapes.add(shape);
        repaint();
    }

    public ArrayList<IShape> getShapes(){
        return this.MyShapes;
    }
}
