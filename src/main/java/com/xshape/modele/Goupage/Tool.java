package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

public class Tool implements ToolGroupComponent, Cloneable {

    IShape shape;

    public Tool(IShape shape){
        this.shape = shape;
    }


    @Override
    public void add(ToolGroupComponent tool) {

    }

    @Override
    public void remove(ToolGroupComponent tool) {

    }

    @Override
    public void draw() {
        this.shape.draw();
    }

    @Override
    public IShape get() {
        return this.shape;
    }


    @Override
    public ToolGroupComponent clone() {
        try {
            return (Tool) super.clone();
        } catch (CloneNotSupportedException e) {
            // si l'objet ne peut pas être cloné
            return null;
        }
    }


}
