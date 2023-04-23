package com.xshape.modele.Goupage;

import com.xshape.modele.CloneShape;
import com.xshape.modele.IShape;

import java.util.ArrayList;

public class Tool implements ToolGroupComponent, Cloneable{

    IShape shape;

    public Tool(IShape shape){
        this.shape = shape;
    }

    public void draw(){
        this.shape.draw();
    }

    @Override
    public ArrayList<ToolGroupComponent> getShapes() {
        return null;
    }

    @Override
    public boolean contains(IShape shape) {
        return false;
    }

    @Override
    public void add(ToolGroupComponent tool) {

    }

    @Override
    public void remove(ToolGroupComponent tool) {

    }

    @Override
    public ToolGroupComponent clone() {
        try {
            Tool clone = (Tool) super.clone();
            CloneShape s = new CloneShape(this.getShape());
            clone.shape = s.getClone();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void clear(){
        remove(this);
    }

    @Override
    public IShape getShape() {
        return this.shape;
    }




}
