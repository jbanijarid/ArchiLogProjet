package com.xshape.modele;

public interface IShape {
    void draw();
    void setColor(int color);
    int getColor();
    void setPosition(double x, double y);
    void setHeight(double height);
    void setWidth(double width);
    void setRadius(double radius);
    double getPositionX();
    double getPositionY();
    double getWidth();
    double getHeight();
    double getRadius();
    double getNbSides();
    boolean IsArea(double mouseX, double mouseY);
    void setRenderer(IRenderer r);
    IRenderer getIRenderer();

    void translate(double dx, double dy);
}
