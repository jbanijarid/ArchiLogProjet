package com.xshape.modele;

public abstract class SimpleShape implements IShape {

    IRenderer renderer;

    public SimpleShape(IRenderer renderer){
        this.renderer = renderer;
    }

}
