package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

public interface ToolGroupComponent {

    double getPositionX();
    double getPositionY();
    void add(IShape shape);
    void remove(IShape shape);
    void draw();
    void setPosition(double x, double y);

}
