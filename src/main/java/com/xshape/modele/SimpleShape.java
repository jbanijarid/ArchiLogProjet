package com.xshape.modele;

public abstract class SimpleShape implements Shape{

    Renderer _renderer;

    public SimpleShape(Renderer renderer){
        _renderer = renderer;
    }

}
