package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

public interface ToolGroupComponent {

    double getPositionX();
    double getPositionY();
    double getWidth();
    double getHeight();
    void add(ToolGroupComponent tool);
    void remove(ToolGroupComponent tool);
    void draw();
    void setPosition(double x, double y);
    void setColor(int color);

}
