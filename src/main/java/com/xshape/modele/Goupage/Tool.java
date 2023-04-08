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
    public double getWidth() {
        return this.shape.getWidth();
    }

    @Override
    public double getHeight() {
        return this.shape.getHeight();
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
    public void setPosition(double x, double y) {
        this.shape.setPosition(x, y);
    }

    @Override
    public void setColor(int color) {

    }


}
