package com.xshape.vue.awt;

import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.IRenderer;
import com.xshape.modele.Rectangle;
import com.xshape.modele.awt.AwtRenderer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AwtConcreteWhiteBoard extends AwtAbstractWhiteBoard {

    private int startX, startY, endX, endY;
    private boolean isDrawing;


    public AwtConcreteWhiteBoard(AwtApplication app, int x, int y, int width, int height) {
        super(app, x, y, width, height);
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

    public void add(ToolGroupComponent draggedTool) {

    }
}
