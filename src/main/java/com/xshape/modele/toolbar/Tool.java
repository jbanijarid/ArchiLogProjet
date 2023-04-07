package com.xshape.modele.toolbar;

import com.xshape.modele.IRenderer;
import com.xshape.modele.IShape;

public class Tool implements ToolBarComponent{

    IShape shape;

    public Tool(IShape shape){
        this.shape = shape;
    }

    @Override
    public IShape get() {
        return this.shape;
    }

    @Override
    public void add(IShape shape) {

    }

    @Override
    public void remove(IShape shape) {

    }
}
