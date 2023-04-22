package com.xshape.vue.awt;


import com.xshape.modele.*;
import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;
import com.xshape.modele.awt.AwtRenderer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;


public class AwtConcreteWhiteBoard extends AwtAbstractWhiteBoard {


    private IRenderer renderer;
    private IShape selectedShape;
    private int prevX, prevY;
    Stack<Command> undoStackAwt;
    Stack<Command> redoStackAwt;
    private ToolGroupComponent contentWhiteBoard = new ToolGroupComposite();


    public IRenderer getRenderer() {
        return renderer;
    }

    public ToolGroupComponent getContentWhiteBoard() {
        return contentWhiteBoard;
    }


    public AwtConcreteWhiteBoard(AwtApplication app, int x, int y, int width, int height) {
        super(app, x, y, width, height);
        app.add(this);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(selectedShape==null){
                    for (ToolGroupComponent c : contentWhiteBoard.getShapes()) {
                        if (c.getShape().IsArea(e.getX(),e.getY())) {
                            selectedShape = c.getShape();
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
                    Command command = new DrawShapeCommand(selectedShape, e.getX(), e.getY(), contentWhiteBoard);
                    undoStackAwt.push(command);
                    redoStackAwt.clear();
                    selectedShape = null;
                    System.out.println("undoStackAwt size: " + undoStackAwt.size());
                    System.out.println("redoStackAwt size: " + redoStackAwt.size());
                    update(undoStackAwt, redoStackAwt);
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

    public void update(Stack<Command> undoStackAwt, Stack<Command> redoStackAwt) {
        System.out.println("pfffffffffffffff");
        this.undoStackAwt = undoStackAwt;
        this.redoStackAwt = redoStackAwt;
        for (Command c : undoStackAwt) {
            System.out.println(((DrawShapeCommand) c).getCopy());
            if (c instanceof DrawShapeCommand) {
                IShape shape = ((DrawShapeCommand) c).getCopy();
                if (!contentWhiteBoard.contains(shape)) {
                    contentWhiteBoard.add(new Tool(shape));
                }
            }
        }
        repaint();
    }









    @Override
    public void paint(Graphics g) {
        IRenderer r = new AwtRenderer(g);
        this.renderer = r;
        super.paint(g);
        for (ToolGroupComponent c : contentWhiteBoard.getShapes()) {
            c.getShape().setRenderer(r);
            c.getShape().draw();
        }
    }

}
