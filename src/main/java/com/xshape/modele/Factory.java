package com.xshape.modele;

public class Factory implements IFactory {
    @Override
    public Rectangle createRectangle(double x, double y, double width, double height, IRenderer r) {
        return new Rectangle(x,y,width,height,r);
    }

    @Override
    public Polygone createPolygone(double centerX, double centerY, double radius, double nbSides, IRenderer renderer) {
        return new Polygone(centerX,centerY,radius,nbSides,renderer);
    }

}
