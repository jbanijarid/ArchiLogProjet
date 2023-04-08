package com.xshape.modele;

public interface IShape {
    void draw();
    void setColor(int color);
    void setPosition(double x, double y);
    double getPositionX();
    double getPositionY();
    double getWidth();
    double getHeight();
}
