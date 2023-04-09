package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

public interface ToolGroupComponent {
    void add(ToolGroupComponent tool);
    void remove(ToolGroupComponent tool);
    ToolGroupComponent clone();
    IShape getShape();
}
