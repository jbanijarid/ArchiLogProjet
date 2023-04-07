package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

public class Tool implements ToolGroupComponent {

    IShape shape;

    public Tool(IShape shape){
        this.shape = shape;
    }


    @Override
    public double getPositionX() {
        return this.shape.getPositionX();
    }

    @Override
    public double getPositionY() {
        return this.shape.getPositionY();
    }

    @Override
    public void add(IShape shape) {

    }

    @Override
    public void remove(IShape shape) {

    }

    @Override
    public void draw() {
        this.shape.draw();
    }

    @Override
    public void setPosition(double x, double y) {
        this.shape.setPosition(x, y);
    }


}
