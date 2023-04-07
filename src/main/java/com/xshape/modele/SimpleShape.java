package com.xshape.modele;

public abstract class SimpleShape implements IShape {

    IRenderer _renderer;

    public SimpleShape(IRenderer renderer){
        _renderer = renderer;
    }


}
