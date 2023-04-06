package com.xshape.modele;

public abstract class SimpleShape implements IShape {

    IRenderer _I_renderer;

    public SimpleShape(IRenderer IRenderer){
        _I_renderer = IRenderer;
    }

}
