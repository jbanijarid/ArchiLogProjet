package com.xshape.modele.fx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import  com.xshape.modele.*;
public class FxRenderer implements IRenderer {

    private GraphicsContext gc;
    private Canvas canvas;


    public FxRenderer(Canvas canvas){
        this.canvas = canvas;
        gc = this.canvas.getGraphicsContext2D();
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public void setColor(int color) {
        Color fxColor = Color.rgb(color, color, color);
        //_gc.setFill(Color.BLUE);
        gc.setFill(fxColor);

    }

    @Override
    public void fillPolygon(double[] xPoints, double[] yPoints, int cote) {
        gc.fillPolygon(xPoints,yPoints,cote);
    }
}
