package com.xshape.modele.fx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import  com.xshape.modele.*;
public class FxRenderer implements IRenderer {

    private GraphicsContext _gc;
    private Canvas _canvas;


    public FxRenderer(Canvas canvas){
        _canvas = canvas;
        _gc = _canvas.getGraphicsContext2D();
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        _gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public void setColor(int color) {
        _gc.setFill(Color.RED);


    }


}
