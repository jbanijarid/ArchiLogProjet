package com.xshape.vue.awt;

import com.xshape.modele.*;
import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Rectangle;
import com.xshape.modele.awt.AwtRenderer;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

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
    IRenderer renderer;
    private Image trashLabel;
    private IFactory factory = new Factory();
    AwtBuilder builder;

    AwtConcreteToolBar(AwtApplication app, int x, int y, int width, int height, AwtBuilder builder){
        super(app, x, y, width, height);
        this.builder = builder;
        IShape r = factory.createRectangle(this.pos_x, this.pos_y, this.width, this.height, this.renderer);
        IShape p = factory.createPolygone(this.pos_x+ this.radious, this.pos_y, this.radious, 6, this.renderer);
        ToolGroupComponent recTool = new Tool(r);
        ToolGroupComponent polyTool = new Tool(p);
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
                    for (ToolGroupComponent c : builder.toolbarContent.getFormes().getShapes()) {
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
                }
                if (selectedShape!=null && !getBounds().contains(e.getPoint())) {
                    selectedShape.setPosition(prevX, prevY);
                }
                repaint();
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

    public boolean inTrashLabel(int ex, int ey){
        if (trashLabel != null && ex >= (getWidth() - trashLabel.getWidth(null)) / 2
                && ex <= (getWidth() - trashLabel.getWidth(null)) / 2 + trashLabel.getWidth(null)
                && ey >= getHeight() - trashLabel.getHeight(null) - 10
                && ey <= getHeight() - 10) {
            return true;
        }
        return false;
    }

    void addTool(ToolGroupComponent tool){
        builder.toolbarContent.getFormes().add(tool);
        repositionTools();
        repaint();
    }

    public void repositionTools() {
        current_y = pos_y;
        for (ToolGroupComponent tool : this.builder.toolbarContent.getFormes().getShapes()) {
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
        for (ToolGroupComponent c : this.builder.toolbarContent.getFormes().getShapes()) {
            c.getShape().setRenderer(r);
            c.getShape().draw();
        }
        if (trashLabel != null) {
            g.drawImage(trashLabel, (getWidth() - trashLabel.getWidth(null)) / 2, getHeight() - trashLabel.getHeight(null) - 10, null);
        }
    }
    public int getCurrent_y() {
        return current_y;
    }
    public void setCurrent_y(int current_y) {
        this.current_y = current_y;
    }
}
