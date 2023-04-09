package com.xshape.modele;

public class CloneShape {
    IShape clone = null;

    public CloneShape(IShape s){
        this.clone = s;
    }

    public IShape getClone(){
        IShape s = null;
        if(this.clone instanceof Rectangle){
            s = new Rectangle(this.clone.getPositionX(), this.clone.getPositionY(), this.clone.getWidth(), this.clone.getHeight(), this.clone.getIRenderer());
        }
        if(this.clone instanceof Polygone){
            s = new Polygone(this.clone.getPositionX(), this.clone.getPositionY(), this.clone.getRadius(), this.clone.getNbSides(), this.clone.getIRenderer());
        }
        return s;
    }
}
