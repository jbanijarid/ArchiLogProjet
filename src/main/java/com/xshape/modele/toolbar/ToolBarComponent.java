package com.xshape.modele.toolbar;

import com.xshape.modele.IRenderer;
import com.xshape.modele.IShape;

public interface ToolBarComponent {

    IShape get();
    void add(IShape shape);
    void remove(IShape shape);


}
