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

    @Override
    public void fillPolygon(double[] xPoints, double[] yPoints, int cote) {
        int[] xInt = new int[xPoints.length];
        for (int i = 0; i < xPoints.length; i++) {
            xInt[i] = (int) xPoints[i];
        }
        int[] yInt = new int[yPoints.length];
        for (int i = 0; i < yPoints.length; i++) {
            yInt[i] = (int) yPoints[i];
        }
        g.fillPolygon(xInt, yInt, xPoints.length);
    }

    @Override
    public void fillRect(double x, double y, double width, double height) {
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }
}
