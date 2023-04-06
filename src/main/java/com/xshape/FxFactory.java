package com.xshape;

import javafx.scene.control.Button;

public class FxFactory implements IFactory{
    @Override
    public Rectangle createRectangle(double x, double y, double width, double height,IRenderer r) {
        return new Rectangle(x,y,width,height,r);
    }

    @Override
    public Polygone createPolygon(double centerX, double centerY, double radius, double nbSides, IRenderer renderer) {
        return new Polygone(centerX,centerY,radius,nbSides,renderer);
    }

    @Override
    public Button createButton(String title, double width, double height) {
        Button b = new Button(title);
        b.setPrefSize(width,height);
        return b;
    }

    @Override
    public IToolBar createToolBar(Composite toolBarElements, Composite canvasElements) {
        return null;
    }

    @Override
    public IWhiteBoard createWhiteBoard(Composite canvasElements, double width, double height) {
        return null;
    }
}
