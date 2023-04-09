package com.xshape.modele.Goupage;

import com.xshape.modele.CloneShape;
import com.xshape.modele.IShape;
import com.xshape.modele.Polygone;
import com.xshape.modele.Rectangle;

public class Tool implements ToolGroupComponent, Cloneable{

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
    public IShape getShape() {
        return this.shape;
    }


}
