package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

public interface ToolGroupComponent {
    void add(ToolGroupComponent tool);
    void remove(ToolGroupComponent tool);
    void draw();
    ToolGroupComponent clone();
    IShape get();
}
