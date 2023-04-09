package com.xshape.vue.awt;

import com.xshape.modele.Goupage.Tool;
import com.xshape.modele.Goupage.ToolGroupComponent;
import com.xshape.modele.IRenderer;
import com.xshape.modele.Polygone;
import com.xshape.modele.Rectangle;
import com.xshape.modele.awt.AwtRenderer;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AwtConcreteToolBar extends AwtAbstractToolBar{

    int pos_x = 30;
    int pos_y = 10;
    int width = 50;
    int height = 30;
    int radious = 25;
    private int current_y = pos_y;
    ArrayList<ToolGroupComponent> tools = new ArrayList<>();
    private Image trashLabel;
    private boolean clicked;
    private ToolGroupComponent selectedTool = null;


    AwtConcreteToolBar(AwtApplication app, int x, int y, int width, int height){
        super(app, x, y, width, height);
        clicked = false;

        try {
            URL imageUrl = new URL("file:src/main/resources/com/xshape/delete.png");
            BufferedImage bufferedImage = ImageIO.read(imageUrl);
            trashLabel = bufferedImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        app.add(this);

    }



    void addTool(ToolGroupComponent tool){
        tools.add(tool);
    }


    public ArrayList<ToolGroupComponent> getTools() {
        return tools;
    }


    @Override
    public void paint(Graphics g) {
        IRenderer renderer = new AwtRenderer(g);
        com.xshape.modele.Rectangle r = new Rectangle(this.pos_x, this.pos_y, this.width, this.height, renderer);
        Polygone p = new Polygone(this.pos_x+ this.radious, this.pos_y, this.radious, 6, renderer);
        ToolGroupComponent recTool = new Tool(r);
        ToolGroupComponent polyTool = new Tool(p);
        addTool(recTool);
        addTool(polyTool);


        for(ToolGroupComponent tool: this.tools){
            tool.getShape().setPosition(tool.getShape().getPositionX(),  current_y);
            tool.getShape().draw();
            current_y += 75;
        }


        if (trashLabel != null) {
            g.drawImage(trashLabel, (getWidth() - trashLabel.getWidth(null)) / 2, getHeight() - trashLabel.getHeight(null) - 10, null);
        }
    }
}
