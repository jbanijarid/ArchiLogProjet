package com.xshape.vue.awt;

import com.xshape.modele.*;
import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;
import com.xshape.modele.Rectangle;
import com.xshape.modele.awt.AwtRenderer;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AwtConcreteToolBar extends AwtAbstractToolBar{

    private int pos_x = 30;
    private int pos_y = 10;
    private int width = 50;
    private int height = 30;
    private int radious = 25;
    private int prevX, prevY;

    private int current_y = pos_y;
    private IShape selectedShape;
    ToolGroupComponent selectedTool;
    ToolGroupComponent tools = new ToolGroupComposite();
    IRenderer renderer;
    private Image trashLabel;
    private IFactory factory = new Factory();




    AwtConcreteToolBar(AwtApplication app, int x, int y, int width, int height){
        super(app, x, y, width, height);
        IShape r = factory.createRectangle(this.pos_x, this.pos_y, this.width, this.height, this.renderer);
        IShape p = factory.createPolygone(this.pos_x+ this.radious, this.pos_y, this.radious, 6, this.renderer);
        ToolGroupComponent recTool = new Tool(r);
        ToolGroupComponent polyTool = new Tool(p);
        addTool(recTool);
        addTool(polyTool);

        try {
            URL imageUrl = new URL("file:src/main/resources/com/xshape/delete.png");
            BufferedImage bufferedImage = ImageIO.read(imageUrl);
            trashLabel = bufferedImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        app.add(this);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(selectedShape==null){
                    for (ToolGroupComponent c : tools.getShapes()) {
                        if (c.getShape().IsArea(e.getX(),e.getY())) {
                            selectedShape = c.getShape();
                            selectedTool = c;
                            prevX = e.getX();
                            prevY = e.getY();
                            break;
                        }

                    }
                }
            }

            public void mouseReleased(MouseEvent e) {

                if (selectedShape!=null && getBounds().contains(e.getPoint())){
                    selectedShape.setPosition(e.getX(), e.getY());
                    repaint();
                }
                if (selectedShape!=null && !getBounds().contains(e.getPoint())) {
                    selectedShape.setPosition(prevX, prevY);
                    repaint();
                }

                // Supprime l'outil si la souris est relâchée sur l'image trashLabel
                if (trashLabel != null && e.getX() >= (getWidth() - trashLabel.getWidth(null)) / 2
                        && e.getX() <= (getWidth() - trashLabel.getWidth(null)) / 2 + trashLabel.getWidth(null)
                        && e.getY() >= getHeight() - trashLabel.getHeight(null) - 10
                        && e.getY() <= getHeight() - 10) {
                    if (selectedTool != null) {
                        tools.remove(selectedTool);
                        selectedTool = null;
                        current_y -= 75;
                        repositionTools();
                        repaint();
                    }
                }

                selectedShape = null;
                selectedTool = null;

            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedShape != null) {
                    selectedShape.setPosition(e.getX(), e.getY()); // mettre à jour la position de la forme
                    prevX = e.getX();
                    prevY = e.getY();
                    repaint();
                }
            }
        });

    }



    void addTool(ToolGroupComponent tool){
        tools.add(tool);
        repositionTools();
        repaint();
    }


    public ToolGroupComponent getTools() {
        return tools;
    }

    private void repositionTools() {
        current_y = pos_y;
        for (ToolGroupComponent tool : tools.getShapes()) {
            if (tool.getShape() instanceof Rectangle) {
                tool.getShape().setPosition(pos_x, current_y);
                tool.getShape().setHeight(height);
                tool.getShape().setWidth(width);
            }
            if (tool.getShape() instanceof Polygone) {
                tool.getShape().setPosition(pos_x + 20, current_y);
                tool.getShape().setRadius(radious);
            }
            current_y += 75;
        }
    }


    @Override
    public void paint(Graphics g) {
        IRenderer r = new AwtRenderer(g);
        this.renderer = r;
        super.paint(g);
        for (ToolGroupComponent c : this.getTools().getShapes()) {
            c.getShape().setRenderer(r);
            c.getShape().draw();
        }


        if (trashLabel != null) {
            g.drawImage(trashLabel, (getWidth() - trashLabel.getWidth(null)) / 2, getHeight() - trashLabel.getHeight(null) - 10, null);
        }
    }
}
