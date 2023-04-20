package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

import java.util.ArrayList;

public interface ToolGroupComponent {
    void add(ToolGroupComponent tool);
    void remove(ToolGroupComponent tool);
    ToolGroupComponent clone();
    IShape getShape();

    void draw();
    void clear();
    ArrayList<ToolGroupComponent> getShapes();
}
