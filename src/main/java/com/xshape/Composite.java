package com.xshape;

import com.xshape.modele.IShape;

import java.util.ArrayList;
import java.util.List;

public class Composite implements IShape {

    private List<IShape>  shaps;

    public Composite() {
        this.shaps = new ArrayList<>();
    }

    public void add(IShape shape){
        this.shaps.add(shape);
    }

    public void remove(IShape shape){
        this.shaps.remove(shape);
    }


    @Override
    public void draw() {
        for(IShape shape : shaps){
            shape.draw();
        }
    }
}
