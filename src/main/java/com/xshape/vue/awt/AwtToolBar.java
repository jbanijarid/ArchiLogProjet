package com.xshape.vue.awt;

import com.xshape.modele.IRenderer;
import com.xshape.modele.IShape;
import com.xshape.modele.Polygone;
import com.xshape.modele.Rectangle;
import com.xshape.modele.awt.AwtRenderer;
import com.xshape.modele.toolbar.Tool;
import com.xshape.modele.toolbar.ToolBarComponent;
import com.xshape.modele.toolbar.ToolBarComposite;
import com.xshape.vue.awt.AwtApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

class AwtToolBar extends Panel {

    int pos_x = 15;
    int pos_y = 0;
    int width = 40;
    int height =40;
    ToolBarComponent tools = new ToolBarComposite();
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



    void addTool(IShape s){
        tools.add(s);
        s.draw();
    }

    //toujours faire addShape pour pouvoir bien placer le tool dans le bartools
    void addShape(){
        this.pos_y = this.pos_y+75;
    }


    public void paint(Graphics g) {
        IRenderer renderer = new AwtRenderer(g);

        Rectangle r = new Rectangle(this.pos_x, this.pos_y, this.width, this.height, renderer);
        addShape();
        Polygone p = new Polygone(this.pos_x, this.pos_y, this.width, this.height, renderer);
        addTool(r);
        addTool(p);


        if (trashLabel != null) {
            g.drawImage(trashLabel, (getWidth() - trashLabel.getWidth(null)) / 2, getHeight() - trashLabel.getHeight(null) - 10, null);
        }
    }

}