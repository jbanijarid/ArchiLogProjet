package com.xshape.vue.awt;

import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.IRenderer;
import com.xshape.modele.IShape;
import com.xshape.modele.Polygone;
import com.xshape.modele.Rectangle;
import com.xshape.modele.awt.AwtRenderer;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.Goupage.ToolGroupComposite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

class AwtToolBar extends Panel {

    int pos_x = 30;
    int pos_y = 10;
    int width = 50;
    int height = 30;
    int radious = 25;
    private int current_y = pos_y;
    ArrayList<ToolGroupComponent> tools = new ArrayList<>();
    private Image trashLabel;
    private boolean clicked;


    public AwtToolBar(AwtApplication app, int x, int y, int width, int height) {
        setBackground(Color.CYAN);
        setBounds(x, y, width, height);
        clicked = false;

        try {
            URL imageUrl = new URL("file:src/main/resources/com/xshape/delete.png");
            BufferedImage bufferedImage = ImageIO.read(imageUrl);
            trashLabel = bufferedImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Créer un MouseListener pour l'image seulement
        ImageMouseListener listener = new ImageMouseListener();
        addMouseListener(listener);

        app.add(this);
    }

    private class ImageMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Vérifier si le clic a été effectué dans la zone de l'image
            if (e.getX() < trashLabel.getWidth(null) && e.getY() < trashLabel.getHeight(null)) {
                clicked = !clicked;

            }
        }
    }

    void addTool(ToolGroupComponent tool){
        tools.add(tool);
    }



    public void paint(Graphics g) {
        IRenderer renderer = new AwtRenderer(g);
        Rectangle r = new Rectangle(this.pos_x, this.pos_y, this.width, this.height, renderer);
        Polygone p = new Polygone(this.pos_x+ this.radious, this.pos_y, this.radious, 6, renderer);
        ToolGroupComponent recTool = new Tool(r);
        ToolGroupComponent polyTool = new Tool(p);
        addTool(recTool);
        addTool(polyTool);



        for(ToolGroupComponent tool: this.tools){
            tool.setPosition(tool.getPositionX(), current_y);
            tool.draw();
            current_y += 75;
        }


        if (trashLabel != null) {
            g.drawImage(trashLabel, (getWidth() - trashLabel.getWidth(null)) / 2, getHeight() - trashLabel.getHeight(null) - 10, null);
        }
    }

}