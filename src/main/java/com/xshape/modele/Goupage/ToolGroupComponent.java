package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

public interface ToolGroupComponent {

    IShape get();
    void add(IShape shape);
    void remove(IShape shape);


}
