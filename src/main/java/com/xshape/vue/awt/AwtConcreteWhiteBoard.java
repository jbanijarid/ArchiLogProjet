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
    private IShape selectedShape;
    private int prevX, prevY;


    public AwtConcreteWhiteBoard(AwtApplication app, int x, int y, int width, int height) {
        super(app, x, y, width, height);
        app.add(this);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(selectedShape==null){
                    for (IShape shape : MyShapes) {
                        if (shape.IsArea(e.getX(),e.getY())) {
                            selectedShape = shape;
                            prevX = e.getX();
                            prevY = e.getY();
                            break;
                        }
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (selectedShape!=null){
                    selectedShape.setPosition(e.getX(), e.getY());
                    selectedShape = null;
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedShape != null) {
                    int dx = e.getX() - prevX;
                    int dy = e.getY() - prevY;
                    selectedShape.setPosition(e.getX(), e.getY()); // mettre à jour la position de la forme
                    prevX = e.getX();
                    prevY = e.getY();
                    repaint();
                }
            }
        });
    }


    @Override
    public void paint(Graphics g) {
        IRenderer r = new AwtRenderer(g);
        super.paint(g);
        for (IShape s : this.MyShapes) {
            s.setRenderer(r);
            s.draw();
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
