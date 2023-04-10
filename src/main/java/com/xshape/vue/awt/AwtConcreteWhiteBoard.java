package com.xshape.vue.awt;


import com.xshape.modele.*;
import com.xshape.modele.awt.AwtRenderer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;


public class AwtConcreteWhiteBoard extends AwtAbstractWhiteBoard {

    private IShape selectedShape;
    private int prevX, prevY;
    Stack<Command> undoStackAwt;


    public AwtConcreteWhiteBoard(AwtApplication app, int x, int y, int width, int height, Stack<Command> undoStackAwt, Stack<Command> redoStackAwt) {
        super(app, x, y, width, height);
        app.add(this);
        this.undoStackAwt=undoStackAwt;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(selectedShape==null){
                    for (Command c : undoStackAwt) {
                        if(c instanceof DrawShapeCommand){
                            if (((DrawShapeCommand) c).getCopy().IsArea(e.getX(),e.getY())) {
                                selectedShape = ((DrawShapeCommand) c).getCopy();
                                prevX = e.getX();
                                prevY = e.getY();
                                break;
                            }
                        }
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (selectedShape!=null){
                    selectedShape.setPosition(e.getX(), e.getY());
                    Command command = new DrawShapeCommand(selectedShape, e.getX(), e.getY());
                    undoStackAwt.push(command);
                    redoStackAwt.clear();
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
        for (Command c : undoStackAwt) {
            if(c instanceof DrawShapeCommand){
                ((DrawShapeCommand) c).getCopy().setRenderer(r);
                ((DrawShapeCommand) c).getCopy().draw();
            }
        }
    }

}
