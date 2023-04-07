package com.xshape.modele.Goupage;

import com.xshape.modele.IShape;

import java.util.ArrayList;

public class ToolGroupComposite implements ToolGroupComponent {

    ArrayList<IShape> tools = new ArrayList<>();


    @Override
    public double getPositionX() {
        return 0;
    }

    @Override
    public double getPositionY() {
        return 0;
    }

    @Override
    public void add(IShape shape) {
        tools.add(shape);
    }

    @Override
    public void remove(IShape shape) {
        tools.remove(shape);
    }

    @Override
    public void draw() {
        for(IShape shape : tools){
            shape.draw();
        }
    }

    @Override
    public void setPosition(double x, double y) {

    }

    public ArrayList<IShape> getTools(){
        return this.tools;
    }



}
