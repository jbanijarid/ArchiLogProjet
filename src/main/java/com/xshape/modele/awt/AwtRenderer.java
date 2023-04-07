package com.xshape.modele.awt;

import com.xshape.modele.IRenderer;
import com.xshape.vue.awt.AwtApplication;

import java.awt.*;

public class AwtRenderer implements IRenderer {



    private Graphics g;

    public AwtRenderer(Graphics g) {
        this.g = g;
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }

    @Override
    public void setColor(int color) {
        g.setColor(new Color(color));
    }
}
